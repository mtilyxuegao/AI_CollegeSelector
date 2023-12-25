
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    
    private String pathInfo; // ����·��

    private String method; // ����ʽ

    private Map<String, String> header; // ����ͷ

    public HttpRequest(Reader inReader) {
    	//System.out.println("===����HttpRequest===");
        try {
            BufferedReader reader = new BufferedReader(inReader);
        	//System.out.println("===����BufferedReader===");
            // ��һ�У�������
            String firstLine = reader.readLine();
        	//System.out.println("===readLine===");
            String[] firstLineItems = firstLine.split(" ");
            // ����ʽ
            method = firstLineItems[0];
            // ����·��
            pathInfo = firstLineItems[1];
            
            // ��ȡ���������У�����ͷ
            String headerLine;
            header = new HashMap<>();
            while ((headerLine = reader.readLine()) != null) {
                if (headerLine.length() == 0) {
                    break;
                }
                String[] headerLineItems = headerLine.split(": ");
                header.put(headerLineItems[0], headerLineItems[1]);
            }
            
            // �����壬Ŀǰò�ƶ�ȡ����
            /*StringBuilder body_builder = new StringBuilder();
			char[] buffer = new char[1024];
			int length;
			while ((length = reader.read(buffer)) != 0) {
			    body_builder.append(buffer, 0, length);
			}
			String body = body_builder.toString();
			System.out.println("�����壺\n" + body);*/
        } catch (IOException e) {
        	System.err.println("�������ݳ���");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("����");
        }
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public String getMethod() {
        return method;
    }

    public String getHeader(String name) {
        return header.get(name);
    }
}
