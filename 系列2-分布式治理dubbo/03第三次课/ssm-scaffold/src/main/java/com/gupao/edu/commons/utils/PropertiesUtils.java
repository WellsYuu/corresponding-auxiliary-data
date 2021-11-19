package com.gupao.edu.commons.utils;


import org.apache.commons.configuration.PropertiesConfiguration;


public class PropertiesUtils {

	public static PropertiesConfiguration smsPropertiesConfiguration = null;

	public static PropertiesConfiguration configPropertiesConfiguration = null; 

	public static PropertiesConfiguration getConfigPropertiesConfiguration(){
		if(null == configPropertiesConfiguration){
			try {
				configPropertiesConfiguration  = new PropertiesConfiguration("conf/prod/application.properties");
			} catch (org.apache.commons.configuration.ConfigurationException e) {
				e.printStackTrace();
			}
		}
		return configPropertiesConfiguration;
	}

}
