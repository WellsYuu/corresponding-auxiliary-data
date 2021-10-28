package com.xiangxue.ch04.builder01.buildpattern;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：客户端
 */
public class Mingyun {

    public static void main(String[] args) {
    	System.out.println("create NvWa");
    	NvWa nvwa =  new NvWa();
    	nvwa.buildPerson(new ManBuilder());
    	nvwa.buildPerson(new WomanBuilder());
    	
    }
}
