package com.imooc.course.dto;

import com.imooc.thrift.user.dto.TeacherDTO;

import java.io.Serializable;

/**
 * Created by Michael on 2017/11/3.
 */
public class CourseDTO implements Serializable {

    private int id;
    private String title;
    private String description;
    private TeacherDTO teacher;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }
}
