package cn.explo.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import cn.explo.pojo.REP_MESSAGE;
import cn.explo.utils.MyJsonUtil;
import cn.explo.utils.encrypt.enums.AesCipherAlgorithmEnum;
import cn.explo.utils.encrypt.enums.AesKeySizeEnum;
import cn.explo.utils.encrypt.enums.CodecEnum;
import cn.explo.utils.encrypt.symmetric.AESAlgorithm;

@ControllerAdvice
//(可以统一处理返回给客户端的数据)
public class EncryptRespBodyAdvice implements ResponseBodyAdvice<Object> {
	private Logger logger = LoggerFactory.getLogger(EncryptRespBodyAdvice.class);
	private String REQ_AES_KEY = "REQ_AES_KEY";

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		// TODO Auto-generated method stub
		logger.info("<<<<  beforeBodyWrite  >>>");
		logger.info("body:" + body);
		AESAlgorithm aesAlgorithm = AESAlgorithm.create().keySize(AesKeySizeEnum.size128)
				.cipherAlgorithm(AesCipherAlgorithmEnum.ECB_PKCS5PADDING).codec(CodecEnum.Base64).build();

		boolean isREP_MESSAGE = (body instanceof REP_MESSAGE);
		logger.info("isREP_MESSAGE:", isREP_MESSAGE);
		Object encryptBody = body;
		if (isREP_MESSAGE) {
			try {
				String serialBody = MyJsonUtil.serialize(body);
				logger.info("serial body:", serialBody);
				ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest) request;
				String decryptKey = (String) servletServerRequest.getServletRequest().getAttribute(REQ_AES_KEY);
				encryptBody = aesAlgorithm.encrypt(serialBody, decryptKey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return encryptBody;
	}

}
