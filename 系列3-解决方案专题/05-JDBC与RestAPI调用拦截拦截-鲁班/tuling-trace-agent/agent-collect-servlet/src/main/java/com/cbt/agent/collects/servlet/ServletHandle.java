package com.cbt.agent.collects.servlet;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbt.agent.collect.CollectHandle;
import com.cbt.agent.collect.Event;
import com.cbt.agent.collect.EventType;
import com.cbt.agent.collect.InParams;
import com.cbt.agent.collect.OutResult;
import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.common.util.Assert;
import com.cbt.agent.common.util.JsonUtils;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceSession;

public class ServletHandle implements CollectHandle {
    private static final String TARGET_CLASS = "javax.servlet.http.HttpServlet";
    private static final String DISPATCH_NAME = "service";
    private Logger logger= LoggerFactory.getLogger(ServletHandle.class);
    // 正则匹配资源文件
    private String ignoreRegex= "^.*\\.(?:css|js|gif|jpg|png|svg)(\\?.*){0,1}$";
    // 后缀匹配资源文件
    private String[] ignoreSuffix={".css",".js",".gif",".jpg",".png",".svg"};
    @Override
    public Event invokerBefore(Event event, InParams in) {
        Assert.isTrue(TARGET_CLASS.equals(in.className));
        Assert.isTrue(DISPATCH_NAME.equals(in.methodName));
        HttpServletRequest request= (HttpServletRequest) in.args[0];

        //勿略静态资源文件
        for(String suffix:ignoreSuffix){
            if(request.getRequestURI().endsWith(suffix)){
                return  null;
            }
        }



        Event newEvent = new Event(getEventType(), true);
        TraceNode node = ServletHanderWapper.handlerDoDispatchMethod((HttpServletRequest) in.args[0], in.className,in.methodName);
        // 重构response参数
        ServletResponseProxy responseProxy = new ServletResponseProxy((HttpServletResponse) in.args[1]);
        in.args[1] = responseProxy;
        newEvent.setData(node);
        newEvent.setAttribute("responseProxy", responseProxy);
        return newEvent;
    }

    @Override
    public void invokerAfter(Event event, OutResult out) {
        Assert.isTrue(TARGET_CLASS.equals(out.className));
        if (TraceContext.getDefault().getCurrentSession() == null) {
            return;
        } else if (event == null || !event.isActive()) {
            return;
        }
        if (DISPATCH_NAME.equals(out.methodName)) {
            TraceContext context=TraceContext.getDefault();
            TraceSession session = context.getCurrentSession();
            TraceNode node = (TraceNode) event.getData();
            node.setEndTime(System.currentTimeMillis());
            ServletResponseProxy responseProxy= (ServletResponseProxy) event.getAttribute("responseProxy");
            String responseValue= getResponseValue((HttpServletRequest)out.args[0],responseProxy);
            node.setOutParam(responseValue);
            if(context.getStackSession()!=null){
                context.getStackSession().close();
                String stackJson = JsonUtils.toJson(context.getStackSession().getAllNodes(), getClass().getClassLoader());
                node.setStackNodes(stackJson);
            }

            session.addNode(node);
            context.closeTrace(session);
        }
    }



    @Override
    public EventType getEventType() {
        return EventType.servlet;
    }
    private String getResponseValue(HttpServletRequest request, ServletResponseProxy responseProxy) {
        String result=new String();
        HttpServletResponse response = (HttpServletResponse) responseProxy.getResponse();
        String contentType = responseProxy.getContentType();
        contentType=contentType==null?"":contentType;
        if(contentType.contains("json")){
            StringWriter copyWriter = responseProxy.getCopyWriter();
            ByteArrayOutputStream copyOutput = responseProxy.getCopyOutput();
            if(copyWriter!=null){
                result=copyWriter.toString();
            }else if(copyOutput!=null){
                try {
                    result = response.getCharacterEncoding() != null ?
                            copyOutput.toString(response.getCharacterEncoding()) : copyOutput.toString();
                } catch (UnsupportedEncodingException e) {
                    logger.error("获取http输出转码异常:"+request.getServletPath(), e);
                }
            }
            responseProxy.clearCopyOut();
        }else if(contentType.contains("html")){
            Enumeration<String> names = request.getAttributeNames();
            Map<String,Serializable> values=new HashMap<>();
            for(int i=0;names.hasMoreElements();i++){
                String name=names.nextElement();
                Object attr = request.getAttribute(name);
                if(attr instanceof Serializable){
                    values.put(name, (Serializable) attr);
                }
            }
            result = values.size() > 0 ? JsonUtils.toJson(values, Thread.currentThread().getContextClassLoader()) : "";
        }
        return result;

    }
}
