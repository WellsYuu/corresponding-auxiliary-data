package mytomcat_1;

import java.io.IOException;

/**
 * HttpServlet：网络请求处理的规范
 * @author Administrator
 * 
 */
public interface HttpServlet {
     //处理HttpServlet
	public void server(HttpRequest request,HttpResponse response) throws IOException;
}
