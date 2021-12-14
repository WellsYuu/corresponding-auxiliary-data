package com.tuling.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Tommy on 2017/11/27.
 */
public class HelloJcl {
    public static void main(String[] args) {
        Log log = LogFactory.getLog(HelloJcl.class);
        log.error("error message jcl");
}
}
