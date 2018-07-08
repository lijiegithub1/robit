package cn.jd.web.servlet;

import cn.jd.pojo.AccessToken;
import cn.jd.pojo.RequestParameter;
import cn.jd.service.ProcessRequest;
import cn.jd.web.utils.SignUtil;
import cn.jd.web.utils.TokenThread;
import com.alibaba.dubbo.common.json.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * servlet
 * @author Administrator
 *
 */
public class CoreServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(CoreServlet.class);
	private static final long serialVersionUID = 1L;
	private ProcessRequest processRequest = null;
	public void init() throws ServletException {
		logger.info("初始化了......");
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		processRequest = (ProcessRequest) wac.getBean("processRequestImpl");
	};
	//doGet是验证微信是否正确
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接受前端传过来的参数 
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		RequestParameter requestParameter = new RequestParameter(signature,timestamp,nonce,echostr);
		logger.info("验证信息 为  requestParameter = " + requestParameter.toString());
		//进行排序 
		//进行算法，最后做比较 
		//最后进行输出 
		PrintWriter out = resp.getWriter();
		if(SignUtil.checkSingUtil(signature, timestamp, nonce)){
			out.print(echostr); 
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*String accessToken = TokenThread.accessToken.getAccess_token();*/
		// 第三方用户唯一凭证
		  String appid = "wx7bcaae66aff49a45";
		// 第三方用户唯一凭证密钥
		  String appsecret = "983abd7888e223c73bf6cb0aee30685e";
		AccessToken accessToken1 = SignUtil.getAccessToken(appid, appsecret);
		String access_token = null;
		if(null != accessToken1){
			access_token = accessToken1.getAccess_token();
		}
		/*logger.info(" 进到了post请求accessToken 为 ==" + accessToken);*/
		logger.info(" 进到了post请求 ==========" );
		//进行编码 设置
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		//直接调用service层进行处理结果 ，返回xml类型
		String respMessage = processRequest.process(req);
		if(StringUtils.isBlank(respMessage)){
			respMessage = "False";
		}
		//响应 
		PrintWriter out = resp.getWriter();  
		logger.info("respMessage "+ respMessage);
		//给微信进行相应 
		out.print(respMessage);  
        out.close();
	}
}
