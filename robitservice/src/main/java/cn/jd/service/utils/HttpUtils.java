package cn.jd.service.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijie32 on 2018/7/28.
 */
public class HttpUtils {

    /*post  请求*/
    public static Map<String, Object> requestPost(String url, String content) {
        Map<String, Object> result = new HashMap<String, Object>();
        String errorStr = "";
        String status = "";
        String response = "";
        OutputStreamWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
            // 设置请求属性
            /*httpUrlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");*/
            httpUrlConnection.setRequestProperty("x-adviewrtb-version", "2.1");
           httpUrlConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=UTF-8");

            // 发送POST请求必须设置如下两行
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 获取URLConnection对象对应的输出流
            /*out = new PrintWriter(httpUrlConnection.getOutputStream());*/
            // 发送请求参数
            out.write(content);
            // flush输出流的缓冲
            out.flush();
            httpUrlConnection.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
               /* response += new String(line.getBytes(), Charset.forName("utf-8"));*/
                response +=line;
            }
            status = new Integer(httpUrlConnection.getResponseCode()).toString();
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            errorStr = e.getMessage();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) { out.close();}
                if (in != null) {in.close();}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result.put("errorStr", errorStr);
        result.put("response", response);
        result.put("status", status);
        return result;

    }
    public static byte[] gbk2utf8(String chenese){
        char c[] = chenese.toCharArray();
        byte [] fullByte =new byte[3*c.length];
        for(int i=0; i<c.length; i++){
            int m = (int)c[i];
            String word = Integer.toBinaryString(m);
//         System.out.println(word);

            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            //补零
            for(int j=0; j<len; j++){
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");

//         System.out.println(sb.toString());

            String s1 = sb.substring(0,8);
            String s2 = sb.substring(8,16);
            String s3 = sb.substring(16);

            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i*3] = bf[0];
            bf[1] = b1;
            fullByte[i*3+1] = bf[1];
            bf[2] = b2;
            fullByte[i*3+2] = bf[2];

        }
        return fullByte;
    }

}
