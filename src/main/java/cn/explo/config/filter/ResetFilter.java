package cn.explo.config.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


/**
 * 替换 ServletRequest,使其可以多次调用request.getBody()与修改body
 *
 */
@Configuration
public class ResetFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(ResetFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		ServletRequest req = request;
		ServletResponse rep = response;
		if (request instanceof HttpServletRequest ) {
			req = new WrapedRequest((HttpServletRequest) request);
		}
//		if (response instanceof HttpServletResponse) {
//			rep = new ActionResponseWrapper((HttpServletResponse) response);
//		}
		chain.doFilter(req, rep);
		logger.info("-----after DoFilter....");

		/**
		 * 对响应数据加密
		 */
//		byte[] originalResponseBody = ((ActionResponseWrapper)rep).getResponseData();
//        byte[] updatedBody = "ttttt".getBytes();
//        OutputStream os = response.getOutputStream();
//        os.write(updatedBody);
//        os.flush();
//        os.close();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
