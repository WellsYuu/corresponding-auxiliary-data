package mytomcat_1;

import java.io.IOException;
import java.io.InputStream;

/**
 * 对客户端进行处理的业务类
 * 
 * @author Sam
 *
 */
public class HttpRequest {
	// 根据客户端的不同请求uri 而响应不同的资源给客户端
	private String uri;
    /**
     * uri:/htmlfile/login.html
     * 给响应类提高该怎么样响应
     * @return
     */
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
			System.out.println("客户端的请求信息：=======" + msg + "========");
			//解析出来uri
			uri =msg.substring(msg.indexOf("/"), msg.indexOf("HTTP/1.1")-1);
			System.out.println("uri：=======" + uri + "========");

		} else {
			System.out.println("bad Request!");
		}
	}
}
