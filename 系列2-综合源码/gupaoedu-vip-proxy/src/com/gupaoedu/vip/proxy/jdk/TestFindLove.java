package com.gupaoedu.vip.proxy.jdk;

import java.io.FileOutputStream;

import com.gupaoedu.vip.custom.GPMeipo;

import sun.misc.ProxyGenerator;

public class TestFindLove {
	public static void main(String[] args) {
		
		try {
			
//			
//			Person obj = (Person)new Meipo().getInstance(new XiaoXingxing());
//			System.out.println(obj.getClass());
//			obj.findLove();
			
			//ԭ��
			//1.�õ��������������ã�Ȼ���ȡ���Ľӿ�
			//2.JDK������������һ���࣬ͬʱʵ�����Ǹ��Ĵ��������ʵ�ֵĽӿ�
			//3.�ѱ�������������Ҳ�õ���
			//4.���¶�̬����һ��class�ֽ���
			//5.Ȼ�����
			
			//��ȡ�ֽ�������
//			byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
//			FileOutputStream os = new FileOutputStream("E:/GP_WORKSPACE/$Proxy0.class");
//			os.write(data);
//			os.close();
			
			//��ʲô?
			//Ϊʲô��
			//��ô����
			
			Person obj = (Person)new GPMeipo().getInstance(new XiaoXingxing());
			System.out.println(obj.getClass());
			obj.findLove();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
