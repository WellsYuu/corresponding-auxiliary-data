package com.tl.flasher.monitor;

import com.tl.flasher.Constants;
import com.tl.flasher.enums.MonitorPushTypeEnum;
import com.tl.flasher.monitor.protocol.AbstractProtocol;
import com.tl.flasher.monitor.scheduler.ScheduledQuartz;
import com.tl.flasher.monitor.scheduler.ScheduledService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.BooleanUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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
public class MonitorService extends HttpServlet implements InitializingBean, DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorService.class);

    private int intervalInSeconds = Constants.MONITOR_INTERVAL_SECONDS;
    private String pushType = MonitorPushTypeEnum.HTTP_ASYN.name() ;
    private AbstractProtocol protocol;
    private String url;

    private String host;
    private int port;

    private ScheduledService scheduledService;



    @Override
    public void init() throws ServletException {
        String statJobTime = this.getInitParameter(Constants.MONITOR_SERVLET_AUTO_CLEAR_TIME_NAME);
        Integer statJobTimeI = Constants.MONITOR_INTERVAL_SECONDS;
        if(!Strings.isNullOrEmpty(statJobTime)){
            statJobTimeI = Integer.valueOf(statJobTime);
        }
        if(statJobTimeI <=0 ){
            throw new RuntimeException("init parameter ["+Constants.MONITOR_SERVLET_AUTO_CLEAR_TIME_NAME+"] is error");
        }
        String scheduledServiceName = this.getInitParameter(Constants.MONITOR_SERVLET_SCHEDULED_SERVLET_NAME);
        if(!Strings.isNullOrEmpty(scheduledServiceName)){
            try {
                Class c = Class.forName(scheduledServiceName);
                scheduledService = (ScheduledService) c.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("init parameter ["+Constants.MONITOR_SERVLET_SCHEDULED_SERVLET_NAME+"] is error");
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException("init parameter ["+Constants.MONITOR_SERVLET_SCHEDULED_SERVLET_NAME+"] is error");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("init parameter ["+Constants.MONITOR_SERVLET_SCHEDULED_SERVLET_NAME+"] is error");
            }
        }

        setIntervalInSeconds(statJobTimeI);
        this.startJob(MonitorPushTypeEnum.HTTP_SERVLET);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(Constants.MONITOR_MAP));
        out.close();

        // 是否手工清除
        String cleanDataS = req.getParameter(Constants.MONITOR_SERVLET_HAND_CLEAR_NAME);
        if(!Strings.isNullOrEmpty(cleanDataS)){
            boolean cleanDataB = BooleanUtils.toBoolean(cleanDataS);
            if(cleanDataB){
                AbstractProtocol.clearLocalData();
            }
        }

    }

    @Override
    public void destroy()  {
        scheduledService.shutdown();
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        MonitorPushTypeEnum mPushType = MonitorPushTypeEnum.valueOf(pushType);
        if(intervalInSeconds <= 0
                || null == mPushType
                || Strings.isNullOrEmpty(url)
                || null == protocol || Strings.isNullOrEmpty(protocol.getEndpoint())){
            throw new RuntimeException("intervalInSeconds pushType OR url OR protocol OR protocol.endpoint is null or empty");
        }

        switch (mPushType.getType()){
            case HTTP:
                this.host = url;
                break;
            default:
                this.host = url;
        }

        this.startJob(mPushType);

    }

    private void startJob(MonitorPushTypeEnum mPushType) {
        JobDataMap jobDataMap = new JobDataMap();
        Map<String,Object> map = Maps.newHashMap();
        map.put(Constants.MONITOR_PUSH_TYPE_NAME,mPushType);
        if(null != protocol){
            // 设置数据抽取时长，与自动任务的间隔时间相同
            protocol.setStep(intervalInSeconds);
            map.put(Constants.MONITOR_PROTOCOL_NAME,protocol);
        }
        map.put(Constants.MONITOR_HOST_NAME,host);
        map.put(Constants.MONITOR_PORT_NAME,port);

        // 默认使用ScheduledExecutor方式
        if(null == scheduledService){
            scheduledService = new ScheduledQuartz();
        }
        scheduledService.startJob(map,intervalInSeconds);
    }

    public int getIntervalInSeconds() {
        return intervalInSeconds;
    }

    public void setIntervalInSeconds(int intervalInSeconds) {
        this.intervalInSeconds = intervalInSeconds;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public AbstractProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(AbstractProtocol protocol) {
        this.protocol = protocol;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ScheduledService getScheduledService() {
        return scheduledService;
    }

    public void setScheduledService(ScheduledService scheduledService) {
        this.scheduledService = scheduledService;
    }
}
