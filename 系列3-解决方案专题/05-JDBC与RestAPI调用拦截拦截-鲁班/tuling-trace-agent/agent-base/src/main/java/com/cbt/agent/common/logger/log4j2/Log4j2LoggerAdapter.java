package com.cbt.agent.common.logger.log4j2;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerAdapter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;

public class Log4j2LoggerAdapter implements LoggerAdapter {
	private final LoggerContext ctx;

	/**
	 * 初始化log2配置信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Log4j2LoggerAdapter() {
		String allLogName = "com.cbt.agent";
		String allAppenderName = "allInfo";
        String traceLogName = "com.cbt.agent.transfer.UploadServiceImpl";
		String traceAppenderName="traceInfo";
		ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		Layout layout = PatternLayout.createLayout("%d [%-5p][%t] %m (%C:%F:%L) %n", config, null, null, true, false, null, null);
		TriggeringPolicy tp = SizeBasedTriggeringPolicy.createPolicy("200MB");
		DefaultRolloverStrategy strategy = DefaultRolloverStrategy.createStrategy("20", null, null, null, config);
		Appender allAppender = RollingFileAppender.createAppender("logs/trace/all_hawkeye.log", "logs/trace/" + "%d{yyyy-MM-dd}/all_hawkeye-%d{yyyy-MM-dd-HH}-%i.log", "true", allAppenderName, null, null, null, tp,
				strategy, layout, null, null, null, null, config);
		allAppender.start();
		config.addAppender(allAppender);
		Appender traceAppender=RollingFileAppender.createAppender("logs/trace/trace_hawkeye.log", "logs/trace/" + "%d{yyyy-MM-dd}/trace_hawkeye-%d{yyyy-MM-dd-HH}-%i.log", "true", traceAppenderName, null, null, null, tp,
				strategy, layout, null, null, null, null, config);
		traceAppender.start();
		config.addAppender(traceAppender);
		AppenderRef allRef = AppenderRef.createAppenderRef(allAppenderName, null, null);
		AppenderRef traceRef = AppenderRef.createAppenderRef(traceAppenderName, null, null);
		LoggerConfig allLoggerConfig = LoggerConfig.createLogger("false", Level.ALL, allAppenderName, "true", new AppenderRef[] {allRef}, null, config, null);
		LoggerConfig traceLoggerConfig = LoggerConfig.createLogger("false", Level.ALL, traceAppenderName, "true", new AppenderRef[] {traceRef}, null, config, null);
		allLoggerConfig.addAppender(allAppender, null, null);
		traceLoggerConfig.addAppender(traceAppender, null, null);
		config.addLogger(allLogName, allLoggerConfig);
		config.addLogger(traceLogName, traceLoggerConfig);
		ctx.updateLoggers();
		ctx.getLogger(allLogName);
		ctx.getLogger(traceLogName);
	}

	@Override
	public Logger getLogger(Class<?> key) {
		return new Log4j2Logger(ctx.getLogger(key.getName()));
	}

	@Override
	public Logger getLogger(String key) {
		return new Log4j2Logger(ctx.getLogger(key));
	}

	@Override
	public void setLevel(com.cbt.agent.common.logger.Level level) {
		// TODO Auto-generated method stub

	}

	@Override
	public com.cbt.agent.common.logger.Level getLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFile(File file) {
		// TODO Auto-generated method stub

	}

}
