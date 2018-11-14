package cn.explo.resolver;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.ReadContext;

import cn.explo.exception.GlobalException;
import cn.explo.utils.MyJsonUtil;
/**
 * 自定义参数解析器
 *
 */
public class JsonPathArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";
	private static final Logger logger = LoggerFactory.getLogger(JsonPathArgumentResolver.class);
	
	// 处理多线程 变量共享
	private static ThreadLocal<ReadContext> localCtx = new ThreadLocal<>();
	private static ThreadLocal<String> localJsonStr = new ThreadLocal<>();
    
    //判断是否支持要转换的参数类型
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
    	logger.info("JsonPathArgumentResolver --- check-support-Parameter---");
        return parameter.hasParameterAnnotation(JsonParam.class);
    }

    //当支持后进行相应的转换
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String body = getRequestBody(webRequest);
        logger.info("JsonPathArgumentResolver --- body---:"+body);
         String jsonStrCache = localJsonStr.get();
         ReadContext  jsonCtxCache = localCtx.get();
        if (jsonCtxCache == null || jsonStrCache == null || "".equals(jsonStrCache)||(body != null && !jsonStrCache.equals(body))){
        	jsonStrCache = body;
        	jsonCtxCache = JsonPath.parse(jsonStrCache);
        }
        Object val = null;
        logger.info("jsonStrCache ---:"+jsonStrCache);
        try {
        	String path = parameter.getParameterAnnotation(JsonParam.class).value();
        	final Type type = parameter.getGenericParameterType();
        	 
        	Object fromValue = jsonCtxCache.read(path);
        	val=MyJsonUtil.convert(fromValue, TypeFactory.rawClass(type));
        	 
            if (parameter.getParameterAnnotation(JsonParam.class).required() && val == null) {
               throw new GlobalException("999999", "数据格式错误!("+parameter.getParameterAnnotation(JsonParam.class).value() +")");
            }
        } catch (PathNotFoundException exception) {
            System.out.println(exception.getStackTrace());
            if (parameter.getParameterAnnotation(JsonParam.class).required()) {
            	throw new GlobalException("999999", "数据格式错误!("+parameter.getParameterAnnotation(JsonParam.class).value() +")");
            }
        }
        localCtx.set(jsonCtxCache);
        localJsonStr.set(jsonStrCache);
        return val;
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = (String) servletRequest.getAttribute(JSON_REQUEST_BODY);
        if (jsonBody == null) {
            try {
                jsonBody = IOUtils.toString(servletRequest.getInputStream());
                servletRequest.setAttribute(JSON_REQUEST_BODY, jsonBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonBody;

    }

}
