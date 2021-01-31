package timeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


@WebServlet("/TimeList")
public class TimeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();
		
		try {
			jsonObject = (JSONObject) parser.parse(getrequestBody(request));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("변환에 실패해씁니다.");
		}
		
		String url = (String) jsonObject.get("URL");
		String list = getList(url);
		
		
		try {
			jsonObject = (JSONObject) parser.parse( list );
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("변환에 실패해씁니다.");
		}
		
		String result = (String) jsonObject.get("list").toString();
		
		response.getWriter().print( result );
		
		
	}
	
	public static String getList(String URL) {
		try {
		    URL infoURL = new URL(URL);
		    InputStream inputStream = infoURL.openStream();
		    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");

		    StringBuffer sb = new StringBuffer();
		    int readByte;
		    while ((readByte = inputStreamReader.read()) != -1) {
		        sb.append((char) readByte);
		    }
		    inputStreamReader.close();
		    inputStream.close();
		    
		    return sb.toString();
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return "";
		
		
	}
	
	public static String getrequestBody(HttpServletRequest request) throws IOException {

		String reqStr = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		reqStr = stringBuilder.toString();
		return reqStr;
	}

}
