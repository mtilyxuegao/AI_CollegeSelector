
public class HttpResponse {

    public HttpResponse() {
        
    }
    
    private int code = 200;
    public void setStatusCode(int code) {
    	this.code = code;
    }
    
    public void setHeader(String key, String value) {
    	
    }

    private String data;
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 返回数据
     * @param
     */
    public byte[] getBytes() {
        // 最终返回的数据： 响应行+响应头+空行+响应正文
        return ("HTTP/1.1 " + Integer.toString(code) + "\r\n" +
                "Content-Type: text/plain\r\n" +
                "\r\n" + data).getBytes();
    }
}
