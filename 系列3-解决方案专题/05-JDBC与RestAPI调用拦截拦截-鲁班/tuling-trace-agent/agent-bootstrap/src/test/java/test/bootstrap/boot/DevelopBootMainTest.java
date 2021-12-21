package test.bootstrap.boot;

import com.cbt.agent.bootstrap.DevelopBootMain;
import org.junit.Test;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.io.File;
public class DevelopBootMainTest {
    @Test
    public void sucessTest() throws Exception {
        ///Users/tommy/git/cbt-agent/
        String home=new File(System.getProperty("user.dir")).getParentFile().toString();
        Instrumentation inst = (Instrumentation) Proxy.newProxyInstance(AgentBootMainTest.class.getClassLoader(),
                new Class[] { Instrumentation.class }, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
        home=home.replaceAll("\\\\","/");
        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append("open=true");
        sbuilder.append(",appId=test_team");
        sbuilder.append(",proKey=test0123");
        sbuilder.append(",proSecret=00215");
        sbuilder.append(",trace.open=true");
        sbuilder.append(",trace.upload.way=logfile");
        sbuilder.append(",trace.upload.path=http://log.yunhou.com/trace/upload");
        sbuilder.append(",dev.base="+home+"/agent-base/target/classes/");
        sbuilder.append(",dev.collects="+home+"/agent-collects/target/classes/&"+home+"/agent-collect-servlet/target/classes/");
        sbuilder.append(",stack.includes=com.*&org.*");
        DevelopBootMain.premain(sbuilder.toString(), inst);
        
    }
}
