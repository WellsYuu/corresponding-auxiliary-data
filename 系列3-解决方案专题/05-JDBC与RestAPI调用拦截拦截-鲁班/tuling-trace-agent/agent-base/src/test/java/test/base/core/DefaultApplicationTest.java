package test.base.core;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.cbt.agent.bootstrap.CbtSessionInfo;
import com.cbt.agent.common.util.Assert;
import com.cbt.agent.core.AgentFinal;
import com.cbt.agent.core.DefaultApplication;

/**
 * Created by tommy on 16/11/2.
 */
public class DefaultApplicationTest implements AgentFinal {

    private CbtSessionInfo session;

    @Before
    public void setUp() throws Exception {
        session = new CbtSessionInfo();
    }

    @Test
    public void testInit() throws Exception {
        DefaultApplication boot = new DefaultApplication();
        Properties pro = new Properties();
        pro.setProperty(OPEN, "true");
        boot.init(session, pro, null, null);
    }

    @Test
    public void testDevcollectTest() throws Exception {
        DefaultApplication boot = new DefaultApplication();
        Properties pro = new Properties();
        pro.setProperty(OPEN, "true");
        String[] collects = { "F:\\git\\cbt-agent\\agent-collect-servlet\\target\\classes\\",
                "F:\\git\\cbt-agent\\agent-collects\\target\\classes\\" };
        boot.init(session, pro, collects, null);
        byte[] result = boot.transform(getClass().getClassLoader(), "com.mysql.jdbc.NonRegisteringDriver", null, null, null);
        Assert.notNull(result);
    }

    @Test
    public void testTransform() throws Exception {
        DefaultApplication boot = new DefaultApplication();
        Properties pro = new Properties();
        pro.setProperty(OPEN, "true");
        boot.init(session, pro, null, null);
        boot.transform(getClass().getClassLoader(), "com.mysql.jdbc.NonRegisteringDriver", null, null, null);
    }

    public static void main(String[] args) {
        DefaultApplicationTest.class.getClassLoader().getResource("com/mysql/jdbc/Driver.class");
    }
}