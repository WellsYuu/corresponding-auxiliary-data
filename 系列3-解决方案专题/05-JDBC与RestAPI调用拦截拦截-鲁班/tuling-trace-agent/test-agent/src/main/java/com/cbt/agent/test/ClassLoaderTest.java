package com.cbt.agent.test;/**
 * Created by Administrator on 2018/6/19.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/19
 **/
public class ClassLoaderTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String collectServletPath = "file:///G:\\git\\cbt-agent\\" +
                "agent-collect-servlet\\target\\cbt-agent-collect-servlet-1.0-SNAPSHOT.jar";
        String servletPath = "file:///C:\\Users\\Administrator\\.m2\\repository" +
                "\\javax\\servlet\\javax.servlet-api\\3.1.0\\javax.servlet-api-3.1.0.jar";
        URLClassLoader parent = new URLClassLoader(new URL[]{new URL(collectServletPath)});
        URLClassLoader child = new URLClassLoader(new URL[]{new URL(collectServletPath),
                new URL(servletPath)}, parent);
        Class<?> clas = child.loadClass("com.cbt.agent.collects.servlet.ServletResponseProxy");
        clas.newInstance();
    }
}
