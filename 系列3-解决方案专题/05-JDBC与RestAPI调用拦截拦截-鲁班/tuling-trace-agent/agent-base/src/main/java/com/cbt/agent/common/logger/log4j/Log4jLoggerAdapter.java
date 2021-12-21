package com.cbt.agent.common.logger.log4j;

import com.cbt.agent.common.logger.Level;
import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerAdapter;
import com.cbt.agent.transfer.UploadServiceImpl;
import org.apache.log4j.RollingFileAppender;

import java.io.File;

public class Log4jLoggerAdapter implements LoggerAdapter {
	private RollingFileAppender traceAppender;
	private RollingFileAppender allAppender;

	public Log4jLoggerAdapter() {
		traceAppender = new RollingFileAppender();
		traceAppender.setName("traceAppender");
		traceAppender.setFile("logs/trace/trace_hawkeye.log");
		org.apache.log4j.PatternLayout patternlayout = new org.apache.log4j.PatternLayout();
		patternlayout.setConversionPattern("[%5p] [%t] %l %d{yyyy-MM-dd HH:mm:ss.SSS}%n%m%n");
		traceAppender.setLayout(patternlayout);
		traceAppender.setThreshold(org.apache.log4j.Level.INFO);
		traceAppender.setEncoding("UTF-8");
		traceAppender.setAppend(true);
		traceAppender.setMaxFileSize("200MB");
		traceAppender.setMaxBackupIndex(20);
		traceAppender.activateOptions();

		allAppender = new RollingFileAppender();
		allAppender.setName("allAppender");
		allAppender.setFile("logs/trace/all_hawkeye.log");
		allAppender.setLayout(patternlayout);
		allAppender.setThreshold(org.apache.log4j.Level.INFO);
		allAppender.setEncoding("UTF-8");
		allAppender.setAppend(true);
		allAppender.setMaxFileSize("200MB");
		allAppender.setMaxBackupIndex(20);
		allAppender.activateOptions();
	}

	@Override
	public Logger getLogger(Class<?> key) {
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(key);
		if (UploadServiceImpl.class.isAssignableFrom(key)) {
			logger.addAppender(traceAppender);
		} else {
			logger.addAppender(allAppender);
		}
		logger.setAdditivity(false);
		return new Log4jLogger(logger);
	}

	@Override
	public Logger getLogger(String key) {
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(key);
		if (UploadServiceImpl.class.getName().equals(key)) {
			logger.addAppender(traceAppender);
		} else {
			logger.addAppender(allAppender);
		}
		logger.setAdditivity(false);
		return new Log4jLogger(logger);
	}

	public void setLevel(Level level) {
		// TODO Auto-generated method stub

	}

	public Level getLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public File getFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFile(File file) {
		// TODO Auto-generated method stub

	}

}
