package test.base.trace;

import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceRequest;
import com.cbt.agent.trace.TraceSession;
import org.junit.Test;
import test.BasiceTest;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by tommy on 16/10/21.
 */
public class TraceSessionTest extends BasiceTest{

    @Test
    public void testAddNode() throws Exception {
        TraceRequest request=new TraceRequest();
        request.setTraceId(UUID.randomUUID().toString());
        request.setParentRpcId("0.1");
        TraceSession session = context.openTrace(request);
        String traceId=TraceSession.createTraceId();
        for(TraceNode node:newMockTraceNodes(traceId)){
            session.addNode(node);
        }
        session.addTraceFrom(newMockTraceFrom(traceId));
        // 延后1秒结束,以有足够时间进行上传
        Thread.sleep(1000);
    }

    @Test
    public void testAddTraceFrom() throws Exception {

    }
}