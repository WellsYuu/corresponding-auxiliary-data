package com.bit.api.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.api.common.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.bit.api.common.ApiException;
import com.bit.api.common.UtilJson;
import com.bit.api.core.ApiStore.ApiRunnable;
import com.bit.api.service.GoodsServiceImpl.Goods;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Tommy
 */
public class ApiGatewayHand implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayHand.class);

    private static final String METHOD = "method";
    private static final String PARAMS = "params";

    ApiStore apiSorte;
    final ParameterNameDiscoverer parameterUtil;
    private TokenService tokenService;

    public ApiGatewayHand() {
        parameterUtil = new LocalVariableTableParameterNameDiscoverer();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        apiSorte = new ApiStore(context);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        apiSorte.loadApiFromSpringBeans();
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) {
        // 系统参数验证
        String params = request.getParameter(PARAMS);
        String method = request.getParameter(METHOD);
        Object result;
        ApiRunnable apiRun = null;
        ApiRequest apiRequest = null;
        try {
            apiRun = sysParamsValdate(request);
            // 构建apiRequest
            apiRequest = buildApiRequest(request);
            // 签名验证
            if (apiRequest.getAccessToken() != null)
                signCheck(apiRequest);

            if (apiRun.getApiMapping().useLogin()) {
                if (apiRequest.isLogin()) {
                     throw new ApiException("009","调用失败：用户未登陆");
                }
            }


            logger.info("请求接口={" + method + "} 参数=" + params + "");
            Object[] args = buildParams(apiRun, params, request, response,apiRequest);
            result = apiRun.run(args);
        } catch (ApiException e) {
            response.setStatus(500);// 封装异常并返回
            logger.error("调用接口={" + method + "}异常  参数=" + params + "", e);
            result = handleError(e);
        } catch (InvocationTargetException e) {
            response.setStatus(500);// 封装业务异常并返回
            logger.error("调用接口={" + method + "}异常  参数=" + params + "", e.getTargetException());
            result = handleError(e.getTargetException());
        } catch (Exception e) {
            response.setStatus(500);// 封装业务异常并返回
            logger.error("其他异常", e);
            result = handleError(e);
        }

        // 统一返回结果
        returnResult(result, response);
    }

    private ApiRequest buildApiRequest(HttpServletRequest request) {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setAccessToken(request.getParameter("token"));
        apiRequest.setSign(request.getParameter("sign"));
        apiRequest.setTimestamp(request.getParameter("timestamp"));
        apiRequest.seteCode(request.getParameter("eCode"));
        apiRequest.setuCode(request.getParameter("uCode"));
        apiRequest.setParams(request.getParameter("params"));
        return apiRequest;
    }

    private ApiRequest signCheck(ApiRequest request) throws ApiException {
        Token token = tokenService.getToken(request.getAccessToken());
        if (token == null) {
            throw new ApiException("验证失败：指定'Token'不存在");
        }
        if (token.getExpiresTime().before(new Date())) {
            throw new ApiException("验证失败：指定'Token'已失效");
        }
        // 生成签名
        String methodName = request.getMethodName();
        String accessToken = token.getAccessToken();
        String secret = token.getSecret();
        String params = request.getParams();
        String timestamp = request.getTimestamp();
        String sign = Md5Util.MD5(secret + methodName + params + token + timestamp + secret);

        if (!sign.toUpperCase().equals(request.getSign())) {
            throw new ApiException("验证失败：签名非法");
        }

        // 时间验证
        if (Math.abs(Long.valueOf(timestamp) - System.currentTimeMillis()) > 10 * 60 * 1000) {
            throw new ApiException("验证失败：签名失效");
        }

        request.setLogin(true);
        request.setMemberId(token.getMemberId());
        return request;
    }


    private Object handleError(Throwable throwable) {
        String code = "";
        String message = "";

        if (throwable instanceof ApiException) {
            code = "0001";
            message = throwable.getMessage();
        } // 扩展异常规范
        else {
            code = "0002";
            message = throwable.getMessage();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("error", code);
        result.put("msg", message);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(out);
        throwable.printStackTrace(stream);
//        result.put("stack", out.toString());
        return result;
    }

    private ApiRunnable sysParamsValdate(HttpServletRequest request) throws ApiException {
        String apiName = request.getParameter(METHOD);
        String json = request.getParameter(PARAMS);

        ApiRunnable api;
        if (apiName == null || apiName.trim().equals("")) {
            throw new ApiException("调用失败：参数'method'为空");
        } else if (json == null) {
            throw new ApiException("调用失败：参数'params'为空");
        } else if ((api = apiSorte.findApiRunnable(apiName)) == null) {
            throw new ApiException("调用失败：指定API不存在，API:" + apiName);
        }
        // 多一个签名参数
        // 
        return api;
    }

    /***
     * 验证业务参数，和构建业务参数对象
     * @param run
     * @param paramJson
     * @param request
     * @param response
     * @param apiRequest
     * @return
     * @throws ApiException
     */
    private Object[] buildParams(ApiRunnable run, String paramJson, HttpServletRequest request, HttpServletResponse response, ApiRequest apiRequest)
            throws ApiException {
        Map<String, Object> map = null;
        try {
            map = UtilJson.toMap(paramJson);
        } catch (IllegalArgumentException e) {
            throw new ApiException("调用失败：json字符串格式异常，请检查params参数 ");
        }
        if (map == null) {
            map = new HashMap<>();
        }

        Method method = run.getTargetMethod();// javassist
        List<String> paramNames = Arrays.asList(parameterUtil.getParameterNames(method));
        // goods ,id
        Class<?>[] paramTypes = method.getParameterTypes(); //反射

        for (Map.Entry<String, Object> m : map.entrySet()) {
            if (!paramNames.contains(m.getKey())) {
                throw new ApiException("调用失败：接口不存在‘" + m.getKey() + "’参数");
            }
        }
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            if (paramTypes[i].isAssignableFrom(HttpServletRequest.class)) {
                args[i] = request;
            } else if (paramTypes[i].isAssignableFrom(ApiRequest.class)){
                args[i] = apiRequest;
            }else if (map.containsKey(paramNames.get(i))) {
                try {
                    args[i] = convertJsonToBean(map.get(paramNames.get(i)), paramTypes[i]);
                } catch (Exception e) {
                    throw new ApiException("调用失败：指定参数格式错误或值错误‘" + paramNames.get(i) + "’"
                            + e.getMessage());
                }
            } else {
                args[i] = null;
            }
        }
        return args;
    }

    // 将MAP转换成具体的目标方方法参数对象
    private <T> Object convertJsonToBean(Object val, Class<T> targetClass) throws Exception {
        Object result = null;
        if (val == null) {
            return null;
        } else if (Integer.class.equals(targetClass)) {
            result = Integer.parseInt(val.toString());
        } else if (Long.class.equals(targetClass)) {
            result = Long.parseLong(val.toString());
        } else if (Date.class.equals(targetClass)) {
            if (val.toString().matches("[0-9]+")) {
                result = new Date(Long.parseLong(val.toString()));
            } else {
                throw new IllegalArgumentException("日期必须是长整型的时间戳");
            }
        } else if (String.class.equals(targetClass)) {
            if (val instanceof String) {
                result = val;
            } else {
                throw new IllegalArgumentException("转换目标类型为字符串");
            }
        } else {
            result = UtilJson.convertValue(val, targetClass);
        }
        return result;
    }

    private void returnResult(Object result, HttpServletResponse response) {
        try {
            UtilJson.JSON_MAPPER.configure(
                    SerializationFeature.WRITE_NULL_MAP_VALUES, true);
            String json = UtilJson.writeValueAsString(result);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html/json;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            if (json != null)
                response.getWriter().write(json);
        } catch (IOException e) {
            logger.error("服务中心响应异常", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) {
        //{"goods":{"goodsName":"daa","goodsId":"1111"},"id":19}
        String mapString = "{\"goods\":{\"goodsName\":\"daa\",\"goodsId\":\"1111\"},\"id\":19}";
        Map<String, Object> map = UtilJson.toMap(mapString);
        System.out.print(map);
        UtilJson.convertValue(map.get("goods"), Goods.class);
        String str = "{\"goodsName\":\"daa\",\"goodsId\":\"1111\"}";
        Goods goods = UtilJson.convertValue(str, Goods.class);
        System.out.print(goods);
    }
}
