package test.collects.httpClient;

import com.alibaba.dubbo.common.utils.IOUtils;
import com.cbt.agent.core.HttpAgentProxyHandle;
import org.junit.Test;
import test.collects.BasiceTest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by tommy on 16/10/22.
 */
public class HttpURLConnectionHandleTest extends BasiceTest {

    @Test
    public void httpSendTest()throws  Exception{
        // 设置协议流解析工厂
        HttpAgentProxyHandle.initializeHttp();
        URL url = new URL("https://www.baidu.com/s");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("myHead", "00082");
        conn.setRequestProperty("myHead2", "3sss00082");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();
        OutputStream output = conn.getOutputStream();
        output.write("wd=setRequestProperty&rsv_spt=1&rsv_iqid=0xb023e744000286e2&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=0&rsv_t=758cBYpRwOCavBv6yLyUsqLdIqmFntiW9XbRPY5Mz%2B%2FShMMbRip9ZSh0KPmZoidbGbL0&rsv_sug3=2&oq=setRequestProperty&rsv_pq=bdbc778400028785&inputT=4247359&rsv_sug4=4247359&rsv_jmp=slow".getBytes());
        output.flush();
        output.close();
        InputStream input = conn.getInputStream();
        System.out.println(Arrays.toString(IOUtils.readLines(input)));
        conn.disconnect();
        System.out.println("");
    }
}
