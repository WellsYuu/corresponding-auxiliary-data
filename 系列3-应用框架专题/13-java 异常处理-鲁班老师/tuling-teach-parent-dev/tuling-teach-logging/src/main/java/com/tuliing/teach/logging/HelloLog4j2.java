package com.tuliing.teach.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Tommy on 2017/10/29.
 */
public class HelloLog4j2 {
    public static final Logger logger = LogManager.getLogger(HelloLog4j2.class);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            logger.error(" lof4j2 error");
            logger.info(" lof4j2 info");
        }

    }
}
