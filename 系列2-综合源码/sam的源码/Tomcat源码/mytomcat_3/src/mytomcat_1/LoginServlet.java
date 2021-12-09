package mytomcat_1;

import java.io.IOException;

public class LoginServlet implements HttpServlet {

	@Override
	public void server(HttpRequest request, HttpResponse response) throws IOException {
	   //request.getParamter()
		String userName=request.getParamter("userName");
		String pwd=request.getParamter("pwd");
		if(userName !=null && userName.equals("sam") && pwd !=null && pwd.equals("456")){
			//有权限 放行
			response.writerFile("htmlfile/welcome.html");
		}else{
			response.writerFile("htmlfile/error.html");
		}
	}

}
