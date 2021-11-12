package cn.enjoy.order.utils;

import org.springframework.util.CollectionUtils;

import java.util.Random;

/**
 * Created by VULCAN on 2018/7/28.
 */
public class RamdomLoadBalance extends LoadBalance {
    @Override
    public String choseServiceHost() {
        String result = "";
        if(!CollectionUtils.isEmpty(SERVICE_LIST)) {
            int index = new Random().nextInt(SERVICE_LIST.size());
            result = SERVICE_LIST.get(index);
        }
        return result ;
    }
}
