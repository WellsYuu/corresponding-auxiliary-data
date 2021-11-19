package com.gupao.edu.dao.user.model;

import com.gupao.framework.common.utils.StringUtils;

public class UserInfo {
    private Integer id;

    private String account;

    private String password;

    private String mobile;

    private String openid;

    private String nickname;

    private String headImg;

    private Integer credits;

    private String createTime;

    private String name;

    private String sex;

    private String birthday;

    private String placeBrith;

    private String email;

    private String wechatNo;

    private Integer classId;

    private Integer num;

    private String top1Cls;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getPlaceBrith() {
        return placeBrith;
    }

    public void setPlaceBrith(String placeBrith) {
        this.placeBrith = placeBrith == null ? null : placeBrith.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo == null ? null : wechatNo.trim();
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTop1Cls() {
        return top1Cls;
    }

    public void setTop1Cls(String top1Cls) {
        this.top1Cls = top1Cls;
    }

    public int calNotNullNum(){
        int num=0;
        if(StringUtils.isNotBlank(getName())){
            num+=1;
        }
        if(StringUtils.isNotBlank(getBirthday())){
            num+=1;
        }
        if(StringUtils.isNotBlank(getMobile())){
            num+=1;
        }
        if(StringUtils.isNotBlank(getNickname())){
            num+=1;
        }
        if(StringUtils.isNotBlank(getName())){
            num+=1;
        }
        if(StringUtils.isNotBlank(getEmail())){
            num+=1;
        }
        if(StringUtils.isNotBlank(getHeadImg())){
            num+=1;
        }
        if(StringUtils.isNotBlank(getPlaceBrith())){
            num+=1;
        }
        return num;
    }
}