package cn.jd.service.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
    private static Logger logger = LogManager.getLogger(HttpUtils.class);
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


    /**
     * get请求
     * @param requestUrl 请求地址
     * @return
     */
    public static String requestGet(String requestUrl) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (Exception e) {
            logger.info("get请求发生错误 ： " + e.getMessage());
            throw new RuntimeException("暂不支持该地区，请重新输入");
        }
        logger.info("get请求正常返回 返回结果 ： " + buffer.toString());
        return buffer.toString();
    }

}
