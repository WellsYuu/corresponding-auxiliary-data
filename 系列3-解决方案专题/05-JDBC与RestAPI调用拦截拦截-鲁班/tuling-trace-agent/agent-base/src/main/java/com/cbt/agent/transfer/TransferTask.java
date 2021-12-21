package com.cbt.agent.transfer;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.collect.CollectHandleProxy;

/**
 * 获取队列线程
 * 
 * @since 0.1.0
 */
public class TransferTask extends Thread {
    private TraceContext context;
    private final Logger logger;

    public TransferTask(TraceContext context, String name) {
        this.setName(name);
        this.context = context;
        logger = LoggerFactory.getLogger(CollectHandleProxy.class);
    }
    
    @Override
    public void run() {
        for (;;) {
            try {
                context.uploadNode(20);
            } catch (Throwable e) {
                logger.error("upload Task error", e);
            }
        }
    }
}