package cn.enjoyedu.broadcast;

import java.net.InetSocketAddress;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：日志实体类
 */
public final class LogMsg {
    public static final byte SEPARATOR = (byte) ':';
    /*源的 InetSocketAddress*/
    private final InetSocketAddress source;
    /*消息内容*/
    private final String msg;
    /*消息id*/
    private final long msgId;
    /*消息发送或者接受的时间*/
    private final long time;

    //用于传入消息的构造函数
    public LogMsg(String msg) {
        this(null, msg,-1,System.currentTimeMillis());
    }

    //用于传出消息的构造函数
    public LogMsg(InetSocketAddress source, long msgId,
                  String msg) {
        this(source,msg,msgId,System.currentTimeMillis());
    }

    public LogMsg(InetSocketAddress source, String msg, long msgId, long time) {
        this.source = source;
        this.msg = msg;
        this.msgId = msgId;
        this.time = time;
    }

    //返回发送 LogMsg 的源的 InetSocketAddress
    public InetSocketAddress getSource() {
        return source;
    }

    //返回消息内容
    public String getMsg() {
        return msg;
    }

    //返回消息id
    public long getMsgId() {
        return msgId;
    }

    //返回消息中的时间
    public long getTime() {
        return time;
    }
}
