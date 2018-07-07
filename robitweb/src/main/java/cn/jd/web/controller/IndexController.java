package cn.jd.web.controller;

import com.jd.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/07/07.
 * 初始化的controller
 */
@Controller

public class IndexController {
    private TestService testService;
    @RequestMapping("index")
    public String showIndex(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        System.out.println("requestURI--》" + requestURI);
        System.out.println("你好");
        return "index";
    }
}
