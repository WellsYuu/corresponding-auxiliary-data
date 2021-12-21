package test.base.util;

import org.junit.Test;
import sun.misc.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by tommy on 16/10/10.
 */
public class ByteUrlConnectionTest {
   
    @Test
    public void testCopyStream() throws Exception {
        FileInputStream finput=new FileInputStream("/Users/tommy/git/cbt-agent/bootstrap/target/dd.jar");
        byte[] bytes= IOUtils.readFully(finput,-1,false);
        URL url=new URL("file:/Users/tommy/git/cbt-agent/bootstrap/target/dd.jar");
        FileOutputStream fout=new FileOutputStream("/Users/tommy/git/cbt-agent/bootstrap/target/dd2.jar");
        bytes=  IOUtils.readFully(url.openStream(),-1,false);
        fout.write(bytes);
        fout.flush();
        fout.close();
    }

    public static void main(String[] args) {
        try {
            loadClassTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public static void loadClassTest()throws Exception{
        FileInputStream finput=new FileInputStream("/Users/tommy/git/cbt-agent/bootstrap/target/dd.jar");
        byte[] bytes= IOUtils.readFully(finput,-1,false);
        bytes=new byte[]{1,2,3,4,4};
       URL url=new URL("file:/Users/tommy/git/cbt-agent/bootstrap/target/dd.jar");
        URLClassLoader ucl = new URLClassLoader(new URL[]{url}, null);
       // Method method = ReflectionUtils.findMethod(URLClassLoader.class, "addURL", URL.class);
       // method.setAccessible(true);
        //ReflectionUtils.invokeMethod(method, ucl, url);
        Class<?> cl = ucl.loadClass("com.cbt.agent.trace.AppInfo");
        System.out.println(cl.getName());
        System.getProperty("temp.dir");
    }
}
