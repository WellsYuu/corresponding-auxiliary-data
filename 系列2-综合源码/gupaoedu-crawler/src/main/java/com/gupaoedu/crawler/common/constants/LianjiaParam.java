package com.gupaoedu.crawler.common.constants;


/**
 * �ٶ������̶��Ĳ�����
 * @author Tom
 *
 */
public class LianjiaParam {
	/** �������ӳ�ʱʱ��(����) */
	public static final int CON_TIME_OUT = 1000;
	/** ���ؽ����ʱʱ��(����) */
	public static final int SO_TIME_OUT = 2000;
	
	//================= ��������Ϊ�������� ==================
	
	/////////////// ��ѡ���� /////////////////
	
	/** ��ǰ�����Ĵʻ�������(Keyword)*/
	public static final String wd = "wd"; 
	
	/** ��ʾ�����ҳ��,��ǰҳ ���� ��ѯҳ��(Page Number) */ 
	public static final String pn = "pn"; 
	
	/** ��������(Class)��cl=3Ϊ��ҳ���� */
	public static final String cl = "cl";

	/////////////// ��ѡ���� /////////////////

	/** ���������ʾ����(Record Number)��ȡֵ��Χ��10--100��֮�䣬ȱʡ����rn=10 */
	public static final String rn = "rn";
	
	/**��ѯ�������ֵı���(Input Encoding)��ȱʡ����ie=gb2312����Ϊ��������*/
	public static final String ie = "ie";
	
	/** �ύ�����������Դվ��**/
	//	�������õ�tn
	//	tn=baidulocal ��ʾ�ٶ�վ�����������صĽ���ܸɾ����޹����š�
	//	���磬�ڰٶ�վ�����������֡����������ؽ���ǲ��Ǻ���ˬ��
	//	tn=baiducnnic ��Ѱٶȷ��ڿ������������������Ϳ����ˣ��ǰٶ�ΪCnnic���Ƶ�
	public static final String tn = "tn";
	
	public static final String tn_baidulocal = "baidulocal";
	
	public static final String tn_baiducnnic = "baiducnnic";
	
	/** ���޶�������������,�����������˵�վ��������ʹ�ò���si=sina.com.cn��Ҫʹ���������Ч������ct����һ��ʹ�á� */
	public static final String si = "si";
	
	/** �˲�����ֵһ����һ�����֣�����Ӧ���������������֤�� */
	//si��ct�������ʹ�ã�������sina.com.cn�����������롱��
	//���ã�http://www.baidu.com/s?q=&ct=2097152&si=sina.com.cn&ie=gb2312&cl=3&wd=����
	public static final String ct = "ct";
	
	/** ��һ�������Ĺؼ���(Before Search)����������������й� */
	public static final String bs = "bs";
	
	/**������Ӧʱ�䣬��λ�Ǻ��� */
	public static final String inputT = "inputT";
	
	/** �������λ������(Related search position)���ٶ��������鼰�������������λ��*/
	public static final String rsp = "rsp";
	
	
	
	
	
	
	
	
	//============= �ٶ�֪���������� ======================
	/** �����ؼ��� */
	public static final String word = "word";
	

}