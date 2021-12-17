package com.tuling.apm;

import com.tuling.apm.collects.ServiceCollect;
import com.tuling.apm.filter.JSONFormat;
import com.tuling.apm.output.JulOutput;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 图灵APM上下文
 * Created by Tommy on 2018/3/8.
 */
public class ApmContext {
    private Instrumentation instrumentation;
    private Properties properties;
    List<ICollect> collects = new ArrayList();
    IFilter filter;
    IOutput output;

    public ApmContext(Properties properties, Instrumentation instrumentation) {
        this.properties = properties;
        this.instrumentation = instrumentation;
        collects.add(new ServiceCollect(this, instrumentation));
        filter = new JSONFormat();
        output = new JulOutput();
    }

    // 递交采集结果
    public void submitCollectResult(Object value) {
        // TODO 基于线程后台执行
        value = filter.doFilter(value);
        output.out(value);
    }

    public String getConfig(String key) {
        return null;
    }

    public List<ICollect> getCollects() {
        return collects;
    }
}
