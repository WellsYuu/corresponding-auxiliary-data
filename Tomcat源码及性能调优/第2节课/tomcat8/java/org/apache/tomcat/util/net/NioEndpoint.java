/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.util.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.X509KeyManager;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.collections.SynchronizedQueue;
import org.apache.tomcat.util.collections.SynchronizedStack;
import org.apache.tomcat.util.compat.JreCompat;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;
import org.apache.tomcat.util.net.SecureNioChannel.ApplicationBufferHandler;
import org.apache.tomcat.util.net.jsse.NioX509KeyManager;

/**
 * NIO�������������ṩ���·���
 * �׽��ֽ����߳�
 * �׽�����ѯ�߳�
 * �����̳߳�
 * ʹ��������ĵ��̳߳ء�
 */
public class NioEndpoint extends AbstractEndpoint<NioChannel> {


    // -------------------------------------------------------------- Constants


    private static final Log log = LogFactory.getLog(NioEndpoint.class);


    public static final int OP_REGISTER = 0x100; //register interest op
    @Deprecated // Unused. Will be removed in Tomcat 8.5.x
    public static final int OP_CALLBACK = 0x200; //callback interest op

    // ----------------------------------------------------------------- Fields

    private NioSelectorPool selectorPool = new NioSelectorPool();

    /**
     * Server socket "pointer".
     */
    private ServerSocketChannel serverSock = null;

    /**
     * use send file
     */
    private boolean useSendfile = true;

    /**
     * The size of the OOM parachute.
     */
    private int oomParachute = 1024*1024;
    /**
     * The oom parachute, when an OOM error happens,
     * will release the data, giving the JVM instantly
     * a chunk of data to be able to recover with.
     */
    private byte[] oomParachuteData = null;

    /**
     * Make sure this string has already been allocated
     */
    private static final String oomParachuteMsg =
        "SEVERE:Memory usage is low, parachute is non existent, your system may start failing.";

    /**
     * Keep track of OOM warning messages.
     */
    private long lastParachuteCheck = System.currentTimeMillis();

    /**
     *
     */
    private volatile CountDownLatch stopLatch = null;

    /**
     * Cache for SocketProcessor objects
     */
    private SynchronizedStack<SocketProcessor> processorCache;

    /**
     * Cache for poller events
     */
    private SynchronizedStack<PollerEvent> eventCache;

    /**
     * Bytebuffer cache, each channel holds a set of buffers (two, except for SSL holds four)
     */
    private SynchronizedStack<NioChannel> nioChannels;


    // ------------------------------------------------------------ Constructor

    public NioEndpoint() {
        // If running on Java 7, the insecure DHE ciphers need to be excluded by
        // default
        if (!JreCompat.isJre8Available()) {
            setCiphers(DEFAULT_CIPHERS + ":!DHE");
        }
    }


    // ------------------------------------------------------------- Properties

    /**
     * Generic properties, introspected
     */
    @Override
    public boolean setProperty(String name, String value) {
        final String selectorPoolName = "selectorPool.";
        try {
            if (name.startsWith(selectorPoolName)) {
                return IntrospectionUtils.setProperty(selectorPool, name.substring(selectorPoolName.length()), value);
            } else {
                return super.setProperty(name, value);
            }
        }catch ( Exception x ) {
            log.error("Unable to set attribute \""+name+"\" to \""+value+"\"",x);
            return false;
        }
    }


    /**
     * Priority of the poller threads.
     */
    private int pollerThreadPriority = Thread.NORM_PRIORITY;
    public void setPollerThreadPriority(int pollerThreadPriority) { this.pollerThreadPriority = pollerThreadPriority; }
    public int getPollerThreadPriority() { return pollerThreadPriority; }


    /**
     * Handling of accepted sockets.
     */
    private Handler handler = null;
    public void setHandler(Handler handler ) { this.handler = handler; }
    public Handler getHandler() { return handler; }


    /**
     * Allow comet request handling.
     */
    private boolean useComet = true;
    public void setUseComet(boolean useComet) { this.useComet = useComet; }
    @Override
    public boolean getUseComet() { return useComet; }
    @Override
    public boolean getUseCometTimeout() { return getUseComet(); }
    @Override
    public boolean getUsePolling() { return true; } // Always supported


    /**
     * Poller thread count.
     */
    private int pollerThreadCount = Math.min(2,Runtime.getRuntime().availableProcessors());
    public void setPollerThreadCount(int pollerThreadCount) { this.pollerThreadCount = pollerThreadCount; }
    public int getPollerThreadCount() { return pollerThreadCount; }

    private long selectorTimeout = 1000;
    public void setSelectorTimeout(long timeout){ this.selectorTimeout = timeout;}
    public long getSelectorTimeout(){ return this.selectorTimeout; }
    /**
     * The socket poller.
     */
    private Poller[] pollers = null;
    private AtomicInteger pollerRotater = new AtomicInteger(0);
    /**
     * Return an available poller in true round robin fashion
     */
    public Poller getPoller0() {
        int idx = Math.abs(pollerRotater.incrementAndGet()) % pollers.length;
        return pollers[idx];
    }


    public void setSelectorPool(NioSelectorPool selectorPool) {
        this.selectorPool = selectorPool;
    }

    public void setSocketProperties(SocketProperties socketProperties) {
        this.socketProperties = socketProperties;
    }

    public void setUseSendfile(boolean useSendfile) {
        this.useSendfile = useSendfile;
    }

    /**
     * Is deferAccept supported?
     */
    @Override
    public boolean getDeferAccept() {
        // Not supported
        return false;
    }

    public void setOomParachute(int oomParachute) {
        this.oomParachute = oomParachute;
    }

    public void setOomParachuteData(byte[] oomParachuteData) {
        this.oomParachuteData = oomParachuteData;
    }


    private SSLContext sslContext = null;
    public SSLContext getSSLContext() { return sslContext;}
    public void setSSLContext(SSLContext c) { sslContext = c;}
    private String[] enabledCiphers;
    private String[] enabledProtocols;

    /**
     * Port in use.
     */
    @Override
    public int getLocalPort() {
        ServerSocketChannel ssc = serverSock;
        if (ssc == null) {
            return -1;
        } else {
            ServerSocket s = ssc.socket();
            if (s == null) {
                return -1;
            } else {
                return s.getLocalPort();
            }
        }
    }


    @Override
    public String[] getCiphersUsed() {
        return enabledCiphers;
    }


    // --------------------------------------------------------- OOM Parachute Methods

    protected void checkParachute() {
        boolean para = reclaimParachute(false);
        if (!para && (System.currentTimeMillis()-lastParachuteCheck)>10000) {
            try {
                log.fatal(oomParachuteMsg);
            }catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                System.err.println(oomParachuteMsg);
            }
            lastParachuteCheck = System.currentTimeMillis();
        }
    }

    protected boolean reclaimParachute(boolean force) {
        if ( oomParachuteData != null ) return true;
        if ( oomParachute > 0 && ( force || (Runtime.getRuntime().freeMemory() > (oomParachute*2))) )
            oomParachuteData = new byte[oomParachute];
        return oomParachuteData != null;
    }

    protected void releaseCaches() {
        this.nioChannels.clear();
        this.processorCache.clear();
        if ( handler != null ) handler.recycle();

    }

    // --------------------------------------------------------- Public Methods
    /**
     * Number of keepalive sockets.
     */
    public int getKeepAliveCount() {
        if (pollers == null) {
            return 0;
        } else {
            int sum = 0;
            for (int i=0; i<pollers.length; i++) {
                sum += pollers[i].getKeyCount();
            }
            return sum;
        }
    }


    // ----------------------------------------------- Public Lifecycle Methods


    /**
     * Initialize the endpoint.
     */
    @Override
    public void bind() throws Exception {

        serverSock = ServerSocketChannel.open();
        socketProperties.setProperties(serverSock.socket());
        InetSocketAddress addr = (getAddress()!=null?new InetSocketAddress(getAddress(),getPort()):new InetSocketAddress(getPort()));
        serverSock.socket().bind(addr,getBacklog());
        serverSock.configureBlocking(true); //mimic APR behavior
        serverSock.socket().setSoTimeout(getSocketProperties().getSoTimeout());

        // Initialize thread count defaults for acceptor, poller
        if (acceptorThreadCount == 0) {
            // FIXME: Doesn't seem to work that well with multiple accept threads
            acceptorThreadCount = 1;
        }
        if (pollerThreadCount <= 0) {
            //minimum one poller thread
            pollerThreadCount = 1;
        }
        stopLatch = new CountDownLatch(pollerThreadCount);

        // Initialize SSL if needed
        if (isSSLEnabled()) {
            SSLUtil sslUtil = handler.getSslImplementation().getSSLUtil(this);

            sslContext = sslUtil.createSSLContext();
            sslContext.init(wrap(sslUtil.getKeyManagers()),
                    sslUtil.getTrustManagers(), null);

            SSLSessionContext sessionContext =
                sslContext.getServerSessionContext();
            if (sessionContext != null) {
                sslUtil.configureSessionContext(sessionContext);
            }
            // Determine which cipher suites and protocols to enable
            enabledCiphers = sslUtil.getEnableableCiphers(sslContext);
            enabledProtocols = sslUtil.getEnableableProtocols(sslContext);
        }

        if (oomParachute>0) reclaimParachute(true);
        selectorPool.open();
    }

    public KeyManager[] wrap(KeyManager[] managers) {
        if (managers==null) return null;
        KeyManager[] result = new KeyManager[managers.length];
        for (int i=0; i<result.length; i++) {
            if (managers[i] instanceof X509KeyManager && getKeyAlias()!=null) {
                String keyAlias = getKeyAlias();
                // JKS keystores always convert the alias name to lower case
                if ("jks".equalsIgnoreCase(getKeystoreType())) {
                    keyAlias = keyAlias.toLowerCase(Locale.ENGLISH);
                }
                result[i] = new NioX509KeyManager((X509KeyManager) managers[i], keyAlias);
            } else {
                result[i] = managers[i];
            }
        }
        return result;
    }


    /**
     * Start the NIO endpoint, creating acceptor, poller threads.
     */
    @Override
    public void startInternal() throws Exception {

        if (!running) {
            running = true;
            paused = false;

            processorCache = new SynchronizedStack<>(SynchronizedStack.DEFAULT_SIZE,
                    socketProperties.getProcessorCache());
            eventCache = new SynchronizedStack<>(SynchronizedStack.DEFAULT_SIZE,
                            socketProperties.getEventCache());
            nioChannels = new SynchronizedStack<>(SynchronizedStack.DEFAULT_SIZE,
                    socketProperties.getBufferPool());

            // Create worker collection
            if ( getExecutor() == null ) {
                createExecutor();
            }

            initializeConnectionLatch();

            // Start poller threads
            pollers = new Poller[getPollerThreadCount()];
            for (int i=0; i<pollers.length; i++) {
                pollers[i] = new Poller();
                Thread pollerThread = new Thread(pollers[i], getName() + "-ClientPoller-"+i);
                pollerThread.setPriority(threadPriority);
                pollerThread.setDaemon(true);
                pollerThread.start();
            }

            startAcceptorThreads();
        }
    }


    /**
     * Stop the endpoint. This will cause all processing threads to stop.
     */
    @Override
    public void stopInternal() {
        releaseConnectionLatch();
        if (!paused) {
            pause();
        }
        if (running) {
            running = false;
            unlockAccept();
            for (int i=0; pollers!=null && i<pollers.length; i++) {
                if (pollers[i]==null) continue;
                pollers[i].destroy();
                pollers[i] = null;
            }
            try {
                stopLatch.await(selectorTimeout + 100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ignore) {
            }
            shutdownExecutor();
            eventCache.clear();
            nioChannels.clear();
            processorCache.clear();
        }

    }


    /**
     * Deallocate NIO memory pools, and close server socket.
     */
    @Override
    public void unbind() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Destroy initiated for "+new InetSocketAddress(getAddress(),getPort()));
        }
        if (running) {
            stop();
        }
        // Close server socket
        serverSock.socket().close();
        serverSock.close();
        serverSock = null;
        sslContext = null;
        releaseCaches();
        selectorPool.close();
        if (log.isDebugEnabled()) {
            log.debug("Destroy completed for "+new InetSocketAddress(getAddress(),getPort()));
        }
    }


    // ------------------------------------------------------ Protected Methods


    public int getWriteBufSize() {
        return socketProperties.getTxBufSize();
    }

    public int getReadBufSize() {
        return socketProperties.getRxBufSize();
    }

    public NioSelectorPool getSelectorPool() {
        return selectorPool;
    }

    @Override
    public boolean getUseSendfile() {
        return useSendfile;
    }

    public int getOomParachute() {
        return oomParachute;
    }

    public byte[] getOomParachuteData() {
        return oomParachuteData;
    }


    @Override
    protected AbstractEndpoint.Acceptor createAcceptor() {
        return new Acceptor();
    }


    /**
     * Process the specified connection.
     */
    protected boolean setSocketOptions(SocketChannel socket) {
        // Process the connection
        try {
            //disable blocking, APR style, we are gonna be polling it
            socket.configureBlocking(false);
            Socket sock = socket.socket();
            socketProperties.setProperties(sock);

            NioChannel channel = nioChannels.pop();
            if ( channel == null ) {
                // SSL setup
                if (sslContext != null) {
                    SSLEngine engine = createSSLEngine();
                    int appbufsize = engine.getSession().getApplicationBufferSize();
                    NioBufferHandler bufhandler = new NioBufferHandler(Math.max(appbufsize,socketProperties.getAppReadBufSize()),
                                                                       Math.max(appbufsize,socketProperties.getAppWriteBufSize()),
                                                                       socketProperties.getDirectBuffer());
                    channel = new SecureNioChannel(socket, engine, bufhandler, selectorPool);
                } else {
                    // normal tcp setup
                    NioBufferHandler bufhandler = new NioBufferHandler(socketProperties.getAppReadBufSize(),
                                                                       socketProperties.getAppWriteBufSize(),
                                                                       socketProperties.getDirectBuffer());

                    channel = new NioChannel(socket, bufhandler);
                }
            } else {
                channel.setIOChannel(socket);
                if ( channel instanceof SecureNioChannel ) {
                    SSLEngine engine = createSSLEngine();
                    ((SecureNioChannel)channel).reset(engine);
                } else {
                    channel.reset();
                }
            }
            getPoller0().register(channel);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            try {
                log.error("",t);
            } catch (Throwable tt) {
                ExceptionUtils.handleThrowable(tt);
            }
            // Tell to close the socket
            return false;
        }
        return true;
    }

    protected SSLEngine createSSLEngine() {
        SSLEngine engine = sslContext.createSSLEngine();
        if ("false".equals(getClientAuth())) {
            engine.setNeedClientAuth(false);
            engine.setWantClientAuth(false);
        } else if ("true".equals(getClientAuth()) || "yes".equals(getClientAuth())){
            engine.setNeedClientAuth(true);
        } else if ("want".equals(getClientAuth())) {
            engine.setWantClientAuth(true);
        }
        engine.setUseClientMode(false);
        engine.setEnabledCipherSuites(enabledCiphers);
        engine.setEnabledProtocols(enabledProtocols);

        configureUseServerCipherSuitesOrder(engine);

        return engine;
    }


    /**
     * Returns true if a worker thread is available for processing.
     * @return boolean
     */
    protected boolean isWorkerAvailable() {
        return true;
    }


    @Override
    public void processSocket(SocketWrapper<NioChannel> socketWrapper,
            SocketStatus socketStatus, boolean dispatch) {
        processSocket((KeyAttachment) socketWrapper, socketStatus, dispatch);
    }

    protected boolean processSocket(KeyAttachment attachment, SocketStatus status, boolean dispatch) {
        try {
            if (attachment == null) {
                return false;
            }
            SocketProcessor sc = processorCache.pop();
            if ( sc == null ) sc = new SocketProcessor(attachment, status);
            else sc.reset(attachment, status);
            Executor executor = getExecutor();
            if (dispatch && executor != null) {
                executor.execute(sc);
            } else {
                sc.run();
            }
        } catch (RejectedExecutionException ree) {
            log.warn(sm.getString("endpoint.executor.fail", attachment.getSocket()), ree);
            return false;
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            // This means we got an OOM or similar creating a thread, or that
            // the pool and its queue are full
            log.error(sm.getString("endpoint.process.fail"), t);
            return false;
        }
        return true;
    }

    @Override
    protected Log getLog() {
        return log;
    }


    // --------------------------------------------------- Acceptor Inner Class
    /**
     * The background thread that listens for incoming TCP/IP connections and
     * hands them off to an appropriate processor.
     */
    protected class Acceptor extends AbstractEndpoint.Acceptor {

        @Override
        public void run() {

            int errorDelay = 0;

            // Loop until we receive a shutdown command
            while (running) {

                // Loop if endpoint is paused
                while (paused && running) {
                    state = AcceptorState.PAUSED;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }

                if (!running) {
                    break;
                }
                state = AcceptorState.RUNNING;

                try {
                    //if we have reached max connections, wait
                    countUpOrAwaitConnection();

                    SocketChannel socket = null;
                    try {
                        // Accept the next incoming connection from the server
                        // socket
                        socket = serverSock.accept();
                    } catch (IOException ioe) {
                        //we didn't get a socket
                        countDownConnection();
                        // Introduce delay if necessary
                        errorDelay = handleExceptionWithDelay(errorDelay);
                        // re-throw
                        throw ioe;
                    }
                    // Successful accept, reset the error delay
                    errorDelay = 0;

                    // setSocketOptions() will add channel to the poller
                    // if successful
                    if (running && !paused) {
                        if (!setSocketOptions(socket)) {
                            countDownConnection();
                            closeSocket(socket);
                        }
                    } else {
                        countDownConnection();
                        closeSocket(socket);
                    }
                } catch (SocketTimeoutException sx) {
                    // Ignore: Normal condition
                } catch (IOException x) {
                    if (running) {
                        log.error(sm.getString("endpoint.accept.fail"), x);
                    }
                } catch (OutOfMemoryError oom) {
                    try {
                        oomParachuteData = null;
                        releaseCaches();
                        log.error("", oom);
                    }catch ( Throwable oomt ) {
                        try {
                            try {
                                System.err.println(oomParachuteMsg);
                                oomt.printStackTrace();
                            }catch (Throwable letsHopeWeDontGetHere){
                                ExceptionUtils.handleThrowable(letsHopeWeDontGetHere);
                            }
                        }catch (Throwable letsHopeWeDontGetHere){
                            ExceptionUtils.handleThrowable(letsHopeWeDontGetHere);
                        }
                    }
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    log.error(sm.getString("endpoint.accept.fail"), t);
                }
            }
            state = AcceptorState.ENDED;
        }
    }


    private void closeSocket(SocketChannel socket) {
        try {
            socket.socket().close();
        } catch (IOException ioe)  {
            if (log.isDebugEnabled()) {
                log.debug("", ioe);
            }
        }
        try {
            socket.close();
        } catch (IOException ioe) {
            if (log.isDebugEnabled()) {
                log.debug("", ioe);
            }
        }
    }


    // ----------------------------------------------------- Poller Inner Classes

    /**
     *
     * PollerEvent, cacheable object for poller events to avoid GC
     */
    public static class PollerEvent implements Runnable {

        private NioChannel socket;
        private int interestOps;
        private KeyAttachment key;

        public PollerEvent(NioChannel ch, KeyAttachment k, int intOps) {
            reset(ch, k, intOps);
        }

        public void reset(NioChannel ch, KeyAttachment k, int intOps) {
            socket = ch;
            interestOps = intOps;
            key = k;
        }

        public void reset() {
            reset(null, null, 0);
        }

        @Override
        public void run() {
            if ( interestOps == OP_REGISTER ) {
                try {
                    socket.getIOChannel().register(socket.getPoller().getSelector(), SelectionKey.OP_READ, key);
                } catch (Exception x) {
                    log.error("", x);
                }
            } else {
                final SelectionKey key = socket.getIOChannel().keyFor(socket.getPoller().getSelector());
                try {
                    if (key == null) {
                        // The key was cancelled (e.g. due to socket closure)
                        // and removed from the selector while it was being
                        // processed. Count down the connections at this point
                        // since it won't have been counted down when the socket
                        // closed.
                        socket.getPoller().getEndpoint().countDownConnection();
                    } else {
                        final KeyAttachment att = (KeyAttachment) key.attachment();
                        if ( att!=null ) {
                            att.access();//to prevent timeout
                            //we are registering the key to start with, reset the fairness counter.
                            int ops = key.interestOps() | interestOps;
                            att.interestOps(ops);
                            key.interestOps(ops);
                        } else {
                            socket.getPoller().cancelledKey(key, SocketStatus.ERROR);
                        }
                    }
                } catch (CancelledKeyException ckx) {
                    try {
                        socket.getPoller().cancelledKey(key, SocketStatus.DISCONNECT);
                    } catch (Exception ignore) {}
                }
            }//end if
        }//run

        @Override
        public String toString() {
            return super.toString()+"[intOps="+this.interestOps+"]";
        }
    }

    /**
     * Poller class.
     */
    public class Poller implements Runnable {

        private Selector selector;
        private final SynchronizedQueue<PollerEvent> events =
                new SynchronizedQueue<>();

        private volatile boolean close = false;
        private long nextExpiration = 0;//optimize expiration handling

        private AtomicLong wakeupCounter = new AtomicLong(0);

        private volatile int keyCount = 0;

        public Poller() throws IOException {
            synchronized (Selector.class) {
                // Selector.open() isn't thread safe
                // http://bugs.sun.com/view_bug.do?bug_id=6427854
                // Affects 1.6.0_29, fixed in 1.7.0_01
                this.selector = Selector.open();
            }
        }

        public int getKeyCount() { return keyCount; }

        public Selector getSelector() { return selector;}

        NioEndpoint getEndpoint() {
            return NioEndpoint.this;
        }

        /**
         * Destroy the poller.
         */
        protected void destroy() {
            // Wait for polltime before doing anything, so that the poller threads
            // exit, otherwise parallel closure of sockets which are still
            // in the poller can cause problems
            close = true;
            selector.wakeup();
        }

        private void addEvent(PollerEvent event) {
            events.offer(event);
            if ( wakeupCounter.incrementAndGet() == 0 ) selector.wakeup();
        }

        /**
         * Add specified socket and associated pool to the poller. The socket will
         * be added to a temporary array, and polled first after a maximum amount
         * of time equal to pollTime (in most cases, latency will be much lower,
         * however).
         *
         * @param socket to add to the poller
         */
        public void add(final NioChannel socket) {
            add(socket,SelectionKey.OP_READ);
        }

        public void add(final NioChannel socket, final int interestOps) {
            PollerEvent r = eventCache.pop();
            if ( r==null) r = new PollerEvent(socket,null,interestOps);
            else r.reset(socket,null,interestOps);
            addEvent(r);
            if (close) {
                NioEndpoint.KeyAttachment ka = (NioEndpoint.KeyAttachment)socket.getAttachment();
                processSocket(ka, SocketStatus.STOP, false);
            }
        }

        /**
         * Processes events in the event queue of the Poller.
         *
         * @return <code>true</code> if some events were processed,
         *   <code>false</code> if queue was empty
         */
        public boolean events() {
            boolean result = false;

            PollerEvent pe = null;
            while ( (pe = events.poll()) != null ) {
                result = true;
                try {
                    pe.run();
                    pe.reset();
                    if (running && !paused) {
                        eventCache.push(pe);
                    }
                } catch ( Throwable x ) {
                    log.error("",x);
                }
            }

            return result;
        }

        /**
         * Registers a newly created socket with the poller.
         *
         * @param socket    The newly created socket
         */
        public void register(final NioChannel socket) {
            socket.setPoller(this);
            KeyAttachment ka = new KeyAttachment(socket);
            ka.setPoller(this);
            ka.setTimeout(getSocketProperties().getSoTimeout());
            ka.setKeepAliveLeft(NioEndpoint.this.getMaxKeepAliveRequests());
            ka.setSecure(isSSLEnabled());
            PollerEvent r = eventCache.pop();
            ka.interestOps(SelectionKey.OP_READ);//this is what OP_REGISTER turns into.
            if ( r==null) r = new PollerEvent(socket,ka,OP_REGISTER);
            else r.reset(socket,ka,OP_REGISTER);
            addEvent(r);
        }

        public KeyAttachment cancelledKey(SelectionKey key, SocketStatus status) {
            KeyAttachment ka = null;
            try {
                if ( key == null ) return null;//nothing to do
                ka = (KeyAttachment) key.attachment();
                if (ka != null && ka.isComet() && status != null) {
                    ka.setComet(false);//to avoid a loop
                    if (status == SocketStatus.TIMEOUT ) {
                        if (processSocket(ka, status, true)) {
                            return null; // don't close on comet timeout
                        }
                    } else {
                        // Don't dispatch if the lines below are cancelling the key
                        processSocket(ka, status, false);
                    }
                }
                ka = (KeyAttachment) key.attach(null);
                if (ka!=null) handler.release(ka);
                else handler.release((SocketChannel)key.channel());
                if (key.isValid()) key.cancel();
                // If it is available, close the NioChannel first which should
                // in turn close the underlying SocketChannel. The NioChannel
                // needs to be closed first, if available, to ensure that TLS
                // connections are shut down cleanly.
                if (ka != null) {
                    try {
                        ka.getSocket().close(true);
                    } catch (Exception e){
                        if (log.isDebugEnabled()) {
                            log.debug(sm.getString(
                                    "endpoint.debug.socketCloseFail"), e);
                        }
                    }
                }
                // The SocketChannel is also available via the SelectionKey. If
                // it hasn't been closed in the block above, close it now.
                if (key.channel().isOpen()) {
                    try {
                        key.channel().close();
                    } catch (Exception e) {
                        if (log.isDebugEnabled()) {
                            log.debug(sm.getString(
                                    "endpoint.debug.channelCloseFail"), e);
                        }
                    }
                }
                try {
                    if (ka != null && ka.getSendfileData() != null
                            && ka.getSendfileData().fchannel != null
                            && ka.getSendfileData().fchannel.isOpen()) {
                        ka.getSendfileData().fchannel.close();
                    }
                } catch (Exception ignore) {
                }
                if (ka != null) {
                    countDownConnection();
                }
            } catch (Throwable e) {
                ExceptionUtils.handleThrowable(e);
                if (log.isDebugEnabled()) log.error("",e);
            }
            return ka;
        }
        /**
         * The background thread that listens for incoming TCP/IP connections and
         * hands them off to an appropriate processor.
         */
        @Override
        public void run() {
            // Loop until destroy() is called
            while (true) {
                try {
                    // Loop if endpoint is paused
                    while (paused && (!close) ) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // Ignore
                        }
                    }

                    boolean hasEvents = false;

                    // Time to terminate?
                    if (close) {
                        events();
                        timeout(0, false);
                        try {
                            selector.close();
                        } catch (IOException ioe) {
                            log.error(sm.getString(
                                    "endpoint.nio.selectorCloseFail"), ioe);
                        }
                        break;
                    } else {
                        hasEvents = events();
                    }
                    try {
                        if ( !close ) {
                            if (wakeupCounter.getAndSet(-1) > 0) {
                                //if we are here, means we have other stuff to do
                                //do a non blocking select
                                keyCount = selector.selectNow();
                            } else {
                                keyCount = selector.select(selectorTimeout);
                            }
                            wakeupCounter.set(0);
                        }
                        if (close) {
                            events();
                            timeout(0, false);
                            try {
                                selector.close();
                            } catch (IOException ioe) {
                                log.error(sm.getString(
                                        "endpoint.nio.selectorCloseFail"), ioe);
                            }
                            break;
                        }
                    } catch (Throwable x) {
                        ExceptionUtils.handleThrowable(x);
                        log.error("",x);
                        continue;
                    }
                    //either we timed out or we woke up, process events first
                    if ( keyCount == 0 ) hasEvents = (hasEvents | events());

                    Iterator<SelectionKey> iterator =
                        keyCount > 0 ? selector.selectedKeys().iterator() : null;
                    // Walk through the collection of ready keys and dispatch
                    // any active event.
                    while (iterator != null && iterator.hasNext()) {
                        SelectionKey sk = iterator.next();
                        KeyAttachment attachment = (KeyAttachment)sk.attachment();
                        // Attachment may be null if another thread has called
                        // cancelledKey()
                        if (attachment == null) {
                            iterator.remove();
                        } else {
                            attachment.access();
                            iterator.remove();
                            processKey(sk, attachment);
                        }
                    }//while

                    //process timeouts
                    timeout(keyCount,hasEvents);
                    if ( oomParachute > 0 && oomParachuteData == null ) checkParachute();
                } catch (OutOfMemoryError oom) {
                    try {
                        oomParachuteData = null;
                        releaseCaches();
                        log.error("", oom);
                    }catch ( Throwable oomt ) {
                        try {
                            System.err.println(oomParachuteMsg);
                            oomt.printStackTrace();
                        }catch (Throwable letsHopeWeDontGetHere){
                            ExceptionUtils.handleThrowable(letsHopeWeDontGetHere);
                        }
                    }
                }
            }//while

            stopLatch.countDown();
        }

        protected boolean processKey(SelectionKey sk, KeyAttachment attachment) {
            boolean result = true;
            try {
                if ( close ) {
                    cancelledKey(sk, SocketStatus.STOP);
                } else if ( sk.isValid() && attachment != null ) {
                    attachment.access();//make sure we don't time out valid sockets
                    if (sk.isReadable() || sk.isWritable() ) {
                        if ( attachment.getSendfileData() != null ) {
                            processSendfile(sk,attachment, false);
                        } else {
                            if ( isWorkerAvailable() ) {
                                unreg(sk, attachment, sk.readyOps());
                                boolean closeSocket = false;
                                // Read goes before write
                                if (sk.isReadable()) {
                                    if (!processSocket(attachment, SocketStatus.OPEN_READ, true)) {
                                        closeSocket = true;
                                    }
                                }
                                if (!closeSocket && sk.isWritable()) {
                                    if (!processSocket(attachment, SocketStatus.OPEN_WRITE, true)) {
                                        closeSocket = true;
                                    }
                                }
                                if (closeSocket) {
                                    cancelledKey(sk,SocketStatus.DISCONNECT);
                                }
                            } else {
                                result = false;
                            }
                        }
                    }
                } else {
                    //invalid key
                    cancelledKey(sk, SocketStatus.ERROR);
                }
            } catch ( CancelledKeyException ckx ) {
                cancelledKey(sk, SocketStatus.ERROR);
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                log.error("",t);
            }
            return result;
        }

        public SendfileState processSendfile(SelectionKey sk, KeyAttachment attachment,
                boolean calledByProcessor) {
            NioChannel sc = null;
            try {
                unreg(sk, attachment, sk.readyOps());
                SendfileData sd = attachment.getSendfileData();

                if (log.isTraceEnabled()) {
                    log.trace("Processing send file for: " + sd.fileName);
                }

                if (sd.fchannel == null) {
                    // Setup the file channel
                    File f = new File(sd.fileName);
                    @SuppressWarnings("resource") // Closed when channel is closed
                    FileInputStream fis = new FileInputStream(f);
                    sd.fchannel = fis.getChannel();
                }

                // Configure output channel
                sc = attachment.getSocket();
                // TLS/SSL channel is slightly different
                WritableByteChannel wc = ((sc instanceof SecureNioChannel)?sc:sc.getIOChannel());

                // We still have data in the buffer
                if (sc.getOutboundRemaining()>0) {
                    if (sc.flushOutbound()) {
                        attachment.access();
                    }
                } else {
                    long written = sd.fchannel.transferTo(sd.pos,sd.length,wc);
                    if (written > 0) {
                        sd.pos += written;
                        sd.length -= written;
                        attachment.access();
                    } else {
                        // Unusual not to be able to transfer any bytes
                        // Check the length was set correctly
                        if (sd.fchannel.size() <= sd.pos) {
                            throw new IOException("Sendfile configured to " +
                                    "send more data than was available");
                        }
                    }
                }
                if (sd.length <= 0 && sc.getOutboundRemaining()<=0) {
                    if (log.isDebugEnabled()) {
                        log.debug("Send file complete for: "+sd.fileName);
                    }
                    attachment.setSendfileData(null);
                    try {
                        sd.fchannel.close();
                    } catch (Exception ignore) {
                    }
                    // For calls from outside the Poller, the caller is
                    // responsible for registering the socket for the
                    // appropriate event(s) if sendfile completes.
                    if (!calledByProcessor) {
                        switch (sd.keepAliveState) {
                        case NONE: {
                            if (log.isDebugEnabled()) {
                                log.debug("Send file connection is being closed");
                            }
                            cancelledKey(sk,SocketStatus.STOP);
                            break;
                        }
                        case PIPELINED: {
                            if (log.isDebugEnabled()) {
                                log.debug("Connection is keep alive, processing pipe-lined data");
                            }
                            if (!processSocket(attachment, SocketStatus.OPEN_READ, true)) {
                                cancelledKey(sk, SocketStatus.DISCONNECT);
                            }
                            break;
                        }
                        case OPEN: {
                            if (log.isDebugEnabled()) {
                                log.debug("Connection is keep alive, registering back for OP_READ");
                            }
                            reg(sk, attachment, SelectionKey.OP_READ);
                            break;
                        }
                        }
                    }
                    return SendfileState.DONE;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("OP_WRITE for sendfile: " + sd.fileName);
                    }
                    if (calledByProcessor) {
                        add(attachment.getSocket(),SelectionKey.OP_WRITE);
                    } else {
                        reg(sk,attachment,SelectionKey.OP_WRITE);
                    }
                    return SendfileState.PENDING;
                }
            }catch ( IOException x ) {
                if ( log.isDebugEnabled() ) log.debug("Unable to complete sendfile request:", x);
                if (!calledByProcessor) {
                    cancelledKey(sk,SocketStatus.ERROR);
                }
                return SendfileState.ERROR;
            }catch ( Throwable t ) {
                log.error("",t);
                if (!calledByProcessor) {
                    cancelledKey(sk, SocketStatus.ERROR);
                }
                return SendfileState.ERROR;
            }
        }

        protected void unreg(SelectionKey sk, KeyAttachment attachment, int readyOps) {
            //this is a must, so that we don't have multiple threads messing with the socket
            reg(sk,attachment,sk.interestOps()& (~readyOps));
        }

        protected void reg(SelectionKey sk, KeyAttachment attachment, int intops) {
            sk.interestOps(intops);
            attachment.interestOps(intops);
        }

        protected void timeout(int keyCount, boolean hasEvents) {
            long now = System.currentTimeMillis();
            // This method is called on every loop of the Poller. Don't process
            // timeouts on every loop of the Poller since that would create too
            // much load and timeouts can afford to wait a few seconds.
            // However, do process timeouts if any of the following are true:
            // - the selector simply timed out (suggests there isn't much load)
            // - the nextExpiration time has passed
            // - the server socket is being closed
            if (nextExpiration > 0 && (keyCount > 0 || hasEvents) && (now < nextExpiration) && !close) {
                return;
            }
            //timeout
            Set<SelectionKey> keys = selector.keys();
            int keycount = 0;
            try {
                for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext();) {
                    SelectionKey key = iter.next();
                    keycount++;
                    try {
                        KeyAttachment ka = (KeyAttachment) key.attachment();
                        if ( ka == null ) {
                            cancelledKey(key, SocketStatus.ERROR); //we don't support any keys without attachments
                        } else if ((ka.interestOps()&SelectionKey.OP_READ) == SelectionKey.OP_READ ||
                                  (ka.interestOps()&SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
                            //only timeout sockets that we are waiting for a read from
                            long delta = now - ka.getLastAccess();
                            long timeout = ka.getTimeout();
                            boolean isTimedout = timeout > 0 && delta > timeout;
                            if ( close ) {
                                key.interestOps(0);
                                ka.interestOps(0); //avoid duplicate stop calls
                                processKey(key,ka);
                            } else if (isTimedout) {
                                key.interestOps(0);
                                ka.interestOps(0); //avoid duplicate timeout calls
                                cancelledKey(key, SocketStatus.TIMEOUT);
                            }
                        } else if (ka.isAsync() || ka.isComet()) {
                            if (close) {
                                key.interestOps(0);
                                ka.interestOps(0); //avoid duplicate stop calls
                                processKey(key,ka);
                            } else if (!ka.isAsync() || ka.getTimeout() > 0) {
                                // Async requests with a timeout of 0 or less never timeout
                                long delta = now - ka.getLastAccess();
                                long timeout = (ka.getTimeout()==-1)?((long) socketProperties.getSoTimeout()):(ka.getTimeout());
                                boolean isTimedout = delta > timeout;
                                if (isTimedout) {
                                    // Prevent subsequent timeouts if the timeout event takes a while to process
                                    ka.access(Long.MAX_VALUE);
                                    processSocket(ka, SocketStatus.TIMEOUT, true);
                                }
                            }
                        }//end if
                    } catch ( CancelledKeyException ckx ) {
                        cancelledKey(key, SocketStatus.ERROR);
                    }
                }//for
            } catch (ConcurrentModificationException cme) {
                // See https://bz.apache.org/bugzilla/show_bug.cgi?id=57943
                log.warn(sm.getString("endpoint.nio.timeoutCme"), cme);
            }
            long prevExp = nextExpiration; //for logging purposes only
            nextExpiration = System.currentTimeMillis() +
                    socketProperties.getTimeoutInterval();
            if (log.isTraceEnabled()) {
                log.trace("timeout completed: keys processed=" + keycount +
                        "; now=" + now + "; nextExpiration=" + prevExp +
                        "; keyCount=" + keyCount + "; hasEvents=" + hasEvents +
                        "; eval=" + ((now < prevExp) && (keyCount>0 || hasEvents) && (!close) ));
            }

        }
    }

// ----------------------------------------------------- Key Attachment Class
    public static class KeyAttachment extends SocketWrapper<NioChannel> {

        public KeyAttachment(NioChannel channel) {
            super(channel);
        }

        public Poller getPoller() { return poller;}
        public void setPoller(Poller poller){this.poller = poller;}
        @Deprecated // Unused. NO-OP. Will be removed in Tomcat 8.5.x
        public void setCometNotify(@SuppressWarnings("unused") boolean notify) { /* NO-OP */ }
        @Deprecated // Unused. Always returns false. Will be removed in Tomcat 8.5.x
        public boolean getCometNotify() { return false; }
        public int interestOps() { return interestOps;}
        public int interestOps(int ops) { this.interestOps  = ops; return ops; }
        public CountDownLatch getReadLatch() { return readLatch; }
        public CountDownLatch getWriteLatch() { return writeLatch; }
        protected CountDownLatch resetLatch(CountDownLatch latch) {
            if ( latch==null || latch.getCount() == 0 ) return null;
            else throw new IllegalStateException("Latch must be at count 0");
        }
        public void resetReadLatch() { readLatch = resetLatch(readLatch); }
        public void resetWriteLatch() { writeLatch = resetLatch(writeLatch); }

        protected CountDownLatch startLatch(CountDownLatch latch, int cnt) {
            if ( latch == null || latch.getCount() == 0 ) {
                return new CountDownLatch(cnt);
            }
            else throw new IllegalStateException("Latch must be at count 0 or null.");
        }
        public void startReadLatch(int cnt) { readLatch = startLatch(readLatch,cnt);}
        public void startWriteLatch(int cnt) { writeLatch = startLatch(writeLatch,cnt);}

        protected void awaitLatch(CountDownLatch latch, long timeout, TimeUnit unit) throws InterruptedException {
            if ( latch == null ) throw new IllegalStateException("Latch cannot be null");
            // Note: While the return value is ignored if the latch does time
            //       out, logic further up the call stack will trigger a
            //       SocketTimeoutException
            latch.await(timeout,unit);
        }
        public void awaitReadLatch(long timeout, TimeUnit unit) throws InterruptedException { awaitLatch(readLatch,timeout,unit);}
        public void awaitWriteLatch(long timeout, TimeUnit unit) throws InterruptedException { awaitLatch(writeLatch,timeout,unit);}

        public void setSendfileData(SendfileData sf) { this.sendfileData = sf;}
        public SendfileData getSendfileData() { return this.sendfileData;}

        public void setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
        }
        public long getWriteTimeout() {return this.writeTimeout;}

        private Poller poller = null;
        private int interestOps = 0;
        private CountDownLatch readLatch = null;
        private CountDownLatch writeLatch = null;
        private volatile SendfileData sendfileData = null;
        private long writeTimeout = -1;

    }

    // ------------------------------------------------ Application Buffer Handler
    public static class NioBufferHandler implements ApplicationBufferHandler {
        private ByteBuffer readbuf = null;
        private ByteBuffer writebuf = null;

        public NioBufferHandler(int readsize, int writesize, boolean direct) {
            if ( direct ) {
                readbuf = ByteBuffer.allocateDirect(readsize);
                writebuf = ByteBuffer.allocateDirect(writesize);
            }else {
                readbuf = ByteBuffer.allocate(readsize);
                writebuf = ByteBuffer.allocate(writesize);
            }
        }

        @Override
        public ByteBuffer expand(ByteBuffer buffer, int remaining) {return buffer;}
        @Override
        public ByteBuffer getReadBuffer() {return readbuf;}
        @Override
        public ByteBuffer getWriteBuffer() {return writebuf;}

    }

    // ------------------------------------------------ Handler Inner Interface


    /**
     * Bare bones interface used for socket processing. Per thread data is to be
     * stored in the ThreadWithAttributes extra folders, or alternately in
     * thread local fields.
     */
    public interface Handler extends AbstractEndpoint.Handler {
        public SocketState process(SocketWrapper<NioChannel> socket,
                SocketStatus status);
        public void release(SocketWrapper<NioChannel> socket);
        public void release(SocketChannel socket);
        public SSLImplementation getSslImplementation();
    }


    // ---------------------------------------------- SocketProcessor Inner Class
    /**
     * This class is the equivalent of the Worker, but will simply use in an
     * external Executor thread pool.
     */
    protected class SocketProcessor implements Runnable {

        private KeyAttachment ka = null;
        private SocketStatus status = null;

        public SocketProcessor(KeyAttachment ka, SocketStatus status) {
            reset(ka, status);
        }

        public void reset(KeyAttachment ka, SocketStatus status) {
            this.ka = ka;
            this.status = status;
        }

        @Override
        public void run() {
            NioChannel socket = ka.getSocket();

            // Upgraded connections need to allow multiple threads to access the
            // connection at the same time to enable blocking IO to be used when
            // NIO has been configured
            if (ka.isUpgraded() && SocketStatus.OPEN_WRITE == status) {
                synchronized (ka.getWriteThreadLock()) {
                    doRun();
                }
            } else {
                synchronized (socket) {
                    doRun();
                }
            }
        }

        private void doRun() {
            NioChannel socket = ka.getSocket();
            SelectionKey key = socket.getIOChannel().keyFor(socket.getPoller().getSelector());

            try {
                int handshake = -1;

                try {
                    if (key != null) {
                        // For STOP there is no point trying to handshake as the
                        // Poller has been stopped.
                        if (socket.isHandshakeComplete() ||
                                status == SocketStatus.STOP) {
                            handshake = 0;
                        } else {
                            handshake = socket.handshake(
                                    key.isReadable(), key.isWritable());
                            // The handshake process reads/writes from/to the
                            // socket. status may therefore be OPEN_WRITE once
                            // the handshake completes. However, the handshake
                            // happens when the socket is opened so the status
                            // must always be OPEN_READ after it completes. It
                            // is OK to always set this as it is only used if
                            // the handshake completes.
                            status = SocketStatus.OPEN_READ;
                        }
                    }
                } catch (IOException x) {
                    handshake = -1;
                    if (log.isDebugEnabled()) log.debug("Error during SSL handshake",x);
                } catch (CancelledKeyException ckx) {
                    handshake = -1;
                }
                if (handshake == 0) {
                    SocketState state = SocketState.OPEN;
                    // Process the request from this socket
                    if (status == null) {
                        state = handler.process(ka, SocketStatus.OPEN_READ);
                    } else {
                        state = handler.process(ka, status);
                    }
                    if (state == SocketState.CLOSED) {
                        close(socket, key, SocketStatus.ERROR);
                    }
                } else if (handshake == -1 ) {
                    close(socket, key, SocketStatus.DISCONNECT);
                } else {
                    ka.getPoller().add(socket,handshake);
                }
            } catch (CancelledKeyException cx) {
                socket.getPoller().cancelledKey(key, null);
            } catch (OutOfMemoryError oom) {
                try {
                    oomParachuteData = null;
                    log.error("", oom);
                    socket.getPoller().cancelledKey(key,SocketStatus.ERROR);
                    releaseCaches();
                } catch (Throwable oomt) {
                    try {
                        System.err.println(oomParachuteMsg);
                        oomt.printStackTrace();
                    } catch (Throwable letsHopeWeDontGetHere){
                        ExceptionUtils.handleThrowable(letsHopeWeDontGetHere);
                    }
                }
            } catch (VirtualMachineError vme) {
                ExceptionUtils.handleThrowable(vme);
            } catch (Throwable t) {
                log.error("", t);
                socket.getPoller().cancelledKey(key,SocketStatus.ERROR);
            } finally {
                ka = null;
                status = null;
                //return to cache
                if (running && !paused) {
                    processorCache.push(this);
                }
            }
        }

        private void close(NioChannel socket, SelectionKey key, SocketStatus socketStatus) {
            // Close socket and pool
            try {
                ka.setComet(false);
                if (socket.getPoller().cancelledKey(key, socketStatus) != null) {
                    // SocketWrapper (attachment) was removed from the
                    // key - recycle the key. This can only happen once
                    // per attempted closure so it is used to determine
                    // whether or not to return the key to the cache.
                    // We do NOT want to do this more than once - see BZ
                    // 57340 / 57943.
                    if (running && !paused) {
                        nioChannels.push(socket);
                    }
                }
            } catch (Exception x) {
                log.error("",x);
            }
        }
    }

    // ----------------------------------------------- SendfileData Inner Class
    /**
     * SendfileData class.
     */
    public static class SendfileData {
        // File
        public volatile String fileName;
        public volatile FileChannel fchannel;
        public volatile long pos;
        public volatile long length;
        // KeepAlive flag
        public SendfileKeepAliveState keepAliveState = SendfileKeepAliveState.NONE;
    }
}
