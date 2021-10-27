package cn.enjoyedu.vo;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：消息的类型定义
 */
public enum MessageType {

    SERVICE_REQ((byte) 0),/*业务请求消息*/
    SERVICE_RESP((byte) 1), /*业务应答消息*/
    ONE_WAY((byte) 2), /*无需应答的消息*/
    LOGIN_REQ((byte) 3), /*登录请求消息*/
    LOGIN_RESP((byte) 4), /*登录响应消息*/
    HEARTBEAT_REQ((byte) 5), /*心跳请求消息*/
    HEARTBEAT_RESP((byte) 6);/*心跳应答消息*/

    private byte value;

    private MessageType(byte value) {
	this.value = value;
    }

    public byte value() {
	return this.value;
    }
}
