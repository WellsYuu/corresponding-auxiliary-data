package com.xiangxue.ch02.memleak;

public class UseStack {

    public static void main(String[] args) {
        Stack stack = new Stack();
        Object o = new Object();
        System.out.println("o="+o);
        stack.push(o);
        Object o1 =  stack.pop();
        System.out.println("o1="+o1);
        
        System.out.println(stack.elements[0]);
        
    }


}
