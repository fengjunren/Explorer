package cn.explo.advice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
/**
 * 
 * 统一处理客户端的请求数据
 *
 */
//@ControllerAdvice
public class RemakeReqBodyAdvice extends RequestBodyAdviceAdapter {
	private Logger logger = LoggerFactory.getLogger(RemakeReqBodyAdvice.class);
	
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		logger.info("************************RemakeRequestBodyAdvice--supports***");
		return true;
	}
	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		// TODO Auto-generated method stub
		logger.info("************************RemakeRequestBodyAdvice--HttpInputMessage****");
		try {
//			return new RemakeHttpInputMessage(inputMessage);
		} catch (Exception e) {
			logger.error("请求数据格式不对!--->"+e.getMessage());
		}
		return inputMessage;
	}
	
	class RemakeHttpInputMessage implements HttpInputMessage {
		private Logger logger = LoggerFactory.getLogger(RemakeReqBodyAdvice.class);
	    private HttpHeaders headers;
	    private InputStream body;

	    public RemakeHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
//	        this.headers = inputMessage.getHeaders();
//	        String content = IOUtils.toString(inputMessage.getBody());
//            logger.info("content:"+content);
//            REQ_MESSAGE req_MESSAGE = MyJsonUtil.deserialize(content, REQ_MESSAGE.class);
//            logger.info("req_MESSAGE:"+req_MESSAGE.toString());
//            Map<String, Object> req_head = req_MESSAGE.getREQ_HEAD();
//            Map<String, Object> req_body = req_MESSAGE.getREQ_BODY();
//            req_body.put("REQ_HEAD", req_head);
//            String remakedContent = MyJsonUtil.serialize(req_body);
//            logger.info("remakedContent:"+remakedContent);
//             
//	        this.body = IOUtils.toInputStream(remakedContent);
	    }

	    @Override
	    public InputStream getBody() throws IOException {
	        return body;
	    }

	    @Override
	    public HttpHeaders getHeaders() {
	        return headers;
	    }
	}

	
}
