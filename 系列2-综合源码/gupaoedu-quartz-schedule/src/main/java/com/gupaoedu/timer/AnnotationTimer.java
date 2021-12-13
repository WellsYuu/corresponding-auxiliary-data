package com.gupaoedu.timer;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnnotationTimer {
	
	Logger LOG = Logger.getLogger(this.getClass());
	
	@Scheduled(cron="0/10 * * * * ?")
	@Async
	public void a(){
		LOG.info("annotation配置的任务执行");
	}
	
}
