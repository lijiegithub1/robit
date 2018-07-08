package cn.jd.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 所有处理的接口
 */

public interface ProcessRequest {

	String process(HttpServletRequest req);
}
