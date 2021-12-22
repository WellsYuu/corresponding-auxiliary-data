package com.tl.flasher.monitor.scheduler;

import com.tl.flasher.monitor.job.MonitorExecutorJob;

import java.util.Map;
import java.util.concurrent.*;

/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */
public class ScheduledExecutor implements ScheduledService {
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    @Override
    public void startJob(Map<String,Object> context,int intervalInSeconds) {
        MonitorExecutorJob job = new MonitorExecutorJob(context);
        service.scheduleAtFixedRate(job,0,intervalInSeconds,TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        if(null != service){
            service.shutdown();
        }
        service = null;
    }

}
