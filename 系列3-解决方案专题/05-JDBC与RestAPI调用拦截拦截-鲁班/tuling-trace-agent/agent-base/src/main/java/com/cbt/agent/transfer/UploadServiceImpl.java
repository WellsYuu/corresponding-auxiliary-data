package com.cbt.agent.transfer;


import com.cbt.agent.bootstrap.CbtSessionInfo;
import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.common.util.Assert;
import com.cbt.agent.common.util.JsonUtils;
import com.cbt.agent.core.AgentFinal;
import com.cbt.agent.trace.TraceFrom;
import com.cbt.agent.trace.TraceNode;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collection;

public class UploadServiceImpl implements UploadService {
    
    private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
    private String defaultUpload;
    private String httpUrl;

    @Override
    public void uploadByDefault(Collection<TraceNode> nodes, Collection<TraceFrom> infos) {
        if ("http".equals(defaultUpload)) {
            Assert.hasText(httpUrl);
            uploadByHttp(nodes, infos, httpUrl);
        } else if ("logfile".equals(defaultUpload)) {
            uploadToLogfile(nodes, infos);
        }
    }

    @Override
    public void uploadByHttp(Collection<TraceNode> nodes, Collection<TraceFrom> infos, String url) {
        if (nodes != null && !nodes.isEmpty()) {
            String nodeJsons[]=new String[nodes.size()];
            int i=0;
            for (TraceNode node : nodes) {
                nodeJsons[i++]=JsonUtils.toJson(node,getClass().getClassLoader());
            }
            execHttp(url, nodeJsons);
            /*String nodesParamJson = JsonUtils.toJson(nodes.toArray(new TraceNode[nodes.size()]),getClass().getClassLoader());
            execHttp(url, "upload.TraceNode", nodesParamJson);*/
        }

        /*if (infos != null && !infos.isEmpty()) {
            String infosParamJson = JsonUtils.toJson(infos.toArray(new TraceFrom[infos.size()]),getClass().getClassLoader());
            execHttp(url, "upload.TraceFrom", infosParamJson);
        }*/
    }

    // TODO: 16/11/6 需要绕过http监控
    void execHttp(String url ,String[] paramJsons)  {
        String param="type=TraceNode";
        // 获取client session id
        CbtSessionInfo clientSession = (CbtSessionInfo) AgentFinal.LOCAL_CONFIG.get(AgentFinal.CLIENT_SESSION_KEY);
        param+="&sessionId="+clientSession.getSessionId();

        for (String paramJson : paramJsons) {
            try {
                param +="&nodeJson="+ URLEncoder.encode(paramJson,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        // 解决spring mvc 在只有一个数组时参数转换错乱的问题
        param+="&nodeJson=";

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setUseCaches(false);

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw new RuntimeException("上传失败", e);
//            logger.error("发送 POST 请求出现异常！",e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                logger.error(ex);
            }
        }


    }

    @Override
    public void uploadByDubbo(Collection<TraceNode> nodes, Collection<TraceFrom> infos) {
        // TODO Auto-generated method stub
    }

    @Override
    public void uploadToRedis(Collection<TraceNode> nodes, Collection<TraceFrom> infos) {
        // TODO Auto-generated method stub
    }

    @Override
    public void uploadToMysql(Collection<TraceNode> nodes, Collection<TraceFrom> infos) {
        // TODO Auto-generated method stub
    }

    public String getDefaultUpload() {
        return defaultUpload;
    }

    public void setDefaultUpload(String defaultUpload) {
        this.defaultUpload = defaultUpload;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    @Override
    public void uploadToLogfile(Collection<TraceNode> nodes, Collection<TraceFrom> infos) {
        if (nodes != null) {
            for (TraceNode node : nodes) {
                logger.info(node.toString());
            }
        }
        if (infos != null) {
            for (TraceFrom info : infos) {
                logger.info(info.toString());
            }
        }
    }
}
