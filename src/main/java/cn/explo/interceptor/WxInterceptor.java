package cn.explo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.explo.utils.CommonUtil;
/**
 * 
 * 
  *   过滤微信服务器回调进行验证的get请求
 */
public class WxInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(WxInterceptor.class);
	private String msg = "";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		boolean pass = true;
		logger.info("###微信服务器回调拦截器请求执行###");
		String method = request.getMethod();
		if ("GET".equalsIgnoreCase(method)) {
			pass = false;
			String echostr = request.getParameter("echostr");
			CommonUtil.write(response, echostr+"");
		}
		if (pass){
			logger.info("GET 请求 放行....");
		}
		return pass;
	}

}
