package com.gupao.framework.velocity;


import com.gupao.framework.common.utils.EnvUtil;

/**
 * <p>
 * 运行环境<br>
 * prod 线上 ， dev 开发 ， test 测试
 * </p>
 * @author qingyin
 * @Date 2016-08-19
 */
public class RunEnvironment {

	private static final String ONLINE = "prod";

	private static final String DEV = "dev";

	private static final String TEST = "test";

	/**
	 * 运行环境配置变量名
	 */
	private String configEnv = "spring_runmode";

	private static String RUN_MODE = null;


	/**
	 * 获取当前运行模式，默认 DEV 开发模式。
	 * <p>
	 * 首先环境变量中获取，变量名：spring_runmode 变量值：dev <br>
	 * 如果不存在 JVM -D选项 参数中获取，例如：-Dspring_runmode=dev <br>
	 * </p>
	 */
	public String getRunMode() {
		if (RUN_MODE == null ) {
			/* 环境变量获取配置 */
			String mode = System.getenv(getConfigEnv());
			if ( mode == null || "".equals(mode) ) {
				mode = System.getProperty(getConfigEnv());
			}
			if ( mode != null ) {
				if ( ONLINE.equals(mode) ) {
					mode = ONLINE;
				} else if ( DEV.equals(mode) ) {
					mode = DEV;
				} else if ( TEST.equals(mode) ) {
					mode = TEST;
				}
			} else {
				/* 默认 Windows 认为是开发环境 */
				if (EnvUtil.isLinux() ) {
					mode = ONLINE;
				} else {
					mode = DEV;
				}
			}
			System.err.println("-Dspring_runmode=" + mode);
			RUN_MODE = mode;
		}
		return RUN_MODE;
	}


	public String getConfigEnv() {
		return configEnv;
	}


	public void setConfigEnv( String configEnv ) {
		this.configEnv = configEnv;
	}

}
