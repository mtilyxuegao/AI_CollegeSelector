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
		      * ��ʼ���ܿͻ�������
		      */
		    while (isLooping && !mServerSocket.isClosed()) {
		        // ���տͻ����׽��֡�
	        	try {
		        	// �������ܿͻ��ˡ�
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
				// ����������ϢΪHttpRequest����
	            HttpRequest request = new HttpRequest(new InputStreamReader(mSocket.getInputStream(), "utf-8"));
	            String path = request.getPathInfo();
	            System.out.println("·��: " + path);
	            // ��Ӧ
	            HttpResponse response = new HttpResponse();
	            response.setData(BusinessCalculator.calculate(path));
	            // ��������
	            OutputStream outputStream = mSocket.getOutputStream();
	            try {
	            	outputStream.write(response.getBytes());
	            	outputStream.flush();
	            } catch (IOException e) {
	            	System.out.println("������Ӧʧ��");
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
