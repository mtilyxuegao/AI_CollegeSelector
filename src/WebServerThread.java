import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class WebServerThread extends Thread {
	private static final int port = 80;
	private ServerSocket mServerSocket;
	private boolean isLooping = true;
	public WebServerThread() {
		
	}
	
	@Override
	public void run() {
		try {
			System.out.println("===server start===");
		    mServerSocket = new ServerSocket(port);
		    //mServerSocket.setReuseAddress(true);
		    /**
		      * 开始接受客户端请求。
		      */
		    while (isLooping && !mServerSocket.isClosed()) {
		        // 接收客户端套接字。
	        	try {
		        	// 阻塞接受客户端。
		            Socket socket = mServerSocket.accept();
		            System.out.println("===server accept===" + socket.getInetAddress().toString());
			        new ServerThread(socket).start();
	            	
	        	} catch (SocketException e) {
	        		break;
	        	}
		    }
		    if (!mServerSocket.isClosed()) {
    			mServerSocket.close();
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		isLooping = false;
		try {
			if (!mServerSocket.isClosed()) {
    			mServerSocket.close();
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ServerThread extends Thread {
		private Socket mSocket;
		public ServerThread(Socket socket) {
			mSocket = socket;
		}
		@Override
		public void run() {
			try {
				// 解析请求信息为HttpRequest对象
	            HttpRequest request = new HttpRequest(new InputStreamReader(mSocket.getInputStream(), "utf-8"));
	            String path = request.getPathInfo();
	            System.out.println("路径: " + path);
	            // 响应
	            HttpResponse response = new HttpResponse();
	            response.setData(BusinessCalculator.calculate(path));
	            // 返回数据
	            OutputStream outputStream = mSocket.getOutputStream();
	            try {
	            	outputStream.write(response.getBytes());
	            	outputStream.flush();
	            } catch (IOException e) {
	            	System.out.println("发送响应失败");
	            } finally {
	            	outputStream.close();
	            }
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
	            try {
					mSocket.close();
					System.out.println("===socket close===");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
