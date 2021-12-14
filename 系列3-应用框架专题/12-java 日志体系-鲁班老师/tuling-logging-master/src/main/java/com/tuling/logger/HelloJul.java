package com.tuling.logger;


/**
 * Created by Tommy on 2017/11/27.
 */
public class HelloJul {
    public static void main(String[] args) {
        java.util.logging.Logger logger
                = java.util.logging.Logger.getLogger(HelloJul.class.getName());
        logger.info("hello jurl info222");
    }
}
