package com.tl.flasher.monitor.job;

import com.tl.flasher.Constants;
import com.tl.flasher.enums.MonitorPushTypeEnum;
import com.tl.flasher.monitor.protocol.AbstractProtocol;
import com.tl.flasher.utils.HttpUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
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
public class MonitorJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorJob.class);

    public static void doJob(Map<String,Object> map){
        MonitorPushTypeEnum mPushType = (MonitorPushTypeEnum) map.get(Constants.MONITOR_PUSH_TYPE_NAME);
        AbstractProtocol protocol = (AbstractProtocol) map.get(Constants.MONITOR_PROTOCOL_NAME);
        String host = (String) map.get(Constants.MONITOR_HOST_NAME);
        Integer port = (Integer) map.get(Constants.MONITOR_PORT_NAME);
        List<Serializable> datas = null;
        if(null != protocol && null != (datas = AbstractProtocol.buildLocalData(protocol)) && !datas.isEmpty()){
            String result = null;
            try {
                Gson gson = new Gson();
                switch (mPushType){
                    case HTTP_ASYN:
                        result = HttpUtil.doPostAsyn(host,gson.toJson(datas));
                        break;
                    case HTTP_SYNC:
                        result = HttpUtil.doPostSync(host, gson.toJson(datas));
                        break;
                    default:

                }
            } catch (Exception e){
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }

            LOGGER.info("post "+ host + port +",result is " +result);

        }

        // 清除本地内存数据
        AbstractProtocol.clearLocalData();
    }

}
