package com.tl.protocol;/*
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

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class RedisClient {

    private Socket socket;
    private  String host;
    private int port;

    public RedisClient() throws IOException {
        this.socket = new Socket("192.168.0.12",6379);
    }

    public RedisClient(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            this.socket = new Socket(host,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //set wk 2018
    public String set( String key, String value) throws IOException {
       //"*3\r\n$3\r\nset\r\n$2\r\nwk\r\n$4\r\n2018"
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("*3").append("\r\n");
        stringBuilder.append("$").append(CommandRedis.set.name().length()).append("\r\n");
        stringBuilder.append(CommandRedis.set).append("\r\n");
        stringBuilder.append("$").append(key.getBytes().length).append("\r\n");
        stringBuilder.append(key).append("\r\n");
        stringBuilder.append("$").append(value.getBytes().length).append("\r\n");
        stringBuilder.append(value).append("\r\n");
        socket.getOutputStream().write(stringBuilder.toString().getBytes());
        byte[] b=new byte[2048];
        socket.getInputStream().read(b);
        return new String(b);

    }


    public String get( String key) throws IOException {
        //"*3\r\n$3\r\nset\r\n$2\r\nwk\r\n$4\r\n2018"
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("*2").append("\r\n");
        stringBuilder.append("$").append(CommandRedis.get.name().length()).append("\r\n");
        stringBuilder.append(CommandRedis.get).append("\r\n");
        stringBuilder.append("$").append(key.getBytes().length).append("\r\n");
        stringBuilder.append(key).append("\r\n");
        socket.getOutputStream().write(stringBuilder.toString().getBytes());
        byte[] b=new byte[2048];
        socket.getInputStream().read(b);
        return new String(b);

    }

    public String setnx(String key, String value) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("*3").append("\r\n");
        stringBuffer.append("$5").append("\r\n");
        stringBuffer.append("setnx").append("\r\n");
        stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
        stringBuffer.append(key).append("\r\n");
        stringBuffer.append("$").append(value.getBytes().length).append("\r\n");
        stringBuffer.append(value).append("\r\n");
        socket.getOutputStream().write(stringBuffer.toString().getBytes());
        byte[] b = new byte[2048];
        socket.getInputStream().read(b );
        return new String(b);
    }

    // 管道
    public void pipeline(String key)throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("*2").append("\r\n");
        stringBuffer.append("$"+CommandRedis.subscribe.name().length()).append("\r\n");
        stringBuffer.append(CommandRedis.subscribe).append("\r\n");
        stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
        stringBuffer.append(key).append("\r\n");
        socket.getOutputStream().write(stringBuffer.toString().getBytes());
        InputStream inputStream = socket.getInputStream();
        while (true) {
            byte[] b = new byte[2048];
            inputStream.read(b );
            System.out.println(new String(b));
        }

    }

    //subscribe
    public void subscribe(String key)throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("*2").append("\r\n");
        stringBuffer.append("$"+CommandRedis.subscribe.name().length()).append("\r\n");
        stringBuffer.append(CommandRedis.subscribe).append("\r\n");
        stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
        stringBuffer.append(key).append("\r\n");
        socket.getOutputStream().write(stringBuffer.toString().getBytes());
        InputStream inputStream = socket.getInputStream();
        while (true) {
            byte[] b = new byte[2048];
            inputStream.read(b );
            System.out.println(new String(b));
        }
    }

    public void close(){
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
