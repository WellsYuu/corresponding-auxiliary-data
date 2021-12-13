package com.gupaoedu.essearch.util;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 生成随机数
 * @author Tom
 *
 */
public class RandomUtil {

	private static Random random = new Random();
	private static final char [] sexs = "男女".toCharArray();
	private static final char [] wxNo = "abcdefghijklmnopqrstuvwxyz0123456789".toLowerCase().toCharArray();
	private static final char [] firstName = "赵钱孙李周吴郑王冯陈卫蒋沈韩杨朱秦许何吕施张孔曹严金魏陶姜谢邹窦章苏潘葛范彭谭夏胡".toCharArray();
	
	//确保车牌号不重复，声明一个缓存区
	private static Set<String> wxNoCache;
	
	
	/**
	 * 随机生成性别
	 * @return
	 */
	public static String randomSex(){
		int i = random.nextInt(sexs.length);
		return ("" + sexs[i]);
	}
	
	/**
	 * 随机生成车牌号
	 * @return
	 */
	public static String randomWxNo(){
		//初始化缓冲区
		openCache();
		
		//微信号自动生成规则，wx_开头加上10位数字字组合
		StringBuffer sb = new StringBuffer();
		for(int c = 0;c < 10;c ++){
			int i = random.nextInt(wxNo.length);
			sb.append(wxNo[i]);
		}
		String carNum = ("wx_" + sb.toString());
		//为了防止微信号重复，生成以后检查一下
		//如果重复，递归重新生成，直到不重复为止
		if(wxNoCache.contains(carNum)){
			return randomWxNo();
		}
		wxNoCache.add(carNum);
		return carNum;
	}
	
	/**
	 * 随机生成坐标
	 */
	public static double[] randomPoint(double myLat,double myLon){
		double min = 0.000001;//坐标范围，最小1米
		double max = 0.00002;//坐标范围，最大1000米
		
		//随机生成一组长沙附近的坐标
		double s = random.nextDouble() % (max - min + 1) + max;
		//格式化保留6位小数
		DecimalFormat df = new DecimalFormat("######0.000000");
		String slon = df.format(s + myLon);
		String slat = df.format(s + myLat);
		Double dlon = Double.valueOf(slon);
		Double dlat = Double.valueOf(slat);
		return new double []{dlat,dlon};
	}
	

	
	/**
	 * 随机生成司机姓名
	 * @return
	 */
	public static String randomNickName(String sex){
		int i = random.nextInt(firstName.length);
		return firstName[i] + ("男".equals(sex) ? "先生" : "女士");
	}
	
	
	/**
	 * 开启缓存区
	 */
	public static void openCache(){
		if(wxNoCache == null){
			wxNoCache = new HashSet<String>();
		}
	}
	
	/**
	 * 清空缓存区
	 */
	public static void clearCache(){
		wxNoCache = null;
	}
	
}
