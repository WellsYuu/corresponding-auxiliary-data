package com.tuling.demo.test;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import org.junit.Ignore;
import org.junit.Test;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * Created by Tommy on 2018/3/6.
 */
public class MyAgentTest {

    @Ignore
    @Test
    public void premainTest() {

    }



    public static void main(String[] args) throws InterruptedException {
        System.out.println("输出进程ID："+ ManagementFactory.getRuntimeMXBean().getName());
        while (true) {
            Thread.sleep(100);
        }
    }

    @Ignore
    @Test
    public void agentAttach() throws Exception {
        String targetPid = "33056";
        VirtualMachine vm = VirtualMachine.attach(targetPid);
        vm.loadAgent(System.getProperty("user.dir") + "/target/tuling-apm-demo-1.0-SNAPSHOT.jar",
                "toagent");
    }
}
