package com.tuling.apm;

import java.lang.instrument.Instrumentation;
import java.util.Properties;

/**
 * Created by Tommy on 2018/3/8.
 */
public class ApmAgent {
    public static void premain(String arg, Instrumentation instrumentation) {
        Properties properties = new Properties();
        ApmContext context = new ApmContext(properties, instrumentation);
    }

}
