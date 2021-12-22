package com.tl.flasher.utils;

import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */
public class HttpUtil {
    private static final Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * POST请求（同步）
     * </P>
     * 连接超时时间：1秒
     * 传输超时时间：3秒
     * @param url
     * @param params
     * @return
     */
    public final static String doPostFormSync(String url,Map<String,String> params) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000).setSocketTimeout(3000).build();
        httpPost.setConfig(requestConfig);
        List<NameValuePair> nvps = Lists.newArrayList();
        Set<String> keys = params.keySet();
        for(String k : keys){
            nvps.add(new BasicNameValuePair(k,params.get(k)));
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e);
        }
        return null;
    }

    /**
     * POST请求（同步）
     * </P>
     * 连接超时时间：1秒
     * 传输超时时间：3秒
     * @param url
     * @param entity
     * @return
     */
    public final static String doPostSync(String url,String entity) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000).setSocketTimeout(3000).build();
        httpPost.setConfig(requestConfig);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            httpPost.setEntity(new StringEntity(entity));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e);
        }
        return null;
    }

    /**
     * POST请求（异步）
     * 连接超时时间：1秒
     * 传输超时时间：3秒
     * @param url
     * @param params
     * @return
     */
    public final static String doPostFormAsyn(String url,Map<String,String> params) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000).setSocketTimeout(3000).build();
        httpPost.setConfig(requestConfig);
        List<NameValuePair> nvps = Lists.newArrayList();
        Set<String> keys = params.keySet();
        for(String k : keys){
            nvps.add(new BasicNameValuePair(k,params.get(k)));
        }
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            Future<HttpResponse> future = httpclient.execute(httpPost, null);
            HttpResponse response = future.get(3000,TimeUnit.MILLISECONDS);// 获取结果
            return EntityUtils.toString(response.getEntity());
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            logger.info(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.info(e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e);
        } catch (TimeoutException e) {
            e.printStackTrace();
            logger.info(e);
        }
        return null;
    }

    /**
     * POST请求（异步）
     * 连接超时时间：1秒
     * 传输超时时间：3秒
     * @param url
     * @param entity
     * @return
     */
    public final static String doPostAsyn(String url,String entity) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000).setSocketTimeout(3000).build();
        httpPost.setConfig(requestConfig);

        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
        try {
            httpPost.setEntity(new StringEntity(entity));
            Future<HttpResponse> future = httpclient.execute(httpPost, null);
            HttpResponse response = future.get(3000,TimeUnit.MILLISECONDS);// 获取结果
            return EntityUtils.toString(response.getEntity());
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            logger.info(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.info(e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e);
        } catch (TimeoutException e) {
            e.printStackTrace();
            logger.info(e);
        }
        return null;
    }
}
