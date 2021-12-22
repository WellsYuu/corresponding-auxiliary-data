package com.cbt.test.web.bean;/**
 * Created by Administrator on 2018/5/31.
 */

/**
 * @author Tommy
 *         Created by Tommy on 2018/5/31
 **/
public class User implements java.io.Serializable {
    private String userid;
    private String userName;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
