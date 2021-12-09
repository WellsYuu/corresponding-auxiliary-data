package mytomcat_1;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 对客户端进行处理的业务类
 * 
 * @author Sam
 *
 */
public class HttpRequest {
	// 根据客户端的不同请求uri 而响应不同的资源给客户端
	private String uri;
	// 存储我们动态请求参数的缓存 userName=sam&pwd=123
	private HashMap<String, String> paramMap = new HashMap<String, String>();
    //非常经典的方法
	public String getParamter(String key){
		return paramMap.get(key);
	}
	public String getUri() {
		return uri;
	}

	// socket=serverSocket.accept();
	// socket.getInputStream()
	// 解析uri发生发生时间点在 输入流管道接进来的时候就应该进行解析
	public HttpRequest(InputStream is) throws IOException {
		// 对我们的请求字节流进解析
		resolverRequest(is);
	}

	// resolverRequest：解析请求具体实现
	private void resolverRequest(InputStream is) throws IOException {
		// 定义一个读取的缓冲池 主要是在InputStream流当中读取字节 字节数组来承接这些字节
		byte[] buff = new byte[1024];
		int len = is.read(buff);
		if (len > 0) {
			// 将读取出来的字节信息 转化成明文信息
			String msg = new String(buff, 0, len);
			// 动态判断请求头 变化截取位置
			int startIndex = msg.indexOf("GET") + 4;
			if (msg.indexOf("POST") != -1) {
				startIndex = msg.indexOf("POST") + 5;
			}
			// 结速的位置
			int end = msg.indexOf("HTTP/1.1") - 1;
			System.out.println("客户端的请求信息：=======" + msg + "========");
			//定义一个参数字符串  userName=sam&pwd=123
			String paramString =null;
			// 解析出来uri
			uri = msg.substring(startIndex, end);
			if (msg.startsWith("GET")) {
				System.out.println("get请求方式 uri:" + uri);
			} else if (msg.startsWith("POST")) {
                 //请求带来参数接续出来
				int parmStart =msg.lastIndexOf("\n");
				paramString =msg.substring(parmStart+1);
				System.out.println("post请求方式 paramString:" + paramString);
			    //userName=sam&pwd=123
				//参数不为空 切 不为空字符串
				if(paramString !=null && !("".equals(paramString.trim()))){
					if(paramString.contains("&")){
						//{userName=sam,pwd=123}
						String [] temp =paramString.split("&");
					   for(String parm:temp){
						   //userName=sam
						   String [] paramTemp =parm.split("=");
						   System.out.println(paramTemp[0]+":"+paramTemp[1]);
						   //将参数存入缓存
						   paramMap.put(paramTemp[0], paramTemp[1]);
					    }
					 //userName=sam
					}else if(paramString.contains("=")){
						  String [] paramTemp =paramString.split("=");
						  paramMap.put(paramTemp[0], paramTemp[1]);
					}
				}
			}

		} else {
			System.out.println("bad Request!");
		}
	}
}
