package cn.enjoyedu.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：消息头
 */
public final class MyHeader {

    private int crcCode = 0xabef0101;

    private int length;// 消息长度

    private long sessionID;// 会话ID

    private byte type;// 消息类型

    private byte priority;// 消息优先级

    private Map<String, Object> attachment = new HashMap<String, Object>(); // 附件

    public final int getCrcCode() {
    	return crcCode;
    }

    public final void setCrcCode(int crcCode) {
    	this.crcCode = crcCode;
    }

    public final int getLength() {
    	return length;
    }

    public final void setLength(int length) {
    	this.length = length;
    }

    public final long getSessionID() {
    	return sessionID;
    }

    public final void setSessionID(long sessionID) {
    	this.sessionID = sessionID;
    }

    public final byte getType() {
    	return type;
    }

    public final void setType(byte type) {
    	this.type = type;
    }

    public final byte getPriority() {
    	return priority;
    }

    public final void setPriority(byte priority) {
    	this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
    	return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
	    this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "MyHeader [crcCode=" + crcCode + ", length=" + length
            + ", sessionID=" + sessionID + ", type=" + type + ", priority="
            + priority + ", attachment=" + attachment + "]";
    }

}
