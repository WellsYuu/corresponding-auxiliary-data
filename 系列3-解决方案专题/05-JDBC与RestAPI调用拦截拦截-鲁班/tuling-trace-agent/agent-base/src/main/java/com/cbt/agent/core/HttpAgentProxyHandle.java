package com.cbt.agent.core;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;

public class HttpAgentProxyHandle implements URLStreamHandlerFactory {
    private static volatile HttpAgentProxyHandle instance;
    private static Field factoryField = null;

    static {
        try {
            factoryField = URL.class.getDeclaredField("factory");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void initializeHttp() throws Exception {
        if (instance == null) {
            instance = new HttpAgentProxyHandle();
            Object facotry = null;
            boolean oldAccessible = factoryField.isAccessible();
            factoryField.setAccessible(true);
            facotry = factoryField.get(URL.class);
            factoryField.setAccessible(oldAccessible);
            if (facotry == null) {
                URL.setURLStreamHandlerFactory(instance);
            } else if (facotry.getClass().getName().equals("org.apache.naming.resources.DirContextURLStreamHandlerFactory")) {
                Method method = facotry.getClass().getMethod("addUserFactory", URLStreamHandlerFactory.class);
                method.invoke(facotry.getClass(), instance);
            } else {
                throw new Exception("url handle factory already defined class=" + facotry.getClass().getName());
            }
        }
    }

    @Override
    public URLStreamHandler createURLStreamHandler(final String protocol) {
        if ("http".equals(protocol)) {
            return new AgentHttpHandler();
        } else if ("https".equals(protocol)) {
            return new AgentHttpsHandler();
        }
        return null;
    }

    class AgentHttpHandler extends sun.net.www.protocol.http.Handler {
        @Override
        protected URLConnection openConnection(URL arg0, Proxy arg1) throws IOException {
            return createAgentHttpUrlConnection((HttpURLConnection) super.openConnection(arg0, arg1), arg0);
        }

        @Override
        protected URLConnection openConnection(URL arg0) throws IOException {
            return openConnection(arg0, (Proxy) null);
        }
    }

    class AgentHttpsHandler extends sun.net.www.protocol.https.Handler {
        @Override
        protected URLConnection openConnection(URL arg0, Proxy arg1) throws IOException {
            return createAgentHttpUrlConnection((HttpURLConnection) super.openConnection(arg0, arg1), arg0);
        }

        @Override
        protected URLConnection openConnection(URL arg0) throws IOException {
            return openConnection(arg0, (Proxy) null);
        }
    }

    public static final String agetHttpConnnClass = "com.cbt.agent.collects.httpClient.AgentHttpUrlConnection";

    public HttpURLConnection createAgentHttpUrlConnection(HttpURLConnection conn, URL url) {
        ClassLoader load = Thread.currentThread().getContextClassLoader();
        try {
            Class<?> cla = load.loadClass(agetHttpConnnClass);
            Constructor<?> cons = cla.getConstructor(new Class[]{HttpURLConnection.class, URL.class});
            return (HttpURLConnection) cons.newInstance(conn, url);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
