package com.cbt.agent.test.http;

import java.io.IOException;
import java.net.*;

// 角色：代理Class
public class HttpProxy {

    public static void init() {
        URL.setURLStreamHandlerFactory(new ProxyURLStreamHandlerFactory());
    }

    static class ProxyURLStreamHandlerFactory implements URLStreamHandlerFactory {
        @Override
        public URLStreamHandler createURLStreamHandler(String protocol) {
            if ("http".equals(protocol)) {
                return new AgentHttpHandler();
            } else if ("https".equals(protocol)) {
                return new AgentHttpsHandler();
            }
            return null;
        }
    }

    static class AgentHttpHandler extends sun.net.www.protocol.http.Handler {
        @Override
        protected URLConnection openConnection(URL url, Proxy proxy) throws IOException {
            HttpURLConnection connection = (HttpURLConnection) super.openConnection(url, proxy);
            return new ProxyHttpUrlConnection(connection, url);
        }

        @Override
        protected URLConnection openConnection(URL url) throws IOException {
            return openConnection(url, (Proxy) null);
        }
    }

    // 静态代理
    static class AgentHttpsHandler extends sun.net.www.protocol.https.Handler {
        @Override
        protected URLConnection openConnection(URL url, Proxy proxy) throws IOException {
            HttpURLConnection connection = (HttpURLConnection) super.openConnection(url, proxy);
            return new ProxyHttpUrlConnection(connection, url);
        }

        @Override
        protected URLConnection openConnection(URL url) throws IOException {
            return openConnection(url, (Proxy) null);
        }
    }


}
