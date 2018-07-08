package cn.jd.web.utils;


import cn.jd.pojo.AccessToken;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TokenThread implements Runnable{
	 private static Logger log = LogManager.getLogger(TokenThread.class);
	    // 第三方用户唯一凭证  
	    private  String appid = "wx7bcaae66aff49a45";
	    // 第三方用户唯一凭证密钥  
	    private  String appsecret = "983abd7888e223c73bf6cb0aee30685e";
		public static AccessToken accessToken = null;
	  
	    public void run() {  
	        while (true) {  
	            try {
	            	try {
	            		accessToken = SignUtil.getAccessToken(appid, appsecret);  
					} catch (Throwable e) {
						log.error("",e);
					}
	                log.info("accessToken 的值为：" +  accessToken);
	                
	                if (null != accessToken) {  
	                    // 休眠7000秒  
	                    Thread.sleep((accessToken.getExpires_in() - 200) * 1000);  
	                } else {  
	                    // 如果access_token为null，60秒后再获取  
	                    Thread.sleep(60 * 1000);  
	                }  
	            } catch (InterruptedException e) {  
	                try {  
	                    Thread.sleep(60 * 1000);  
	                } catch (InterruptedException e1) {  
	                    log.error("{}", e1);  
	                }  
	                log.error("{}", e);  
	            } catch (Throwable e) {
					e.printStackTrace();
				}  
	        }  
	    }  
	    
	    public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getAppsecret() {
			return appsecret;
		}

		public void setAppsecret(String appsecret) {
			this.appsecret = appsecret;
		}
		public static void main(String[] args) {
			log.info("nihao ");
			log.warn("warn ");
			log.error("error ");
		}
}
