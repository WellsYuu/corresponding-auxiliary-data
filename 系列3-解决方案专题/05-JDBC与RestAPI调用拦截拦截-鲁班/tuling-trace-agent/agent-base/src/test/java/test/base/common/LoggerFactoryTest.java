package test.base.common;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import org.junit.Test;

/**
 * Created by tommy on 16/11/12.
 */
public class LoggerFactoryTest {
    @Test
    public void testGetlogger(){
        Logger logger = LoggerFactory.getLogger(LoggerFactoryTest.class);
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        logger.error(new Exception("exception"));
    }
}
