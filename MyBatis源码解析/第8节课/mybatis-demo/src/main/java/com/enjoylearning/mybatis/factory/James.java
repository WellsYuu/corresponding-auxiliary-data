package com.enjoylearning.mybatis.factory;

import com.enjoylearning.mybatis.factory.product.SmallMovie;
import com.enjoylearning.mybatis.factory.real.CangSmallMovieFactory;
import com.enjoylearning.mybatis.factory.simple.SimpleSmallMovieFactory;

public class James {
	
	public static void main(String[] args) {
		//简单工厂使用
//		SimpleSmallMovieFactory factory = new SimpleSmallMovieFactory();
//		SmallMovie movie = factory.createMovie("cang");
//		movie.watch();
		
		//工厂模式使用
		CangSmallMovieFactory factory = new CangSmallMovieFactory();
		SmallMovie movie = factory.createMovie();
		movie.watch();
		
	}

}
