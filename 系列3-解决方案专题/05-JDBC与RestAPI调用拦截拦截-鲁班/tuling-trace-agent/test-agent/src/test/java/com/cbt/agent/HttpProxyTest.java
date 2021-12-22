package com.cbt.agent;

import com.cbt.agent.test.http.HttpProxy;
import org.junit.Before;
import org.junit.Test;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * Created by Tommy on 2017/11/24.
 */
public class HttpProxyTest {

    @Before
    public void HttpProxyTest(){
        HttpProxy.init();
    }

    @Test
    public void ProxyTest() throws IOException {
        URL url = new URL("https://www.baidu.com");
        URLConnection conn = url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();
        OutputStream output = conn.getOutputStream();
        output.write("a=c&b=1".getBytes());
        InputStream input = conn.getInputStream();
        byte[] bytes = IOUtils.readFully(input, -1, false);
        System.out.println(new String(bytes));
    }
}
