package com.cbt.agent.bootstrap;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Properties;

/**
 * Created by tommy on 16/11/2.
 */
public interface AgentApplication {
    public void init(CbtSessionInfo session, Properties properties, String[] collectPaths, Instrumentation inst) throws Exception;
    public ClassFileTransformer getTransformer();
}
