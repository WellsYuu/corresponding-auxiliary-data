package com.gupaoedu.crawler.common.constants;


/**
 * 百度搜索固定的参数名
 * @author Tom
 *
 */
public class LianjiaParam {
	/** 请求连接超时时间(毫秒) */
	public static final int CON_TIME_OUT = 1000;
	/** 返回结果超时时间(毫秒) */
	public static final int SO_TIME_OUT = 2000;
	
	//================= 以下内容为搜索参数 ==================
	
	/////////////// 必选参数 /////////////////
	
	/** 当前搜索的词或者内容(Keyword)*/
	public static final String wd = "wd"; 
	
	/** 显示结果的页数,当前页 乘以 查询页数(Page Number) */ 
	public static final String pn = "pn"; 
	
	/** 搜索类型(Class)，cl=3为网页搜索 */
	public static final String cl = "cl";

	/////////////// 可选参数 /////////////////

	/** 搜索结果显示条数(Record Number)，取值范围在10--100条之间，缺省设置rn=10 */
	public static final String rn = "rn";
	
	/**查询输入文字的编码(Input Encoding)，缺省设置ie=gb2312，即为简体中文*/
	public static final String ie = "ie";
	
	/** 提交搜索请求的来源站点**/
	//	几个有用的tn
	//	tn=baidulocal 表示百度站内搜索，返回的结果很干净，无广告干扰。
	//	比如，在百度站内搜索“快乐”，看看返回结果是不是很清爽。
	//	tn=baiducnnic 想把百度放在框架中吗？试试这个参数就可以了，是百度为Cnnic定制的
	public static final String tn = "tn";
	
	public static final String tn_baidulocal = "baidulocal";
	
	public static final String tn_baiducnnic = "baiducnnic";
	
	/** 在限定的域名中搜索,比如想在新浪的站内搜索可使用参数si=sina.com.cn，要使这个参数有效必须结合ct参数一起使用。 */
	public static final String si = "si";
	
	/** 此参数的值一般是一串数字，估计应该是搜索请求的验证码 */
	//si和ct参数结合使用，比如在sina.com.cn中搜索“理想”，
	//可用：http://www.baidu.com/s?q=&ct=2097152&si=sina.com.cn&ie=gb2312&cl=3&wd=理想
	public static final String ct = "ct";
	
	/** 上一次搜索的关键词(Before Search)，估计与相关搜索有关 */
	public static final String bs = "bs";
	
	/**搜索相应时间，单位是毫秒 */
	public static final String inputT = "inputT";
	
	/** 相关搜索位置排序(Related search position)，百度搜索建议及相关搜索的排名位置*/
	public static final String rsp = "rsp";
	
	
	
	
	
	
	
	
	//============= 百度知道搜索参数 ======================
	/** 搜索关键字 */
	public static final String word = "word";
	

}