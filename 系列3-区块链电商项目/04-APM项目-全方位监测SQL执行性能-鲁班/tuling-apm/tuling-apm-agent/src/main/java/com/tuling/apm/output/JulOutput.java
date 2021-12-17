package com.tuling.apm.output;

import com.tuling.apm.IOutput;

import java.util.logging.Logger;

/**
 * Created by Tommy on 2018/3/8.
 */
public class JulOutput implements IOutput {
    static Logger logger = Logger.getLogger(JulOutput.class.getName());

    @Override
    public boolean out(Object value) {
        logger.info(value.toString());
        return true;
    }
}
