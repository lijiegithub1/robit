package cn.jd.web.utils;


import cn.jd.pojo.AccessToken;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 工具类	
 * @author Administrator
 *
 */
public class SignUtil {
	private static Logger logger = LogManager.getLogger(SignUtil.class);
	//token
	@Value("${token}")
	private static String token = "robot";
	//private static String APPSECRET = "dd4ac98494a56da5b9f20f82c9858857";
	//private static String APPSECRET = "c3f04605748a6aaaf5a9e47c2e3e52d1";
	//private static String appid = "wx5c0d68cf1b6a109e";
	//private static String appid = "wx8dc5ac202f3bc20c";
	/**
	 * 把json字符串转成json对象 
	 */
	
	/**
	 * 获得access_token的方法 
	 * 1 get请求的地址 ：https://api.weixin.qq.com/cgi-bin/token?
	 * 			grant_type=client_credential&appid=APPID&secret=APPSECRET
	 * 2  参数
	 * grant_type	是	获取access_token填写client_credential
		appid	是	第三方用户唯一凭证
	    secret	是	第三方用户唯一凭证密钥，即appsecret
	 * @param appsecret 
	 * @param appid 
	 * @throws IOException 
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) throws IOException{
		String urlStr = "https://api.weixin.qq.com/cgi-bin/token?"
				+ "grant_type=client_credential&appid="+appid+"&secret="+appsecret;
		logger.info("urlStr-------->" + urlStr);
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		//获得结果的数据
		InputStream in = conn.getInputStream();
		//把数据用字符串进行接受 
		BufferedReader bufferReaeder = new BufferedReader(new InputStreamReader(in));
		StringBuffer buffer = new StringBuffer();
        String temp = null;
        while ((temp = bufferReaeder.readLine()) != null) {
            buffer.append(temp);
        }
       JSONObject jSONObject = JSON.parseObject(buffer.toString());
       String accessToken = "";
       AccessToken token = null;
       if(null != jSONObject){
    	    accessToken = (String)jSONObject.get("access_token");
    	    Integer    errcode = (Integer)jSONObject.get("errcode");
    	    Integer    expires_in = (Integer)jSONObject.get("expires_in");
    	    logger.info("accessToken==>" + accessToken);
    	     token = new AccessToken(accessToken,expires_in);
       }
		return token;
	}
	
	
	/**
	 * 检查标志是否正确
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSingUtil(String signature, String timestamp, String nonce) {
		//1先进行排序 
		String[] str = new String[]{token, timestamp, nonce };
		Arrays.sort(str);
		MessageDigest md = null;
		String tmpStr = null;  
		StringBuilder content = new StringBuilder();
		for (String string : str) {
			content.append(string);
		}
		try {
			//2 在进行字符串的拼接
			md = MessageDigest.getInstance("SHA-1");
			//3、 
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
			logger.info("tmpStr-->" + tmpStr +"signature-->" + signature);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false; 
	}
	
	
	  /** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param byteArray 
     * @return 
     */  
    private static String byteToStr(byte[] byteArray) {  
        String strDigest = "";  
        for (int i = 0; i < byteArray.length; i++) {  
            strDigest += byteToHexStr(byteArray[i]);  
        }  
        return strDigest;  
    } 
	/** 
     * 将字节转换为十六进制字符串 
     *  
     * @param mByte 
     * @return 
     */  
    private static String byteToHexStr(byte mByte) {  
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  
        String s = new String(tempArr);  
        return s;  
    }  
}
