package com.gupaoedu.vip.delegate;

public class DispatcherTest {

	
	public static void main(String[] args) {
		Dispatcher dispatcher = new Dispatcher(new ExectorA());
		//����ȥ���������ǵ���Ŀ�����ڸɻ�
		//��ʵ�ʸɻ��������ͨԱ��
		//����ǵ��ͣ��ɻ����ҵģ����������
		dispatcher.doing();
	}
	
}
