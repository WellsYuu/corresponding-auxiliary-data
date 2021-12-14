package com.tuling.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tommy on 2017/11/27.
 */
public class HelloSlf4j {
    private static Logger logger = LoggerFactory.getLogger(HelloSlf4j.class);

    public static void main(String[] args) {
      /*  org.apache.log4j.Logger logger1=   org.apache.log4j.Logger.getLogger(HelloSlf4j.class);
        logger1.error("log4-error - abcd");
        logger1.info("log4- info -abcd");*/


        for (int i = 0; i < 2000; i++) {
            logger.error("slf4j- error - abcd");
            logger.info("slf4j- info -abcd");
        }
    }
}
