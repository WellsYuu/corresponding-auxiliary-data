package test.base.util;


import com.cbt.agent.common.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;

/**
 * Created by tommy on 16/10/10.
 */
public class UrlJarTest {
    public static void main(String[] args) throws Exception {
        byte[] bytes=new byte[200];
        URL url = new URL("jar:file:/Users/tommy/git/cbt-agent/out/cbt-agent-bootstrap.jar!/cbt-agent-base.jar");
        Path path = Files.createTempFile("jar_cache", null, new FileAttribute[0]);
        Files.copy(url.openStream(), path, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
        url= path.toUri().toURL();
        path.toFile().deleteOnExit();
        URLClassLoader ucl = new URLClassLoader(new URL[]{}, null);
        Method method = ReflectionUtils.findMethod(URLClassLoader.class, "addURL", URL.class);
        method.setAccessible(true);
        ReflectionUtils.invokeMethod(method, ucl, url);

        Class<?> cl =  ucl.loadClass("com.cbt.agent.trace.AppInfo");
        System.out.println(cl.getName());

        System.getProperty("temp.dir");
    }


}

