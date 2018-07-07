package cn.jd.web.servlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/07/07.
 */
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LogManager.getLogger(InitServlet.class);
    private static ServletContext application ;
    public void init() throws ServletException {
        //在初始化的时候去servletcontent
        application =this.getServletContext();
        // 获取web.xml中配置的参数
        String  appid  = getInitParameter("appid");
        String  appsecret = getInitParameter("appsecret");

        if (StringUtils.isBlank(appid) || StringUtils.isBlank(appsecret)) {
            logger.warn("appid and appsecret is not blank. appid="+appid + ", appsecret="+appsecret);
            return;
        }
/*
        TokenThread tokenThread = new TokenThread();
        tokenThread.setAppid(appid);
        tokenThread.setAppsecret(appsecret);
        new Thread(tokenThread).start();*/
    }
    public static ServletContext getApplication() {
        return application;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getMethod());
        super.doGet(req, resp);
    }
}