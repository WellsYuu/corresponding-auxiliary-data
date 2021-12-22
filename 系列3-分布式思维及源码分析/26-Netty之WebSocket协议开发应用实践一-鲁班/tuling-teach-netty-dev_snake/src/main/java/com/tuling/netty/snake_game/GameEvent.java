package com.tuling.netty.snake_game;

/**
 * Created by Tommy on 2018/1/25.
 */
public class GameEvent implements java.io.Serializable{
    private transient String accountId;// 通知目标,为空表示通知所有客户端
    private EventType type; //事件类型 die
    private String message;// 事件消息

    public GameEvent(EventType type, String message) {
        this.type = type;
        this.message = message;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public enum EventType{
        die;
    }
}
