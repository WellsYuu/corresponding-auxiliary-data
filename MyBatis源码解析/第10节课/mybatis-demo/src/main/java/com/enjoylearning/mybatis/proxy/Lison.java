package com.enjoylearning.mybatis.proxy;

public class Lison {
	
	public static void main(String[] args) {
		//隔壁有个女孩，叫王美丽
		Girl girl = new WangMeiLi();
		//他有个庞大的家庭，想要跟她约会必须征得她家里人的同意
		WangMeiLiProxy   family = new WangMeiLiProxy(girl);
		//有一次我去约王美丽，碰到了她的妈妈，我征得了她妈妈的同意
		Girl mother = (Girl) family.getProxyInstance();
		//通过她的妈妈这个代理才能与王美丽约会
		mother.date();
		//华丽分割线
		System.out.println("-----------------------------------");
		//通过她的妈妈这个代理才能与王美丽看电影
//		mother.watchMovie();
	}

}
