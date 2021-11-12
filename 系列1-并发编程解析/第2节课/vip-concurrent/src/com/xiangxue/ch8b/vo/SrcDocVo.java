package com.xiangxue.ch8b.vo;

import java.util.List;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：待处理文档实体类
 */
public class SrcDocVo {
    //待处理文档名称
    private final String docName;
    //待处理文档中题目id列表
    private final List<Integer> questionList;

    public SrcDocVo(String docName, List<Integer> questionList) {
        this.docName = docName;
        this.questionList = questionList;
    }

    public String getDocName() {
        return docName;
    }

    public List<Integer> getQuestionList() {
        return questionList;
    }
}
