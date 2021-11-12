package com.xiangxue.ch8b.vo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：题目实体类
 */
public class QuestionInDBVo {
    //题目id
    private final int id;
    //题目详情，平均长度800字节
    private final String detail;
    //题目内容的摘要
    private final String sha;

    public QuestionInDBVo(int id, String detail,String sha) {
        this.id = id;
        this.detail = detail;
        this.sha = sha;
    }

    public int getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

	public String getSha() {
		return sha;
	}
    
}
