package test.base.core;

import com.cbt.agent.core.AgentLoader;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

/**
 * Created by tommy on 16/11/5.
 */
public class AgentLoaderTest {

    @Test
    public void testLoadItemByJar() throws Exception {
        AgentLoader loader = new AgentLoader();
        URL url = new URL("file:/Users/tommy/git/cbt-agent/out/cbt-agent-collects.jar");
        loader.loadItemByJar(url);
        Assert.assertTrue(loader.getAgentPlugins().length > 0);
    }

    @Test
    public void testLoadClassByte() throws Exception {
        AgentLoader loader = new AgentLoader();
        URL url = new URL("file:/Users/tommy/git/cbt-agent/out/cbt-agent-collects.jar");
        loader.loadItemByJar(url);
        byte[] bytes = loader.loadClassByte("com.mysql.jdbc.NonRegisteringDriver", getClass().getClassLoader());
        Assert.assertNotNull(bytes);
    }

    @Test
    public void testLoadClass() throws Exception {
        AgentLoader loader = new AgentLoader();
        URL url = new URL("file:/Users/tommy/git/cbt-agent/out/cbt-agent-collects.jar");
        loader.loadItemByJar(url);
        Class cla = loader.loadClass("com.mysql.jdbc.NonRegisteringDriver", getClass().getClassLoader());
        Assert.assertNotNull(cla);
    }
}