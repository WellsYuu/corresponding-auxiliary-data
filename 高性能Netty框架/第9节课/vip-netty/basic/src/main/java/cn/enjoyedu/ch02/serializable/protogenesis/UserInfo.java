package cn.enjoyedu.ch02.serializable.protogenesis;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：实体类
 */
public class UserInfo implements Serializable {

    /**
     * 默认的序列号
     */
    private static final long serialVersionUID = 1L;

    private String userName;

    private int userID;

	public UserInfo buildUserName(String userName) {
		this.userName = userName;
		return this;
    }

    public UserInfo buildUserID(int userID) {
		this.userID = userID;
		return this;
    }

    public final String getUserName() {
		return userName;
    }


    public final void setUserName(String userName) {
		this.userName = userName;
    }


    public final int getUserID() {
		return userID;
    }


    public final void setUserID(int userID) {
		this.userID = userID;
    }

    //自行序列化
    public byte[] codeC() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		byte[] value = this.userName.getBytes();//userName转换为字节数组value
		buffer.putInt(value.length);//写入字节数组value的长度
		buffer.put(value);//写入字节数组value的值
		buffer.putInt(this.userID);//写入userID的值
		buffer.flip();//准备读取buffer中的数据
		value = null;
		byte[] result = new byte[buffer.remaining()];
		buffer.get(result);//buffer中的数据写入字节数组并作为结果返回
		return result;
    }

	//自行序列化方法2
    public byte[] codeC(ByteBuffer buffer) {
		buffer.clear();
		byte[] value = this.userName.getBytes();
		buffer.putInt(value.length);
		buffer.put(value);
		buffer.putInt(this.userID);
		buffer.flip();
		value = null;
		byte[] result = new byte[buffer.remaining()];
		buffer.get(result);
		return result;
    }
}
