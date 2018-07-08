package cn.jd.web.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;

/**
 * 这个类处理http get post请求的方法
 * @author Administrator
 *
 */
public class HttpUtils {
	private static Logger log = LogManager.getLogger(HttpUtils.class);
	/**
	 * 封装的可以post/get请求的工具类  且对应https 需要一个验证器  ，这个验证器我会写 ，其实就是放到请求中
	 * @param requestUrl 请求的url
	 * @param requestMethod get/post
	 * @param requestPamas post请求携带的参数
	 * @return
	 * @throws IOException 
	 */
	public static JSONObject httpRequest (String requestUrl , String requestMethod, String requestPamas) throws IOException{
		log.info("请求的参数为： requestUrl = " +requestUrl+ " requestMethod = " + requestMethod + " requestPamas = "+ requestPamas);
		JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();  
		//获取url地址 
		URL url = new URL(requestUrl);
		HttpsURLConnection  conn = (HttpsURLConnection )url.openConnection();
		//设置请求的参数 
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false); 
		
		  // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
		SSLSocketFactory ssf = null;
        try {
        	TrustManager[] tm = { new MyX509TrustManager() };  
        	SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
			sslContext.init(null, tm, new java.security.SecureRandom());
			 ssf = sslContext.getSocketFactory(); 
		} catch (Exception e) {
			log.error("验证失败");
			e.printStackTrace();
		}  
        // 从上述SSLContext对象中得到SSLSocketFactory对象  
		//安全
		conn.setSSLSocketFactory(ssf);
		//请求的方式
		conn.setRequestMethod(requestMethod);
		if("GET".equals(requestMethod)){
			conn.connect();
		}
		//post进行添加参数
		//1、 如果有参数需要获得out进行输出 
		if(StringUtils.isNotBlank(requestPamas)){
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(requestPamas.getBytes("UTF-8"));
			outputStream.flush();
		}
		//2 获得输入 inputStream
		InputStream in = conn.getInputStream();
		BufferedReader  br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		String str = null;
		while ((str = br.readLine())!= null) {
			buffer.append(str);
		}
		//最后将其转化为jsonObject对象 
		 jsonObject = JSONObject.fromObject(buffer.toString());
		 if(null != jsonObject ){
			 log.info("最终得到的结果是 ：jsonObject = " + jsonObject);
		 }
		return jsonObject;
	}
	
	
	
	
	
}
