package timeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

@WebServlet("/TimeUpdate")
public class TimeUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		
		try {
			jsonObject = (JSONObject) parser.parse(getrequestBody(request));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("변환에 실패해씁니다.");
		}
		
		String tid = (String) jsonObject.get("tid");
		String comment = (String) jsonObject.get("comment");
		System.out.println( comment );
		
		String url = "https://barosa.me:6611/timeline/"+tid; 	//URL
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("comment", comment );	//PARAM
		
		
		
		
		String resp = postRequest(url, param);
		
		response.getWriter().print( resp );
		
		
	}
	
	public static String postRequest(String pURL, HashMap < String, String > pList) {

        String myResult = "";

        try {
            //   URL 설정하고 접속하기 
            URL url = new URL(pURL); // URL 설정 

            HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속 
            //-------------------------- 
            //   전송 모드 설정 - 기본적인 설정 
            //-------------------------- 
            //http.setDefaultUseCaches(false);
            //http.setDoInput(true); // 서버에서 읽기 모드 지정 
            http.setDoOutput(true); // 서버로 쓰기 모드 지정  
            http.setRequestMethod("PUT"); // 전송 방식은 PUT



            //--------------------------
            // 헤더 세팅
            //--------------------------
            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다 
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


            //-------------------------- 
            //   서버로 값 전송 
            //-------------------------- 
            StringBuffer buffer = new StringBuffer();

            //HashMap으로 전달받은 파라미터가 null이 아닌경우 버퍼에 넣어준다
			
            
            if (pList != null) {

                Set key = pList.keySet();

                for (Iterator iterator = key.iterator(); iterator.hasNext();) {
                    String keyName = (String) iterator.next();
                    String valueName = pList.get(keyName);
                    buffer.append(keyName).append("=").append(valueName);
                }
            }
            
            
            System.out.println( buffer.toString() );
            

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();


            //--------------------------
            //   Response Code
            //--------------------------
            //http.getResponseCode();

            System.out.println( "http reponsecode : "+ http.getResponseCode()); 

            //-------------------------- 
            //   서버에서 전송받기 
            //-------------------------- 
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str + "\n");
            }
            myResult = builder.toString();
            return myResult;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResult;
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
