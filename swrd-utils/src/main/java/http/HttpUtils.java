package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtils {
	
	public static String get(String url, Map<String, String> param, String charset){
		String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if(param != null){
            	StringBuilder sb = new StringBuilder();
            	for(Map.Entry<String, String> e : param.entrySet()){
            		sb.append(e.getKey() + "=" + e.getValue()).append("&");
            	}
            	urlNameString += sb.substring(0, sb.length()-1);
            }
			System.out.println("urlNameString:"+urlNameString);
			String req = URLEncoder.encode(urlNameString);
			URL realUrl = new URL(req);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if(charset != null){
            	connection.setRequestProperty("Accept-Charset", charset);
            }
            connection.connect();
            if(charset != null){
            	in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
            }else{
            	in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
	}
	public static String post(String url, String param, String charset){
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Authorization", "JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyNzIsInVzZXJuYW1lIjoiemhhbmd5dW5nYW5nIiwiZXhwIjoxNTg4MTI5NjcxLCJlbWFpbCI6InpoYW5neXVuZ2FuZ0BzZWNvby5jb20iLCJvcmlnX2lhdCI6MTU4NzUyNDg3MX0.yWm-r-Tg1WWfJ4mBszYg0a2Hl6XmSGAHNmF2CXpwoDY");
			connection.setRequestProperty("Content-Type", "application/json");

			if(charset != null){
				connection.setRequestProperty("Accept-Charset", charset);
			}
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(param);
			out.flush();
			out.close();
			
			if(charset != null){
				in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
			}else{
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
