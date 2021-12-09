package mytomcat_1;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * 读取配置文件类
 * @author Administrator
 *
 */
public class cofigUtils {
      public static Map<String,String> getClassName(String path) throws Exception{
    	  //spring MVC handlerMap 根据你的请求uri----找到相应的后台处理类 handler
    	  Map<String,String> handlerMap = new HashMap<String, String>();
    	  SAXReader Reader = new SAXReader();
    	  File file = new File(path);
    	  Document document= Reader.read(file);
    	   //拿到跟元素
    	  Element rootElemet=document.getRootElement();
    	@SuppressWarnings("unchecked")
		List<Element>childElements=rootElemet.elements();
    	for(Element childElement:childElements){
    		///key：/login.action
    		String key=childElement.element("url-pattern").getText();
    		//value：mytomcat_1.LoginServlet（全限定的类名）
    		String value=childElement.element("servlet-class").getText();
            //映射到缓存
    		handlerMap.put(key, value);
    	}
    	  return handlerMap;
      }
}
