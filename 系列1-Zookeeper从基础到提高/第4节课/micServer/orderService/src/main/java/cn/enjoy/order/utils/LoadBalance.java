package cn.enjoy.order.utils;

import java.util.List;

/**
 * Created by VULCAN on 2018/7/28.
 */
public abstract class LoadBalance {
    public volatile static List<String> SERVICE_LIST;

    public abstract String choseServiceHost();

}
