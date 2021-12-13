package com.gupaoedu.singleton;

//����ʽ����.��֤�̰߳�ȫ
public class Singleton2 {
	//1����һ���Ƚ����췽��˽�л�
	private Singleton2() {}
	//2��Ȼ������һ����̬�������浥��������
	private static Singleton2 single=null;
	//3��ͨ���ṩһ����̬��������õ���������
	//Ϊ�˱�֤���̻߳�������ȷ���ʣ�����������ͬ����synchronized
	//����  synchronized �ؼ��֣����������ܷǳ����µ�
	//����synchronized�ؼ����Ժ󣬶���getInstance()������˵����ʼ�յ��߳�������
	//û�г�����������ǵļ������Դ�������Դ���˷�
	public static synchronized Singleton2 getInstance() {
		if (single == null) {
			single = new Singleton2();
		}
		return single;  
	}
}
