package mytomcat_1;

import java.io.IOException;

/**
 * RegiestServlet:注册处理类 
 * @author Administrator
 *
 */
public class RegiestServlet implements HttpServlet {

	@Override
	public void server(HttpRequest request, HttpResponse response) throws IOException {
		response.writerFile("htmlfile/registerSuccess.html");
		
	}

	
}
