package cn.explo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.explo.config.filter.WrapedRequest;
import cn.explo.pojo.REQ;
import cn.explo.utils.MyJsonUtil;
import cn.explo.utils.PropsUtil;
import cn.explo.utils.encrypt.asymmetric.RSAAlgorithm;
import cn.explo.utils.encrypt.enums.AesCipherAlgorithmEnum;
import cn.explo.utils.encrypt.enums.AesKeySizeEnum;
import cn.explo.utils.encrypt.enums.CodecEnum;
import cn.explo.utils.encrypt.symmetric.AESAlgorithm;

/**
 * 
 *  加解密 拦截器
 */
public class DecryptInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(DecryptInterceptor.class);
	private String REQ_AES_KEY = "REQ_AES_KEY";

	/**
	 * 对请数据解密
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		boolean pass = true;
		if (request instanceof WrapedRequest) {
			WrapedRequest reqWrap = (WrapedRequest) request;
			String method = request.getMethod();
			logger.info("###统一进行解密拦截器请求执行###");
			if (!"GET".equals(method)) { // 放行 get 请求
				String body = IOUtils.toString(request.getInputStream());
				logger.info("收到的数据:" + body);
				if (body == null || "".equals(body)) {
					return pass;
				}
				REQ req = MyJsonUtil.deserialize(body, REQ.class);
				String data = req.Data;
				String key = req.Key;
				logger.info("收到加密密钥key:" + key);
				String privateKey = PropsUtil.getProperty("encrypt.rsa.private_key");
				RSAAlgorithm rsaAlgorithm = RSAAlgorithm.create().codec(CodecEnum.Base64).build();
				String decryptKey = rsaAlgorithm.decryptByPrivate(key,privateKey);
				logger.info("解密后的密钥decryptKey:" + decryptKey);
				request.setAttribute(REQ_AES_KEY, decryptKey);
				logger.info("开始解密报文体数据...");
				
				AESAlgorithm aesAlgorithm = AESAlgorithm.create().keySize(AesKeySizeEnum.size128)
		                .cipherAlgorithm(AesCipherAlgorithmEnum.ECB_PKCS5PADDING)
		                .codec(CodecEnum.Base64).build();
				
				String decryptData = aesAlgorithm.decrypt(data, decryptKey);
 
				logger.info("解密后的报文体数据:" + decryptData);
				reqWrap.setRequestBody(decryptData.getBytes(reqWrap.getCharacterEncoding()));
			}
		}
		return pass;
	}

	/**
	 * 对响应数据加密
	 */
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		// TODO Auto-generated method stub
//		logger.info("-------------afterCompletion");
//		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//		if (response instanceof HttpServletResponse) {
//			ActionResponseWrapper resp = (ActionResponseWrapper) response;
//			String resData = new String(resp.getResponseData());
//			logger.info("resData:" + resData);
//            
// 
//		}
//	}
	public static void main(String[] args) {
//		String key="gmoPyLZbXXE2BUypHcw3FzonJ8NRKGkgGTTJyVnAh/S75Nof6IP1m1388a6nG8MkwYGoPdY5PLqpUY1scGb69KkWt9Li6TYsJKpQv3wkHSD7XcEUdq0SEedfDU7/EmoxcvUei2SMMZI1rpiz8zf5O7hxbxzF2ZwKRD50as+PQfM=";
//		String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKtVJPDFj9vZs5KCJwJK/BjRGgOi1NHQh4kKKzKplA7Z38Lszbt18PIfwU93EeHtk05/wkmIRbpM1mfDj/QAWjehGY8VXP2ciXGiMugdp1C24JfOuqRohyDPxkr71E2DmH5WaOrxnkKH9IK3OKEiSyZI6fv+ck55PNdlSABW9hyrAgMBAAECgYB/LWGRU6TYdb3E59Wa7xw4M6bSwiCladvhjmnIXnYnm/Rr1alVGFP09jthCCp/AQHSSCVovb28b2LLfS+y2DweScq6+ioy4/m6jQJVLJqTsjN05LOLJnda0M60xXCsBr69XdV7ca7buBMaPNhEK2YZPLk6BTW0xWKS7tspIO8vSQJBAPcVGADq0qjO0DKtaLBDva2ADXvT8oG2LP9nPUSIy0wMQgmR2/qDep+WG0eLPnA8xumdBtI+mx9MZimmEy/BK88CQQCxhCntqUEZwHrI4K2sccFEmsi5e+1G+TFHJsZAd7Xaz0sx6x4FJfuAKIwIAAVbrCQnyP0TEfakIyN75X7lE+xlAkBh2I+d5WAxC111hCh+DQVzTM+KJv/VEDC3n8mqd0WSwxPvuDxV83U+93GsHl9GkTrwTofiAHrrpRScD7Fvat8PAkBk2zVDPV2qduyMAvNJvZvUEaknkVx/bYds6H/NEva0qgAMDYyR2kGpT+A3j2N78TLdk9kNo7AhE4HOm9Zb6tTtAkBsk6Bx5AHfxXP3U/i9rZmY9eES/u8o92MK/0wuhkQyovCEwLBZFE7wCRDO/KYQsFN9WR1iBgN8PC+LM9IbuGQi";
//		RSAAlgorithm rsaAlgorithm = RSAAlgorithm.create().codec(CodecEnum.Base64).build();
//		String decryptKey = rsaAlgorithm.decryptByPrivate(key,privateKey);
//		logger.info("解密后的密钥decryptKey:" + decryptKey);
	}
	
}
