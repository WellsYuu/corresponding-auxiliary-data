package mytomcat_1;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract
/**
 * Tomcat  Server v2.0  web
 * @author Sam
 *mytomcat_2/htmlfile/register.html
 */
public class Server {

	public static void main(String[] args) {
		//发生信息的电报机
		ServerSocket serverSocket = null;
		Socket client =null;
		try {
			//服务发生信息的电报机：ServerSocket 初始化
			serverSocket =new ServerSocket(9999);
			System.out.println("服务器初始化完毕，初始化注册端口是"+9999);
			//不断在接收客户端链接
			while (true) {
				//服务端阻塞等待客户端的Socket链接过来
				client=serverSocket.accept();
				//===============请求业务类处理范围===============
				InputStream in =client.getInputStream();
				HttpRequest request = new HttpRequest(in);
				String requesUri =request.getUri();
				//===============响应业务类处理范围===============
					OutputStream os =  client.getOutputStream();
					HttpResponse response = new HttpResponse(os);
					//传入uri:/htmlfile/login.html
					 //这里需要判断是否是静态资源
					if(isStatic(requesUri)){
						response.writerFile(requesUri.substring(1));
					}
					//到这里为止就是一个请求和一个响应的业务完成
					client.close();
				 }
				

		} catch (Exception e) {
		}

	}
//静态资源重复获取问题解决
	//是否是静态资源
		public static boolean isStatic(String uri){
			boolean isStatic = false;
	        //定义一个静态资源的集合 
			String [] suffixs={"html","css","jpg","js","jpeg","png"};
			for(String suffix:suffixs){
				if(uri.endsWith("."+suffix)){
					isStatic = true;
					break;
				}
			}
			return isStatic;
		}
}
