package mytomcat_1;

import java.io.IOException;
/**
 * LoginServlet：登录处理
 * @author sam
 *
 */
public class LoginServlet implements HttpServlet {

	@Override
	public void server(HttpRequest request, HttpResponse response) throws IOException {
	   //Servlet:request.getParamter() 
		String userName=request.getParamter("userName");
		String pwd=request.getParamter("pwd");
		if(userName !=null && userName.equals("sam") && pwd !=null && pwd.equals("456")){
			//有权限 放行 转发
			response.writerFile("htmlfile/welcome.html");
		}else{
			response.writerFile("htmlfile/error.html");
		}
	}

}
