package test.base.trace;

import com.cbt.agent.trace.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by tommy on 16/10/21.
 */
public class TraceContextTest {
    Properties properties =null;
    TraceSession session;
    TraceContext context;
    @Before
    public void setUp() throws Exception {
        properties = new Properties();
        properties.setProperty("trace.upload.way", "http");
        properties.setProperty("trace.upload.http.url", "http://120.76.224.136:10000/bug-microscope/trace/upload");
        properties.setProperty("trace.open", "true");
        properties.setProperty("trace.upload.threads", "3");
        properties.setProperty("server.appId", "66");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void contextInitTest() {
        context=new TraceContext(properties);
        assertTrue(context.isActive());
    }

    @org.junit.Test
    public void testOpenTrace() throws Exception {
        context=new TraceContext(properties);
        TraceRequest request=new TraceRequest();
        request.setTraceId(UUID.randomUUID().toString());
        request.setParentRpcId("0.1");
        session = context.openTrace(request);
        assertTrue(session!=null);
    }



    @org.junit.Test
    public void testStoreNode() throws Exception {
        TraceContext context=new TraceContext(properties);
        TraceNode traceNode=new TraceNode();
        traceNode.setTraceId(UUID.randomUUID().toString());
        traceNode.setResultState("ok");
        traceNode.setBeginTime(System.currentTimeMillis());
        traceNode.setRpcId("0.1");
        context.storeNode(traceNode);
        TraceBeanWapper wap = new TraceBeanWapper(traceNode);
        wap.setTheUploadUrl("http://127.0.0.1:8767/trace");
        context.storeNode(wap);
    }



    @org.junit.Test
    public void testCloseTrace() throws Exception {
        context=new TraceContext(properties);
        TraceRequest request=new TraceRequest();
        request.setTraceId(UUID.randomUUID().toString());
        request.setParentRpcId("0.1");
        session = context.openTrace(request);
        assertTrue(session!=null);
        context.closeTrace(session);
    }

}