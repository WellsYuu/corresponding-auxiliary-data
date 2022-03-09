package com.imooc.thrift.user.dto;

/**
 * Created by Michael on 2017/11/3.
 */
public class TeacherDTO extends UserDTO {

    private String intro;
    private int stars;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
