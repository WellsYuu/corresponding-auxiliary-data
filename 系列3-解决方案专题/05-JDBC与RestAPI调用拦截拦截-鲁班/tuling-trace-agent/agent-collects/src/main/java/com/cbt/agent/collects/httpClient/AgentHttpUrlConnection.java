package com.cbt.agent.collects.httpClient;


import com.cbt.agent.collect.CollectHandle;
import com.cbt.agent.collect.CollectHandleBeanFactory;
import com.cbt.agent.collect.Event;
import com.cbt.agent.collect.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.util.List;
import java.util.Map;

/**
 * Created by tommy on 16/7/23.
 */
public class AgentHttpUrlConnection extends HttpURLConnection {
    private final CollectHandle handle;
    HttpURLConnection target;
    private Event event;

    public static final String getInputStream = "getInputStream";
    public static final String getOutputStream = "getOutputStream";
    public static final String connect = "connect";
    public static final String disconnect = "disconnect";

    HttpInputStream httpInputStream;
    HttpOutputStream httpOutputStream;

    /**
     * Constructor for the HttpURLConnection.
     *
     * @param u the URL
     */
    public AgentHttpUrlConnection(HttpURLConnection target, URL u) {
        super(u);
        this.target = target;
        handle = CollectHandleBeanFactory.getBean(HttpURLConnectionHandle.class);
        InParams _inParams = new InParams();
        OutResult _outResult = new OutResult();
        _inParams.className = HttpURLConnection.class.getName();
        _inParams.methodName="AgentHttpUrlConnection";
        _outResult.className=_inParams.className;
        _outResult.methodName= _inParams.methodName;
        _inParams.args=new Object[]{target,u};
        _outResult.args=_inParams.args;
        _outResult.result=this;
        _inParams.put("agentHttpConn",this);
        Event newEvent = handle.invokerBefore(event, _inParams);
        handle.invokerAfter(newEvent,_outResult);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    private Object proxyInvoke(String methodSign, Object... args) throws IOException {
        InParams _inParams = new InParams();
        OutResult _outResult = new OutResult();
        _inParams.className = HttpURLConnection.class.getName();
        _inParams.methodName = methodSign;
        _outResult.className = HttpURLConnection.class.getName();
        _outResult.methodName = methodSign;
        _inParams.args = args;
        _outResult.args = args;
        Event newEvent = handle.invokerBefore(event, _inParams);
        try {
            _outResult.result = invoke(methodSign, args);
        } catch (IOException e) {
            _outResult.error = e;
            throw e;
        } catch (Throwable e) {
            _outResult.error = e;
            throw e;
        } finally {
            _outResult.others.put("agentHttpConn",this);
            handle.invokerAfter(newEvent != null ? newEvent : event, _outResult);
        }
        return _outResult.result;
    }

    private Object invoke(String methodSign, Object... args) throws IOException {
        if (getInputStream.equals(methodSign)) {
            if (httpInputStream == null)
                httpInputStream = new HttpInputStream(target.getInputStream());
            return httpInputStream;
        } else if (getOutputStream.equals(methodSign)) {
            if (httpOutputStream == null)
                httpOutputStream = new HttpOutputStream(target.getOutputStream());
            return httpOutputStream;
        } else if (connect.equals(methodSign)) {
            target.connect();
            return null;
        } else if (disconnect.equals(methodSign)) {
            target.disconnect();
            return null;
        }
        return null;
    }

    @Override
    public void disconnect() {
        try {
            proxyInvoke(disconnect);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {
        proxyInvoke(connect);
    }

    @Override
    public String getHeaderFieldKey(int n) {
        return target.getHeaderFieldKey(n);
    }


    @Override
    public void setFixedLengthStreamingMode(int contentLength) {
        target.setFixedLengthStreamingMode(contentLength);
    }

    @Override
    public void setFixedLengthStreamingMode(long contentLength) {
        target.setFixedLengthStreamingMode(contentLength);
    }

    @Override
    public void setChunkedStreamingMode(int chunklen) {
        target.setChunkedStreamingMode(chunklen);
    }

    @Override
    public String getHeaderField(int n) {
        return target.getHeaderField(n);
    }

    @Override
    public void setInstanceFollowRedirects(boolean followRedirects) {
        target.setInstanceFollowRedirects(followRedirects);
    }

    @Override
    public boolean getInstanceFollowRedirects() {
        return target.getInstanceFollowRedirects();
    }

    @Override
    public void setRequestMethod(String method) throws ProtocolException {
        target.setRequestMethod(method);
    }

    @Override
    public String getRequestMethod() {
        return target.getRequestMethod();
    }

    @Override
    public int getResponseCode() throws IOException {
        return target.getResponseCode();
    }

    @Override
    public String getResponseMessage() throws IOException {
        return target.getResponseMessage();
    }

    @Override
    public long getHeaderFieldDate(String name, long Default) {
        return target.getHeaderFieldDate(name, Default);
    }

    @Override
    public Permission getPermission() throws IOException {
        return target.getPermission();
    }

    @Override
    public InputStream getErrorStream() {
        return target.getErrorStream();
    }

    @Override
    public void setConnectTimeout(int timeout) {
        target.setConnectTimeout(timeout);
    }

    @Override
    public int getConnectTimeout() {
        return target.getConnectTimeout();
    }

    @Override
    public void setReadTimeout(int timeout) {
        target.setReadTimeout(timeout);
    }

    @Override
    public int getReadTimeout() {
        return target.getReadTimeout();
    }

    @Override
    public URL getURL() {
        return target.getURL();
    }

    @Override
    public int getContentLength() {
        return target.getContentLength();
    }

    @Override
    public long getContentLengthLong() {
        return target.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        return target.getContentType();
    }

    @Override
    public String getContentEncoding() {
        return target.getContentEncoding();
    }

    @Override
    public long getExpiration() {
        return target.getExpiration();
    }

    @Override
    public long getDate() {
        return target.getDate();
    }

    @Override
    public long getLastModified() {
        return target.getLastModified();
    }

    @Override
    public String getHeaderField(String name) {
        return target.getHeaderField(name);
    }

    @Override
    public Map<String, List<String>> getHeaderFields() {
        return target.getHeaderFields();
    }

    @Override
    public int getHeaderFieldInt(String name, int Default) {
        return target.getHeaderFieldInt(name, Default);
    }

    @Override
    public long getHeaderFieldLong(String name, long Default) {
        return target.getHeaderFieldLong(name, Default);
    }

    @Override
    public Object getContent() throws IOException {
        return target.getContent();
    }

    @Override
    public Object getContent(Class[] classes) throws IOException {
        return target.getContent(classes);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return (InputStream) proxyInvoke(getInputStream);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return (OutputStream) proxyInvoke(getOutputStream);
    }

    @Override
    public String toString() {
        return target.toString();
    }

    @Override
    public void setDoInput(boolean doinput) {
        target.setDoInput(doinput);
    }

    @Override
    public boolean getDoInput() {
        return target.getDoInput();
    }

    @Override
    public void setDoOutput(boolean dooutput) {
        target.setDoOutput(dooutput);
    }

    @Override
    public boolean getDoOutput() {
        return target.getDoOutput();
    }

    @Override
    public void setAllowUserInteraction(boolean allowuserinteraction) {
        target.setAllowUserInteraction(allowuserinteraction);
    }

    @Override
    public boolean getAllowUserInteraction() {
        return target.getAllowUserInteraction();
    }

    @Override
    public void setUseCaches(boolean usecaches) {
        target.setUseCaches(usecaches);
    }

    @Override
    public boolean getUseCaches() {
        return target.getUseCaches();
    }

    @Override
    public void setIfModifiedSince(long ifmodifiedsince) {
        target.setIfModifiedSince(ifmodifiedsince);
    }

    @Override
    public long getIfModifiedSince() {
        return target.getIfModifiedSince();
    }

    @Override
    public boolean getDefaultUseCaches() {
        return target.getDefaultUseCaches();
    }

    @Override
    public void setDefaultUseCaches(boolean defaultusecaches) {
        target.setDefaultUseCaches(defaultusecaches);
    }

    @Override
    public void setRequestProperty(String key, String value) {
        target.setRequestProperty(key, value);
    }

    @Override
    public void addRequestProperty(String key, String value) {
        target.addRequestProperty(key, value);
    }

    @Override
    public String getRequestProperty(String key) {
        return target.getRequestProperty(key);
    }

    @Override
    public Map<String, List<String>> getRequestProperties() {
        return target.getRequestProperties();
    }

    class HttpInputStream extends BufferedInputStream {
        ByteArrayOutputStream copy = new ByteArrayOutputStream();

        public HttpInputStream(InputStream in) {
            super(in);
        }

        @Override
        public synchronized int read(byte[] b, int off, int len) throws IOException {
            int i = super.read(b, off, len);
            if (i > 0)
            copy.write(b, off, i);
            return i;
        }

        @Override
        public synchronized int read() throws IOException {
            int i = super.read();
            if (i != -1)
            copy.write(i);
            return i;
        }
    }

    public byte[] getInputBytes() {
        if (httpInputStream != null) {
            return httpInputStream.copy.toByteArray();
        }
        return new byte[]{};
    }

    public byte[] getOutputBytes() {
        if (httpOutputStream != null) {
            return httpOutputStream.copy.toByteArray();
        }
        return new byte[]{};
    }

    class HttpOutputStream extends BufferedOutputStream {
        ByteArrayOutputStream copy = new ByteArrayOutputStream();

        public HttpOutputStream(OutputStream out) {
            super(out);
        }

        @Override
        public synchronized void write(int b) throws IOException {
            super.write(b);
            copy.write(b);
        }

        @Override
        public synchronized void write(byte[] b, int off, int len) throws IOException {
            super.write(b, off, len);
            copy.write(b, off, len);
        }

        @Override
        public synchronized void flush() throws IOException {
            super.flush();
        }

    }


}
