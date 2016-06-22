package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author zhouhangbo
 * @date 2016年5月11日 下午3:45:52
 * @version V1.0.0
 * @Description:
 */
public abstract class Server {

	private static int port = 8080;
	private static ServerSocket server = null;

	public final void start() {
		start(port);
	}

	public final void start(int port) {
		try {
			if (server == null) {// 不支持开多个，重新开启需要调用close
				server = new ServerSocket(port);
				this.accept();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开启的线程数量
	 * */
	private final void accept() {
		try {
			if (server != null) {
				final Socket client = server.accept();
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							BufferedReader in = new BufferedReader(
									new InputStreamReader(
											client.getInputStream()));
							PrintWriter out = new PrintWriter(
									client.getOutputStream());
							while (true) {
								String str = in.readLine();
								out.println(getAndPostData(str));
								out.flush();
							}
						} catch (IOException e) {
							System.out.println(e);
						}

					}
				}).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final void close() {
		try {
			if (server != null) {
				server.close();
				System.out.println("--------server closed--------");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param data
	 *            获得客户端的数据
	 * @return 给客户端发送数据
	 * @Description:
	 */
	public abstract String getAndPostData(String data);

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8080);
			Socket client = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream());
			while (true) {
				String str = in.readLine();
				System.out.println(str);
				out.println("has receive....");
				out.flush();
				if (str.equals("end"))
					break;
			}
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
