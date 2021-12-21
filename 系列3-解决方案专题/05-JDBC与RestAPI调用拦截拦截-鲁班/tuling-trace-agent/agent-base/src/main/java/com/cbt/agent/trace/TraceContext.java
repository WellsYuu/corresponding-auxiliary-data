package com.cbt.agent.trace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

import com.cbt.agent.collect.CollectHandleProxy;
import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.common.util.Assert;
import com.cbt.agent.common.util.ClassUtils;
import com.cbt.agent.common.util.StringUtils;
import com.cbt.agent.core.AgentFinal;
import com.cbt.agent.transfer.TransferTask;
import com.cbt.agent.transfer.UploadService;
import com.cbt.agent.transfer.UploadServiceImpl;

/**
 * 构建监控上下文
 * 
 * @since 0.1.0
 */
public class TraceContext implements AgentFinal {
        public static final String TID = "traceId";
        public static final String PID = "parentId";
        public static final String PROPERTIES = "properties";
        public static final String TRACE_ID = "trace.id";
        public static final String TRACE_LEVEL = "trace.level";
        public static final String UPLOAD_PATH = "trace.upload.path";
        public static final String DEBUG_ID = "trace-debug-id";
        public static final String TRACE_DEBUG = "trace.debug";
        public static final String DEBUG_CONSOLE_KEY = "cbtdebug_";
    private static TraceContext defaultContext;

        private final Properties properties;
        private UploadService service;

        
        /**
         * 上传线程数
         */
        private int uploadThreads = 5;
        /**
         * 存放采取后的上报数据队列
         */
    private final ArrayBlockingQueue<Object> nodeQueue;
    private final Logger logger;
        /**
         * 存放session数据
         */
        private static final ThreadLocal<TraceSession> traceThreadLocalSession = new ThreadLocal<TraceSession>();

        static {
                /*
                 * 构建默认 Context
                 */

                if ( Boolean.valueOf(LOCAL_CONFIG.getProperty("trace.open", "false")))
                        defaultContext = new TraceContext(LOCAL_CONFIG);
                else
                        defaultContext = null;
        }

        public TraceContext(Properties properties) {
            logger = LoggerFactory.getLogger(CollectHandleProxy.class);
                this.properties = properties;
                nodeQueue = new ArrayBlockingQueue<Object>(Integer.parseInt(properties.getProperty("trace.task.maxSize", "1000")));

        
                // 初始上传服务
                UploadServiceImpl serviceImpl = new UploadServiceImpl();
                serviceImpl.setDefaultUpload(properties.getProperty("trace.upload.way", "http"));
                serviceImpl.setHttpUrl(properties.getProperty("trace.upload.path", "http://log.cbt.api/upload"));
                service = serviceImpl;
                try {
                        uploadThreads = Integer.parseInt(properties.getProperty("trace.upload.threads", "5"));
                } catch (NumberFormatException e) {
                        new RuntimeException("参数 ‘trace.upload.threads’ 必须为数字", e).printStackTrace();
                        uploadThreads = 5;
                }
                // 启动上传线程
                for (int i = 0; i < uploadThreads; i++) {
                        TransferTask task = new TransferTask(this, "TransferTask-Thread-" + i);
                        task.setDaemon(true);
                        task.start();
                }
        }

        /**
         * 根据请求在本机的当前线程开启一个监控会话
         * 
         * @return
         */
        public TraceSession openTrace(TraceRequest request) {
                TraceSession session = createSession(request);
                traceThreadLocalSession.set(session);
            StackNode rootNode=new StackNode();
            StackSession stackSession=new StackSession(rootNode);
                return session;
        }

    /**
     * 根据请求在本机的当前线程开启一个监控会话
     *
     * @param request
     * @param rooNode 如果不为空表示开启求堆栈跟踪
     * @return
     */
    public TraceSession openTrace(TraceRequest request, StackNode rooNode) {
        TraceSession session = createSession(request);
        traceThreadLocalSession.set(session);
        StackSession stackSession = new StackSession(rooNode);
        System.getProperties().put(Thread.currentThread(),stackSession);
//        System.getProperties().put(stackSession,stackSession.errorHand);
        return session;
    }

        /**
         * 结束数据采取
         * 
         * @return
         */
        public boolean closeTrace(TraceSession session) {
                Assert.isTrue(traceThreadLocalSession.get() == session);
                destorySession();
                return true;
        }

        /**
         * 监控服务是否处于激活状态 该参数表示可人员干预指定节点处于暂停或工作状态。
         * 
         * @return
         */
        public boolean isActive() {
                return true;
        }

        /**
         * 创建session
         * 
         * @return
         * 
         * @return
         */
        private TraceSession createSession(TraceRequest request) {
                TraceSession session = new TraceSession(this, request);
                return session;
        }

        /**
         * 获取session
         * 
         * @return
         */
        public TraceSession getCurrentSession() {
                return traceThreadLocalSession.get();
        }



        /**
         * 销毁session
         */
        private void destorySession() {
                traceThreadLocalSession.remove();
            // 移除堆栈跟踪会话
            if(System.getProperties().containsKey(Thread.currentThread())){
               /* StackSession stackSession= (StackSession) System.getProperties().get(Thread.currentThread());
                System.out.println("关闭stack session");
                stackSession.printStack(System.out);*/
                System.getProperties().remove(Thread.currentThread());
            }
        }

        /**
         * 保存到日志队列中,如果对列已满将直接放弃
         * 
         * @param traceNode
         */
        public void storeNode(Object traceNode) {
                if (!(traceNode instanceof TraceNode || traceNode instanceof TraceBeanWapper || traceNode instanceof TraceFrom)) {
                        throw new IllegalArgumentException();
                }
                if (!nodeQueue.offer(traceNode)) {
                    logger.error("日志上传失败，对列已满:" + traceNode.toString());
                }
        }

        /**
         * 节点上传
         * 
         *
         */
        public int uploadNode(int size) {
                List<Object> list = new ArrayList<Object>(size + 1);
                Object first = null;
                try {
                        first = nodeQueue.take();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                        return 0;
                }
                nodeQueue.drainTo(list, size);
                list.add(first);

                List<TraceNode> nodes = new ArrayList<>(size);
                List<TraceFrom> infos = new ArrayList<>(size);

                for (Object obj : list) {
                        if (obj instanceof TraceBeanWapper) {
                                if (!uploadWapperBean((TraceBeanWapper) obj)) { // 根据url指定上传,若失败则
                                                                                                                                // 使用默认方式上传
                                        obj = ((TraceBeanWapper) obj).getBean();
                                }
                        }
                        if (obj instanceof TraceNode) {
                                nodes.add((TraceNode) obj);
                        } else if (obj instanceof TraceFrom) {
                                infos.add((TraceFrom) obj);
                        }
                }
                if (!nodes.isEmpty() || !infos.isEmpty()) service.uploadByDefault(nodes, infos);
                return list.size();
        }

        // 指定路径上传
        private boolean uploadWapperBean(TraceBeanWapper wapNode) {
                String upUrl = wapNode.getTheUploadUrl();
                if (!StringUtils.hasText(upUrl)) {
                        return false;
                }
                if (upUrl.startsWith("http")) {
                        service.uploadByHttp(wapNode.getNode() == null ? null : Arrays.asList(wapNode.getNode()), wapNode.getFrom() == null ? null : Arrays.asList(wapNode.getFrom()), upUrl);
                        return true;
                }
                return false;
        }

        public StackSession getStackSession(){
              return (StackSession) System.getProperties().get(Thread.currentThread());
        }

        // 是否开启默认跟踪链
        public static boolean isDefaultOpen() {
                return defaultContext != null;
        }

        // 获取默认的跟踪链上下文
        public static final TraceContext getDefault() {
                return defaultContext;
        }

        /**
         * 获取所有配置属性
         * 
         * @return
         */
        public Properties getAllProperties() {
                return properties;
        }

    public static void setDefaultContext(TraceContext defaultContext) {
        TraceContext.defaultContext = defaultContext;
    }

}
