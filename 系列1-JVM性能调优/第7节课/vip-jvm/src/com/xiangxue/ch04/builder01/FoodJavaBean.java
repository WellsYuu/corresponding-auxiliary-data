package com.xiangxue.ch04.builder01;

class FoodJavaBean {

    //required
    private final String foodName;
    private final int reilang;

    //optional
    private  int danbz;
    private  int dianfen;
    private  int zf;
    private  int tang;
    private  int wss;
    
	public FoodJavaBean(String foodName, int reilang) {
		super();
		this.foodName = foodName;
		this.reilang = reilang;
	}

	public int getDanbz() {
		return danbz;
	}

	public void setDanbz(int danbz) {
		this.danbz = danbz;
	}

	public int getDianfen() {
		return dianfen;
	}

	public void setDianfen(int dianfen) {
		this.dianfen = dianfen;
	}

	public int getZf() {
		return zf;
	}

	public void setZf(int zf) {
		this.zf = zf;
	}

	public int getTang() {
		return tang;
	}

	public void setTang(int tang) {
		this.tang = tang;
	}

	public int getWss() {
		return wss;
	}

	public void setWss(int wss) {
		this.wss = wss;
	}

	public String getFoodName() {
		return foodName;
	}

	public int getReilang() {
		return reilang;
	}
    
    
    
    


}
