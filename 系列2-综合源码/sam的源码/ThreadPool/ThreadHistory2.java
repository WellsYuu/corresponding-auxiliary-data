package ThreadPool;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadHistory2 {
	public static void handleRequest(Socket socket) {
		try {
			InputStream is = socket.getInputStream();
			byte[] buff = new byte[1024];
			int len = is.read(buff);
			if (len > 0) {
				// 将读取出来的字节信息 转化成明文信息
				String msg = new String(buff, 0, len);
				System.out.println("客户端的请求信息：=======" + msg + "========");
				// 解析出来uri

				System.out.println("msg：=======" + msg + "========");

			} else {
				System.out.println("bad Request!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		ServerSocket server;
		try {
			server = new ServerSocket(8080);
			while (true) {
				final Socket client = server.accept();
				Runnable task = new Runnable() {
					@Override
					public void run() {
						// 业务
						handleRequest(client);

					}
				};
				new Thread(task).start();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
