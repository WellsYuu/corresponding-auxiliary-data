package com.enjoylearning.mybatis.proxy;

public class WangMeiLi implements Girl {

	@Override
	public void date() {
		System.out.println("王美丽说：跟你约会好开心啊");
		this.watchMovie();
	}

	@Override
	public void watchMovie() {
		System.out.println("王美丽说：这个电影我不喜欢看");
	}

}
