package com.cbt.agent.collects.jdbc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.cbt.agent.common.util.JsonUtils;
import com.cbt.agent.common.util.NetUtils;
import com.cbt.agent.trace.TraceNode;

/**
 * 
 * Description: jdbc操作状态<br/>
 * 
 * @author zengguangwei@cbt.com
 * @date: 2016年6月6日 下午8:20:09
 * @version 1.0
 * @since JDK 1.7
 */
public class JdbcData implements java.io.Serializable {
    private long beginTime;
    private String sql;
    private ArrayList params = new ArrayList();
    private Integer resultSize;
    private String state;
    private String url;
    private String userName;
    private Throwable error;
    private String executeType;
    private Object[][] resultSet;

    public long getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }
    public String getSql() {
        return sql;
    }
    public void setSql(String sql) {
        this.sql = sql;
    }

    public ArrayList getParams() {
        return params;
    }

    public void setParams(ArrayList<?> params) {
        this.params = params;
    }
    public Integer getResultSize() {
        return resultSize;
    }
    public void setResultSize(Integer resultSize) {
        this.resultSize = resultSize;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Throwable getError() {
        return error;
    }
    public void setError(Throwable error) {
        this.error = error;
    }

    

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }


	public Object[][] getResultSet() {
		return resultSet;
	}
	public void setResultSet(Object[][] resultSet) {
		this.resultSet = resultSet;
	}




	public static class TraceNodeBuilder{
       public static TraceNode builderNode(JdbcData data,TraceNode node){
           node.setAddressIp(parseHostByJdbcUrl(data.getUrl()));
            node.setNodeType("Data Base");
           node.setBeginTime(data.getBeginTime());
           node.setEndTime(System.currentTimeMillis());
           if (data.getError() != null) {
               node.setErrorMessage(data.getError().getMessage());
               ByteArrayOutputStream outStream = new ByteArrayOutputStream();
               PrintStream printStream = new PrintStream(outStream);
               data.getError().printStackTrace(printStream);
               node.setErrorStack(outStream.toString());
               node.setResultState("fail");
           } else {
               node.setResultState("sucess");
           }
           node.setFromIp(NetUtils.getLocalHost());
           List<String> param = new ArrayList<>(data.getParams().size() + 1);
           param.add(data.getSql());
           param.addAll(data.getParams());
            node.setInParam(JsonUtils.toJson(param, JdbcData.class.getClassLoader()));
           node.setResultSize(String.valueOf(data.getResultSize()));
           // 取sql语句第一个单词作为服务方法名称
            node.setServiceName(data.executeType==null?"execute":data.executeType);
           node.setServicePath(data.getUrl());
            node.setOutParam(JsonUtils.toJson(data.getResultSet(), JdbcData.class.getClassLoader()));
           return node;
       }

        private static String parseHostByJdbcUrl(String url) {
            int i = url.indexOf("//");
            int end = url.indexOf(":", i);
            if (end > 0) {
                return url.substring(i + 2, end);
            }
            return url;
        }
    }
}
