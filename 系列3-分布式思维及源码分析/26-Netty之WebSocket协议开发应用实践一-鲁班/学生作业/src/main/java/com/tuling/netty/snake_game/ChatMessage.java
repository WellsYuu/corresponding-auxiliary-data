package com.tuling.netty.snake_game;

/**
 * @Author fyp
 * @Description
 * @Date Created at 2018/1/26 14:23
 * @Project tio-http-server
 */
public class ChatMessage {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time==0?System.currentTimeMillis():time;
    }

    private String msg;

    public void setTime(long time) {
        this.time = time;
    }

    private long time;
}
