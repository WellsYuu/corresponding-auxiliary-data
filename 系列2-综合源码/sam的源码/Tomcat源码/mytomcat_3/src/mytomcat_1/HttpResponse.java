package mytomcat_1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 响应客户端请求业务处理类：HttpResponse
 * 本类主要是操作输出流OutputStream
 * @author Sam
 *
 */
public class HttpResponse {
   private OutputStream os =null;
   public  HttpResponse(OutputStream os) throws IOException{
	   //将传入进来的OutputStream os 本地化  才能本类的方法操作
	   this.os =os;
   }
   //本类的方法就可与用这个os 去做响应
   public void writerFile(String path)throws IOException{
	   //引入一个文件读取流 用来读取文件的路径path 
	   //就是一个静态页面 静态页面有 图片 有文字 有样式 用字节流读取 是万能
	   FileInputStream fis= new FileInputStream(path);
	   byte[] buff =new byte[1024];
		 int len=0;
	   StringBuffer sb=new StringBuffer();
		//平凑响应头信息
		sb.append("HTTP/1.1 200 OK\n");
		sb.append("Content-Type: text/html;charset=UTF-8\n");
		sb.append("\r\n");
		//响应头信息写出去
		os.write(sb.toString().getBytes());
		while ((len=fis.read(buff))!=-1) {
			//还有信息没有写完
			os.write(buff,0,len);
		}
		//出了while循环代表 buff里面的信息已经写完  len = -1
		fis.close();
		os.flush();
		os.close();
   }
}
