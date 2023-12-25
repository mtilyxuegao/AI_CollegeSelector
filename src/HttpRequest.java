
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    
    private String pathInfo; // 请求路径

    private String method; // 请求方式

    private Map<String, String> header; // 请求头

    public HttpRequest(Reader inReader) {
    	//System.out.println("===构造HttpRequest===");
        try {
            BufferedReader reader = new BufferedReader(inReader);
        	//System.out.println("===构造BufferedReader===");
            // 第一行：请求行
            String firstLine = reader.readLine();
        	//System.out.println("===readLine===");
            String[] firstLineItems = firstLine.split(" ");
            // 请求方式
            method = firstLineItems[0];
            // 请求路径
            pathInfo = firstLineItems[1];
            
            // 读取接下来的行：请求头
            String headerLine;
            header = new HashMap<>();
            while ((headerLine = reader.readLine()) != null) {
                if (headerLine.length() == 0) {
                    break;
                }
                String[] headerLineItems = headerLine.split(": ");
                header.put(headerLineItems[0], headerLineItems[1]);
            }
            
            // 请求体，目前貌似读取不了
            /*StringBuilder body_builder = new StringBuilder();
			char[] buffer = new char[1024];
			int length;
			while ((length = reader.read(buffer)) != 0) {
			    body_builder.append(buffer, 0, length);
			}
			String body = body_builder.toString();
			System.out.println("请求体：\n" + body);*/
        } catch (IOException e) {
        	System.err.println("接收数据出错");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("错误");
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
