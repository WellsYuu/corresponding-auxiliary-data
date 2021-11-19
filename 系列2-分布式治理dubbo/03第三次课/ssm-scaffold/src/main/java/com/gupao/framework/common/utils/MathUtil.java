package com.gupao.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Random;

public class MathUtil {

	static Logger LOG= LoggerFactory.getLogger(MathUtil.class);
	/**
	 * 获取随机的数值。
	 * @param length	长度
	 * @return
	 */
	public static String getRandom620(Integer length){
	   String result = "";
	   Random rand = new Random();
	   int n = 20;
	   if(null != length && length > 0){
		   n = length;
	   }
       boolean[]  bool = new boolean[n];
       int randInt = 0;
       for(int i = 0; i < length ; i++) {
            do {
                randInt  = rand.nextInt(n);

            }while(bool[randInt]);

           bool[randInt] = true;
           result += randInt;
       }
       return result;
	}
	/**
	 * MD5 加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	 public static String  getMD5(String str) {  
	        MessageDigest messageDigest = null;  
	            try {
					messageDigest = MessageDigest.getInstance("MD5");
					messageDigest.reset();
					messageDigest.update(str.getBytes("UTF-8"));
				} catch (Exception e) {
					LOG.error("MD5转换异常！message：%s", e.getMessage());
				}  
				
	        byte[] byteArray = messageDigest.digest();  
	        StringBuffer md5StrBuff = new StringBuffer();  
	        for (int i = 0; i < byteArray.length; i++) {              
	            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
	                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
	            else  
	                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
	        }  
	        return md5StrBuff.toString();  
	    }

	public static void main(String[] args) {
		String str=String.format("%s#%s", "test","test");
		System.out.println(getMD5(str));
	}
}