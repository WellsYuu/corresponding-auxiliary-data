package com.xiangxue.ch5.bitwise;

import java.io.UnsupportedEncodingException;
/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：演示位运算
 */
public class IntToBinary {
	
    public static void main(String[] args) throws UnsupportedEncodingException {
    	
    	int data = 4;
    	System.out.println("the 4 is "+Integer.toBinaryString(data));
    	
    	//位与  &(1&1=1 1&0=0 0&0=0)
    	System.out.println("the 4 is "+Integer.toBinaryString(4));
    	System.out.println("the 6 is "+Integer.toBinaryString(6));
    	System.out.println("the 4&6 is "+Integer.toBinaryString(4&6));
    	//位或 | (1|1=1 1|0=1 0|0=0)
    	System.out.println("the 4|6 is "+Integer.toBinaryString(4|6));
    	//位非~（~1=0  ~0=1）
    	System.out.println("the ~4 is "+Integer.toBinaryString(~4));
    	//位异或 ^ (1^1=0 1^0=1 0^0=0)
    	System.out.println("the 4^6 is "+Integer.toBinaryString(4^6));
    	
    	// <<有符号左移 >>有符号的右移  >>>无符号右移
    	
    	//取模的操作 a % (2^n) 等价于 a&(2^n-1)
    	System.out.println("the 345 % 16 is "+(345%16)+ " or "+(345&(16-1)));
    	
    } 
}
