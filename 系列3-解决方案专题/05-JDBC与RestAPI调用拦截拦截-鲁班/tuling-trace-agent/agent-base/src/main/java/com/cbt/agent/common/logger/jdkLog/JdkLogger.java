package com.cbt.agent.common.logger.jdkLog;


import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.support.FailsafeLogger;
import java.util.logging.Level;
public class JdkLogger implements Logger {

	private static final String FQCN = FailsafeLogger.class.getName();
	public java.util.logging.Logger logger;

	public JdkLogger(java.util.logging.Logger logger) {
		this.logger = logger;
	}

	public void trace(String msg) {
		log(Level.FINER,msg, null);
	}

	public void trace(Throwable e) {
		log( Level.FINER, e == null ? null : e.getMessage(), e);
	}

	public void trace(String msg, Throwable e) {
		log(Level.FINER, msg, e);
	}

	public void debug(String msg) {
		log( Level.FINE, msg, null);
	}

	public void debug(Throwable e) {
		log(Level.FINE, e == null ? null : e.getMessage(), e);
	}

	public void debug(String msg, Throwable e) {
		log(Level.FINE, msg, e);
	}

	public void info(String msg) {
		log( Level.INFO, msg, null);
	}

	public void info(Throwable e) {
		log(Level.INFO, e == null ? null : e.getMessage(), e);
	}

	public void info(String msg, Throwable e) {
		log( Level.INFO, msg, e);
	}

	public void warn(String msg) {
		log(Level.WARNING, msg, null);
	}

	public void warn(Throwable e) {
		log( Level.WARNING, e == null ? null : e.getMessage(), e);
	}

	public void warn(String msg, Throwable e) {
		log( Level.WARNING, msg, e);
	}

	public void error(String msg) {
		log(Level.SEVERE, msg, null);
	}

	public void error(Throwable e) {
		log(Level.SEVERE, e == null ? null : e.getMessage(), e);
	}

	public void error(String msg, Throwable e) {
		log( Level.SEVERE, msg, e);
	}

	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINER);
	}

	public boolean isDebugEnabled() {
		return  logger.isLoggable(Level.FINE);
	}

	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	// from commons logging. This would be my number one reason why java.util.logging
	// is bad - design by committee can be really bad ! The impact on performance of
	// using java.util.logging - and the ugliness if you need to wrap it - is far
	// worse than the unfriendly and uncommon default format for logs.

	private void log(java.util.logging.Level level, String msg, Throwable ex) {
		if (logger.isLoggable(level)) {
			// Hack (?) to get the stack trace.
			Throwable dummyException=new Throwable();
			StackTraceElement locations[]=dummyException.getStackTrace();
			// Caller will be the third element
			String cname = "unknown";
			String method = "unknown";
			if (locations != null && locations.length >2) {
				StackTraceElement caller = locations[2];
				cname = caller.getClassName();
				method = caller.getMethodName();
			}
			if (ex==null) {
				logger.logp(level, cname, method, msg);
			} else {
				logger.logp(level, cname, method, msg, ex);
			}
		}
	}



}
