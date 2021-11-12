package com.enjoy.action.command;

import com.enjoy.action.command.command.DiscountCommand;
import com.enjoy.action.command.command.HotCommand;
import com.enjoy.action.command.command.NewerCommand;

/**
 * 命令模式：将不符合抽象编程的调用，改造成符合的抽象编程
 * 首页榜单
 */
public class CommandClient {

    public static void main(String[] args) {
        //三个命令，代表三个请求
        Command command1,command2,command3;
        command1 = new HotCommand();
        command2 = new NewerCommand();
        command3 = new DiscountCommand();

        ListView listView;
        listView = new ListView();
        listView.setCommand(command2);

        listView.getList();



    }
}
