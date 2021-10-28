package com.xiangxue.ch04.builder01;

public class FoodBuilder {

    //required
    private final String foodName;
    private final int reilang;

    //optional
    private  int danbz;
    private  int dianfen;
    private  int zf;
    private  int tang;
    private   int wss;
    
    public static class Builder{
        //required
        private final String foodName;
        private final int reilang;

        //optional
        private  int danbz;
        private  int dianfen;
        private  int zf;
        private  int tang;
        private   int wss;
        
		public Builder(String foodName, int reilang) {
			super();
			this.foodName = foodName;
			this.reilang = reilang;
		}
		
		public Builder danbz(int val) {
			this.danbz = val;
			return this;
		}
        
		//.......
		
		public FoodBuilder build() {
			return new FoodBuilder(this);
		}
    }
    
    private FoodBuilder(Builder builder) {
    	foodName = builder.foodName;
    	reilang = builder.reilang;
    	danbz = builder.danbz;
    	//.....
    	
    }
    
    public static void main(String[] args) {
    	FoodBuilder foodBuilder 
    	= new FoodBuilder.Builder("food2", 1000).danbz(100)
    	//.....
    	.build();

	}



}
