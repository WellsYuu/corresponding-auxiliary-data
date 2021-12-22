package test.base.util;

import com.cbt.agent.common.util.JarUtil;
import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by tommy on 16/11/5.
 */
public class JarUtilTest {

    @Test
    public void testAddUrl() throws Exception {
        URL url = new URL("jar:file:/Users/tommy/git/cbt-agent/out/cbt-agent-bootstrap.jar!/cbt-agent-collects.jar");
        URLClassLoader loader = new URLClassLoader(new URL[0]);
        JarUtil.addUrl(url, loader);
        Class<?> c = loader.loadClass("com.cbt.agent.collects.dubbo.DubboConsumerMonitorHandle");
        System.out.println(c.getName());
    }
}