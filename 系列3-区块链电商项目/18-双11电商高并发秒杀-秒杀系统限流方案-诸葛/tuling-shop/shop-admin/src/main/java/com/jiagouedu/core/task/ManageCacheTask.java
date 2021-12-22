package com.jiagouedu.core.task;

import com.jiagouedu.core.front.SystemManager;
import com.jiagouedu.core.oscache.ManageCache;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 后台缓存定时更新
 * 
 * @author wukong 图灵学院 QQ:245553999
 * 
 */
@Component
public class ManageCacheTask implements Runnable {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ManageCacheTask.class);
//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private ManageCache manageCache;

	public void setManageCache(ManageCache manageCache) {
		this.manageCache = manageCache;
	}

	@Override
	public void run() {
		while (true) {
			
			try {
//				TimeUnit.MINUTES.sleep(15);//单位：分钟
				TimeUnit.SECONDS.sleep(Long.valueOf(SystemManager.getInstance().getProperty("task_SystemAutoNotifyTask_time")));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			logger.error("OrderCancelTask.run...");
			try {
				manageCache.loadAllCache();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
