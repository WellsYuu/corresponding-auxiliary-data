package com.gupaoedu.crawler.support;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 从网络下载文件并保存到本地。
 * @author Tom
 *
 */
public class DownLoader {
	public static void main(String[] args) {
	    download("","http://www.baidu.com/img/baidu_sylogo1.gif", "G:/dowloads/image/baidu_sylogo1.gif");
	}
	/**
	   * 下载文件到本地
	   *
	   * @param urlStr 被下载的文件地址
	   * @param fileName 本地文件名
	   * @throws Exception 各种异常
	   */
	public static void download(String baseUrl,String urlStr, String fileName){
		InputStream is = null;
		OutputStream os = null;
		try{
		    // 构造URL
		    URL url = new URL(urlStr);
		    // 打开连接
		    URLConnection con = url.openConnection();
		    // 输入流
		    is = con.getInputStream();
		    // 1K的数据缓冲
		    byte[] bs = new byte[1024];
		    // 读取到的数据长度
		    int len;
		    // 输出的文件流
		    os = new FileOutputStream(fileName);
		    // 开始读取
		    while ((len = is.read(bs)) != -1) {
		      os.write(bs, 0, len);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    // 完毕，关闭所有链接
			try{
				if(null != os){
					os.close();
				}
				if(null != is){
					is.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	    }
	}
}