package com.gupaoedu.singleton;

//����ʽ������.�ڵ�һ�ε��õ�ʱ��ʵ�����Լ�
public class Singleton1 {
	//1����һ���Ƚ����췽��˽�л�
	private Singleton1() {}
	//2��Ȼ������һ����̬�������浥��������
	private static Singleton1 single = null;
	//3��ͨ���ṩһ����̬��������õ���������
	//����ȫ��
	public static Singleton1 getInstance() {
		if (single == null) {
			single = new Singleton1();
		}
		return single;
	}
}
