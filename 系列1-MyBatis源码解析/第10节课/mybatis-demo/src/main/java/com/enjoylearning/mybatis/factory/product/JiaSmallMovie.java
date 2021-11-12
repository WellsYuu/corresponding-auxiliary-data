package com.enjoylearning.mybatis.factory.product;

import java.math.BigDecimal;

public class JiaSmallMovie implements SmallMovie{
	
	private String actor;
	
	private String director;
	
	private String bianju;
	
	private String sheji;
	
	private String music;
	
	private String light;
	
	private BigDecimal money;
	
	private String jianji;
	
	private String paiShe;
	
	private String xuanChuan;
	
	
	@Override
	public void watch() {
		System.out.println("加老师，为您表演，此处少儿不宜");
		
	}
	
	
	

	public JiaSmallMovie(String actor) {
		super();
		this.actor = actor;
	}







	public JiaSmallMovie(String actor, String director) {
		super();
		this.actor = actor;
		this.director = director;
	}







	public JiaSmallMovie(String actor, String director, String bianju) {
		super();
		this.actor = actor;
		this.director = director;
		this.bianju = bianju;
	}







	public JiaSmallMovie(String actor, String director, String bianju,
			String sheji) {
		super();
		this.actor = actor;
		this.director = director;
		this.bianju = bianju;
		this.sheji = sheji;
	}

	
	






	public JiaSmallMovie(String actor, String director, String bianju,
			String sheji, String music) {
		super();
		this.actor = actor;
		this.director = director;
		this.bianju = bianju;
		this.sheji = sheji;
		this.music = music;
	}
	
	







	public JiaSmallMovie(String actor, String director, String bianju,
			String sheji, String music, String light, BigDecimal money,
			String jianji, String paiShe, String xuanChuan) {
		super();
		this.actor = actor;
		this.director = director;
		this.bianju = bianju;
		this.sheji = sheji;
		this.music = music;
		this.light = light;
		this.money = money;
		this.jianji = jianji;
		this.paiShe = paiShe;
		this.xuanChuan = xuanChuan;
	}




	public String getActor() {
		return actor;
	}




	public void setActor(String actor) {
		this.actor = actor;
	}




	public String getDirector() {
		return director;
	}




	public void setDirector(String director) {
		this.director = director;
	}




	public String getBianju() {
		return bianju;
	}




	public void setBianju(String bianju) {
		this.bianju = bianju;
	}




	public String getSheji() {
		return sheji;
	}




	public void setSheji(String sheji) {
		this.sheji = sheji;
	}




	public String getMusic() {
		return music;
	}




	public void setMusic(String music) {
		this.music = music;
	}




	public String getLight() {
		return light;
	}




	public void setLight(String light) {
		this.light = light;
	}




	public BigDecimal getMoney() {
		return money;
	}




	public void setMoney(BigDecimal money) {
		this.money = money;
	}




	public String getJianji() {
		return jianji;
	}




	public void setJianji(String jianji) {
		this.jianji = jianji;
	}




	public String getPaiShe() {
		return paiShe;
	}




	public void setPaiShe(String paiShe) {
		this.paiShe = paiShe;
	}




	public String getXuanChuan() {
		return xuanChuan;
	}




	public void setXuanChuan(String xuanChuan) {
		this.xuanChuan = xuanChuan;
	}

}
