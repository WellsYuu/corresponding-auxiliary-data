package com.enjoy.action.command;

/**
 * 命令模式：将方法调用本身，包装成一个对象（命令对象）
 * 订单支付过程
 */
public class ListView {

    private Command command; //维持一个抽象命令对象的引用

    //为功能键注入命令
    public void setCommand(Command command) {
        this.command = command;
    }

    //请求列表
    public void getList() {
        System.out.print("首页请求");
        String result = command.execute();
        System.out.println("当前列表："+result);
    }
}
