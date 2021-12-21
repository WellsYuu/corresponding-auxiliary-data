package com.cbt.agent.collects.servlet;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.common.util.JsonUtils;
import com.cbt.agent.common.util.NetUtils;
import com.cbt.agent.common.util.StringUtils;
import com.cbt.agent.trace.StackNode;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceFrom;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceRequest;
import com.cbt.agent.trace.TraceSession;

/**
 * http 请求处理类
 * 
 * @since 0.1.0
 */
public class ServletHanderWapper {
    private static final Logger logger = LoggerFactory.getLogger(ServletHanderWapper.class);

	private static final Set<String> PHP_URL_PREFIX = new HashSet<String>(5);
    private static final Map<String, String> configTemplate;
	static {
		PHP_URL_PREFIX.add("method");
		PHP_URL_PREFIX.add("timestamp");
        configTemplate = new HashMap<>();
        configTemplate.put(TraceContext.TRACE_ID, null);
        configTemplate.put(TraceContext.TRACE_LEVEL, null);
        configTemplate.put(TraceContext.UPLOAD_PATH, null);
        configTemplate.put(TraceContext.DEBUG_ID, null);
	}
    //TODO 后期需要重构,通过HTTP REquest 获取TraceRequest
	protected static TraceNode handlerDoDispatchMethod(HttpServletRequest request, String targetClassName, String methodName) {

        Properties properties = option(request);
		TraceRequest traceRequest = new TraceRequest();
		traceRequest.setTraceId(properties.getProperty(TraceContext.TRACE_ID, TraceSession.createTraceId()));
		traceRequest.setParentRpcId("0");
		traceRequest.setProperties(properties);

        StackNode stackNode = new StackNode(99999L, targetClassName.replaceAll("[.]", "/"), methodName);
        TraceSession session = TraceContext.getDefault().openTrace(traceRequest,stackNode);
		// 记录开始时间
		String rpcId = session.getCurrentRpcId();
		TraceNode node = new TraceNode();
		node.setNodeType("http");
		node.setTraceId(session.getTraceId());
		node.setRpcId(session.getCurrentRpcId());
		node.setBeginTime(System.currentTimeMillis());
		node.setAddressIp(NetUtils.getLocalHost());
		node.setFromIp(HttpUtils.getIp(request));

		node.setServicePath(request.getRequestURL().toString());
		node.setServiceName(request.getRequestURI());
        node.setInParam(JsonUtils.toJson(buildRequestParam(request), Thread.currentThread().getContextClassLoader()));

		session.setAttribute(rpcId + ".node", node);
		node.setResultState("succeed");
		TraceFrom traceFrom = new TraceFrom();
		traceFrom.setTraceId(node.getTraceId());
		traceFrom.setSource("unknown");

		StringBuffer accessPath = new StringBuffer(request.getRequestURL().toString());
		// 拼装参数
		StringBuffer urlParam = new StringBuffer();
		for (Entry<String, String[]> e : request.getParameterMap().entrySet()) {
			if (isExclude(targetClassName, e.getKey())) {
				urlParam.append("&");
				urlParam.append(e.getKey() + "=" + e.getValue()[0]);
			}
		}
		if (urlParam.length() > 0) {
			urlParam.deleteCharAt(0);
			accessPath.append("?");
			accessPath.append(urlParam);
		}
		traceFrom.setAccessPath(accessPath.toString());
		traceFrom.setRefererPath(request.getHeader("Referer")); // 获取引用页
		traceFrom.setClientId("unknown");
		traceFrom.setClientIp(node.getFromIp());
		traceFrom.setDebugId(properties.getProperty(TraceContext.DEBUG_ID));
		traceFrom.setDebugParams(properties.toString());
		if (StringUtils.hasText(traceFrom.getDebugId())) {
			traceFrom.setTraceLevel("debug");
		} else {
			traceFrom.setTraceLevel("info");
		}
		traceFrom.setCreateTime(System.currentTimeMillis());
		session.addTraceFrom(traceFrom);
		return node;
	}

	private static HashMap<String, Serializable> buildRequestParam(HttpServletRequest request) {
		String[] values;
		HashMap<String, Serializable> params = new HashMap<>();
		for (String name : Collections.list(request.getParameterNames())) {
			values = request.getParameterValues(name);
			if (values.length == 1) {
				params.put(name, values[0]);
			} else {
				params.put(name, values);
			}
		}
		return params;
	}

	private static boolean isExclude(String className, String key) {
		return true;
	}

    public static Properties option(HttpServletRequest request) {
        // Enumeration<String> headers = request.getHeaderNames();
        // while(headers.hasMoreElements()){
        // String headerName = headers.nextElement();
        // log.info("Header from request:" + headerName + ":" +
        // request.getHeader(headerName));
        // }
        // Cookie[] cookies = request.getCookies();
        // if(cookies != null){
        // for(Cookie cookie : cookies){
        // log.info("Cookie from request:" + cookie.getName() + ":" +
        // cookie.getValue());
        // }
        // }

        Properties paramProperties = getByParam(request);
        if (paramProperties != null) {
            return paramProperties;
        }
        Properties headerProperties = getByHead(request);
        if (headerProperties != null) {
            return headerProperties;
        }
        Properties cookieProperties = getByCookie(request);
        if (cookieProperties != null) {
            return cookieProperties;
        }
        return new Properties();
    }



    public static Properties getByCookie(HttpServletRequest request) {
        Properties properties = new Properties();
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                // logger.info("Cookie of URL:" +
                // request.getRequestURL().toString() + "|" + c.getName() + "/"
                // + c.getValue());
                if (configTemplate.containsKey(c.getName())) {
                    properties.put(c.getName(), c.getValue());
                }
            }
        }
        if (properties.containsKey(TraceContext.UPLOAD_PATH)) {// url 解码
            String up = properties.getProperty(TraceContext.UPLOAD_PATH);
            try {
                properties.setProperty(TraceContext.UPLOAD_PATH, URLDecoder.decode(up, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("调用链url转码失败", e);
            }
        }
        return properties;
    }

    public static Properties getByHead(HttpServletRequest request) {
        String traceDebugId = request.getHeader("trace-debug-id");
        if (traceDebugId != null && traceDebugId.length() > 0) {
            Properties properties = new Properties();
            properties.setProperty(TraceContext.DEBUG_ID, TraceContext.DEBUG_CONSOLE_KEY + traceDebugId);
            Properties config = TraceContext.getDefault().getAllProperties();
            String uploadPath = config.getProperty(TraceContext.UPLOAD_PATH);
            if (uploadPath == null || uploadPath.length() == 0) {
                logger.error("调用链无上传url配置");
            } else {
                properties.setProperty(TraceContext.UPLOAD_PATH, uploadPath);
            }
            return properties;
        }
        return null;
    }

    public static Properties getByParam(HttpServletRequest request) {
        String traceDebugId = request.getParameter(TraceContext.DEBUG_ID);
        if (traceDebugId != null && traceDebugId.length() > 0) {
            Properties properties = new Properties();
            properties.setProperty(TraceContext.DEBUG_ID, TraceContext.DEBUG_CONSOLE_KEY + traceDebugId);
            Properties config = TraceContext.getDefault().getAllProperties();
            String uploadPath = config.getProperty(TraceContext.UPLOAD_PATH);
            if (uploadPath == null || uploadPath.length() == 0) {
                logger.error("调用链无上传url配置");
            } else {
                properties.setProperty(TraceContext.UPLOAD_PATH, uploadPath);
            }
            return properties;
        }
        return null;
    }
}
