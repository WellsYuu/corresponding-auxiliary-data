package mytomcat_1;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract
/**
 * Tomcat  Server v1.0
 * @author Sam
 *
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
				//===================== 解析请求 HttpRequest===============================
				//对客户端里面的请求信息进行一个处理
				InputStream in =client.getInputStream();
				//定义一个读取的缓冲池  主要是在InputStream流当中读取字节  字节数组来承接这些字节
				byte[] buff =new byte[1024];
				 int len=in.read(buff);
				 if(len>0){
					//将读取出来的字节信息 转化成明文信息
					 String msg = new String(buff,0,len);
					System.out.println("客户端的请求信息：======="+msg+"========");
					//立马需要响应信息出去 需要输出流  这样拿就是拿到我们客户端的输出流
					
					//client.getOutputStream  保证输出到客户端当中
					//=========================响应客户端 HttpResponse============================
					OutputStream os =  client.getOutputStream();
					SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//构建一个响应头信息 200 ok
					StringBuffer sb=new StringBuffer();
					//平凑响应头信息
					sb.append("HTTP/1.1 200 OK\n");
					sb.append("Content-Type: text/html;charset=UTF-8\n");
					sb.append("\r\n");
					//响应正文
					String html = "<html><head><title>欢迎各位同学！</title></head><body>当前时间："
							+"<font size='14' color='blue'>"
							+ formt.format(new Date())
							+"</font>"
							+ "<br/>服务器回复：<font size='14' color='blue'>大家今天有收获吗？</font></body></html>";
					//将响应头+正文 全部链接在一起
					sb.append(html);
					//os =  client.getOutputStream();
					os.write(sb.toString().getBytes());
					//===================================================
					os.flush();
					os.close();
					//到这里为止就是一个请求和一个响应的业务完成
					client.close();
				 }
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
