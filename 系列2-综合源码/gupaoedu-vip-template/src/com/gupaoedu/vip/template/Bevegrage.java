package com.gupaoedu.vip.template;

//������(�ó�ȥ��Ǯ��)
public abstract class Bevegrage {
	
	//���ܱ���д
	public final void create(){
		//1����ˮ�տ�
		boilWater();
		//2���ѱ���׼���á�ԭ���Ϸŵ�����
		pourInCup();
		//3����ˮ����
		brew();
		//4����Ӹ���
		addCoundiments();
	}
	
	public abstract void pourInCup();
	
	public abstract void addCoundiments();
	
	
	public void brew(){
		System.out.println("����ˮ���뱭�н��г���");
	};
	
	public void boilWater(){
		System.out.println("�տ�ˮ���յ�100�ȿ��������");
	}
	
}
