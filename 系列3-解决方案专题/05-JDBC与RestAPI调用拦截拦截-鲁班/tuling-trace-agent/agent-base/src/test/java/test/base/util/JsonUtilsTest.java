package test.base.util;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import test.BasiceTest;

import com.cbt.agent.common.util.JsonUtils;
import com.cbt.agent.trace.TraceNode;

public class JsonUtilsTest extends BasiceTest {
    @Test
    public void toJsonTest() {
        ArrayList<TraceNode> nodes = newMockTraceNodes(UUID.randomUUID().toString());
        String json = JsonUtils.toJson(nodes, getClass().getClassLoader());
        System.out.println(json);
        Assert.assertNotNull(json);
    }
}
