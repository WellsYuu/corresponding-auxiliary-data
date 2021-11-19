package com.gupao.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by miaoto1 on 2016/8/19.
 */
public class EnvUtil {

   static Logger logger= LoggerFactory.getLogger(EnvUtil.class);

    private static Boolean OS_LINUX = null;

    public EnvUtil() {
    }

    public static boolean isLinux() {
        if(OS_LINUX == null) {
            String OS = System.getProperty("os.name").toLowerCase();
            logger.info("os.name: " + OS);
            if(OS != null && OS.contains("windows")) {
                OS_LINUX = Boolean.valueOf(false);
            } else {
                OS_LINUX = Boolean.valueOf(true);
            }
        }

        return OS_LINUX.booleanValue();
    }

    public static Properties getEnv() {
        Properties prop = new Properties();
        try {
            Process e = null;
            if(isLinux()) {
                e = Runtime.getRuntime().exec("sh -c set");
            } else {
                e = Runtime.getRuntime().exec("cmd /c set");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(e.getInputStream()));
            String line;
            while((line = br.readLine()) != null) {
                int i = line.indexOf("=");
                if(i > -1) {
                    String key = line.substring(0, i);
                    String value = line.substring(i + 1);
                    prop.setProperty(key, value);
                }
            }
            br.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return prop;
    }
}
