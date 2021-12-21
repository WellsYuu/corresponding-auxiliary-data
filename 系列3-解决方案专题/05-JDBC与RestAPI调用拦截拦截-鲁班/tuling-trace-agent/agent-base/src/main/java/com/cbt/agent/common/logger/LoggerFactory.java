package com.cbt.agent.common.logger;

import com.cbt.agent.common.logger.jdkLog.JdkLogger;
import com.cbt.agent.common.logger.log4j.Log4jLoggerAdapter;
import com.cbt.agent.common.logger.log4j2.Log4j2LoggerAdapter;
import com.cbt.agent.common.logger.support.FailsafeLogger;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 日志输出器工厂
 * 
 * @author sunli
 */
public class LoggerFactory {

	private LoggerFactory() {
	}
    
//	private static volatile LoggerAdapter LOGGER_ADAPTER;

	private static final ConcurrentMap<String, FailsafeLogger> LOGGERS = new ConcurrentHashMap<String, FailsafeLogger>();

	// 查找常用的日志框架
	static {
           /* try {
                setLoggerAdapter(new Log4j2LoggerAdapter());
        } catch (Throwable e1) {
            LoggerFactory.class.getClassLoader();
                System.err.println("Failed to load log4j2.xml in classpath for log4j2 logging LOCAL_CONFIG.");
//            e1.printStackTrace();
            LoggerFactory.class.getClassLoader();
                try {
                    setLoggerAdapter(new Log4jLoggerAdapter());
                } catch (Throwable e2) {
                    System.err.println("Failed to load log4j.properties in classpath for  log4j logging LOCAL_CONFIG");
//                e2.printStackTrace();
                }
            }*/
		
        // LoggerContext ctx = (LoggerContext)
        // org.apache.logging.log4j.LogManager.getContext(false);
        // ctx.getConfiguration().addFilter(new Log4j2Filter());
	}

	/**
	 * 设置日志输出器供给器
	 * 
	 * @param loggerAdapter
	 *            日志输出器供给器
	 */
	/*public static void setLoggerAdapter(LoggerAdapter loggerAdapter) {
		if (loggerAdapter != null) {
			Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());
			logger.info("using logger: " + loggerAdapter.getClass().getName());
			LoggerFactory.LOGGER_ADAPTER = loggerAdapter;
			for (Map.Entry<String, FailsafeLogger> entry : LOGGERS.entrySet()) {
				entry.getValue().setLogger(LOGGER_ADAPTER.getLogger(entry.getKey()));
			}
		}
	}*/

	/**
	 * 获取日志输出器
	 * 
	 * @param key
	 *            分类键
	 * @return 日志输出器, 后验条件: 不返回null.
	 */
	public static Logger getLogger(final Class<?> key) {
		FailsafeLogger logger = LOGGERS.get(key.getName());
		if (logger == null) {
			JdkLogger jdkLogger=new JdkLogger(java.util.logging.Logger.getLogger(key.getName()));
			LOGGERS.putIfAbsent(key.getName(), new FailsafeLogger(jdkLogger));
			logger = LOGGERS.get(key.getName());
		}
		return logger;
	}

	/**
	 * 获取日志输出器
	 * 
	 * @param key
	 *            分类键
	 * @return 日志输出器, 后验条件: 不返回null.
	 */
	public static Logger getLogger(String key) {
		FailsafeLogger logger = LOGGERS.get(key);
		if (logger == null) {
			JdkLogger jdkLogger=new JdkLogger(java.util.logging.Logger.getLogger(key));
			LOGGERS.putIfAbsent(key, new FailsafeLogger(jdkLogger));
			logger = LOGGERS.get(key);
		}
		return logger;
	}

	/**
	 * 动态设置输出日志级别
	 * 
	 * @param level
	 *            日志级别
	 *//*
	public static void setLevel(Level level) {
		LOGGER_ADAPTER.setLevel(level);
	}*/

	/**
	 * 获取日志级别
	 * 
	 * @return 日志级别
	 *//*
	public static Level getLevel() {
		return LOGGER_ADAPTER.getLevel();
	}*/

	/**
	 * 获取日志文件
	 * 
	 * @return 日志文件
	 *//*
	public static File getFile() {
		return LOGGER_ADAPTER.getFile();
	}*/

}
