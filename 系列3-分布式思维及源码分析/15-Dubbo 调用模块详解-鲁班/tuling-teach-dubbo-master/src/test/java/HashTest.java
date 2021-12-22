import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Tommy on 2017/12/7.
 */
public class HashTest {
    public static void main(String[] args) {
        HashMap<String,String> map=new HashMap<String, String>();
        map.put("abc","abc");
        map.get("abc");
        "abc".hashCode();
    }
    public void httpTest() throws MalformedURLException {
        URL url=new URL("http://192.168.0.144:20880/com.tuling.teach.service.DemoService?method=");
    }
}
