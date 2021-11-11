package com.enjoy.loadbalance;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;

import java.util.List;

/**
 * Created by Peter on 11/18 018.
 */
public class LastLoadBalance implements LoadBalance {

    /**
     *
     * @param invokers 所有provider的实现
     * @param url
     * @param invocation
     * @param <T>
     * @return
     * @throws RpcException
     */
    @Override
    public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation)
            throws RpcException {
        System.out.println("启动最后一个");
        //固定使用第一个
        return invokers.get(invokers.size()-1);
    }
}
