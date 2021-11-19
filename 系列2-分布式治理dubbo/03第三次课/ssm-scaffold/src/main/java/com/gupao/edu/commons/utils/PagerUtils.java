package com.gupao.edu.commons.utils;

import java.util.List;
import java.util.Map;

/**
 */
public class PagerUtils<T> {


    private List<T> list;         //保存查询的结果集合
    private int totalRecord;        //总记录数
    private int pageSize = 5;       //页面显示的数目
    private Integer totalPage;          //总页码数
    private int currentPage = 1;    //当前页码
    private int previousPage;       //前一页
    private int nextPage;           //后一页
    private int startIndex;         //开始页
    private int endIndex;           //结束页

    /**
     * 参数列表
     */
    private Map<String, Object> parameters;

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public int getStartIndex() {
        this.startIndex = (this.currentPage-1)*this.pageSize;
        return startIndex;
    }
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    public int getEndIndex() {   //从数据库中获取的结束索引，供页面使用
        int end = this.getStartIndex() + this.getPageSize();  //不包含最后一条记录-1
        if(end>this.getTotalRecord()){
            end = this.getStartIndex() + (this.getTotalRecord()%this.getPageSize());
        }
        this.endIndex = end;
        return this.endIndex;
    }
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public int getTotalRecord() {
        return totalRecord;
    }
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getTotalPage() {             //得到总页码数
        if(this.totalRecord%this.pageSize==0){
            this.totalPage = this.totalRecord/this.pageSize;
        }else{
            this.totalPage = this.totalRecord/this.pageSize+1;
        }

        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getPreviousPage() {
        if(this.currentPage-1<1){      //如果上一页小于1，则说明当前页码已经是第一页了
            this.previousPage = 1;
        }else{
            this.previousPage = this.currentPage-1;
        }
        return previousPage;
    }

    public int getNextPage() {
        if(this.currentPage+1>=this.totalPage){   //如果下一页大于总数页，则说明当前页码已经是最后一页了
            this.nextPage = this.totalPage;
        }else{
            this.nextPage = this.currentPage +1;
        }
        return nextPage;
    }
}
