package com.tl.util;/*
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
 * 以往视频加小乔老师QQ：895900009
 * 悟空老师QQ：245553999
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * jvm的原子操作
 */
public class OrderAtomicServicImpl implements OrderServer {
    static AtomicInteger atomicInteger=new AtomicInteger();
    public  String getOrderNo(){
        SimpleDateFormat simpleDateFormat=  new SimpleDateFormat("YYYYmmDDHHMMSS");
        return simpleDateFormat.format(new Date())+atomicInteger.incrementAndGet();

    }
}
