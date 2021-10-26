package com.enjoy.action.command.command;

import com.enjoy.action.command.Command;
import com.enjoy.action.command.handler.NewerHandler;

public class NewerCommand extends Command {
    private NewerHandler handler = new NewerHandler();

    @Override
    public String execute() {
        return handler.getNewers();
    }
}
