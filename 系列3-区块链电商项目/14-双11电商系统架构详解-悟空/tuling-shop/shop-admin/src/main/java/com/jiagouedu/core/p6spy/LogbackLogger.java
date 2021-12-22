package com.jiagouedu.core.p6spy;

import com.p6spy.engine.common.P6SpyProperties;
import com.p6spy.engine.logging.appender.FormattedLogger;
import com.p6spy.engine.logging.appender.P6Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogbackLogger extends FormattedLogger implements P6Logger {
    protected String lastEntry;
    private static Logger log;

    public LogbackLogger() {
        P6SpyProperties properties = new P6SpyProperties();
        log = LoggerFactory.getLogger("p6spy");
//        log.setAdditivity(false);
    }

    public void logException(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        this.logText(sw.toString());
    }


    @Override
    public void logSQL(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        if (!"resultset".equals(category)) log.debug(sql);
    }

    public void logText(String text) {
        log.debug(text);
        this.setLastEntry(text);
    }
}
