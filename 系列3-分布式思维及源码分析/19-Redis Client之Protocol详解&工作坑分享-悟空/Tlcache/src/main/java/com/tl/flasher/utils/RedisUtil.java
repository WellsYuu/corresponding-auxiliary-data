package com.tl.flasher.utils;

import com.tl.flasher.Constants;

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
public class RedisUtil {

    public static String buildKey(String business,String key){
        return new StringBuilder(business).append(Constants.DEFAULT_SEPARATOR).append(key).toString();
    }

    public static String buildKeys(String business,String ... keys){
        StringBuilder sb = new StringBuilder(business).append(Constants.DEFAULT_SEPARATOR);
        for(String key : keys){
            sb.append(key);
        }
        return sb.toString();
    }

    public static byte[] buildKey(String business,byte[] key){
        byte[] busi = (business + Constants.DEFAULT_SEPARATOR).getBytes();
        byte[] bytes = new byte[busi.length + key.length];
        System.arraycopy(busi, 0, bytes, 0, busi.length);
        System.arraycopy(key, 0, bytes, busi.length, key.length);
        return bytes;
    }


}
