package cn.explo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import cn.explo.interceptor.CorsInterceptor;
import cn.explo.interceptor.DecryptInterceptor;
import cn.explo.interceptor.WxInterceptor;
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");// 跨域配置
		registry.addInterceptor(new WxInterceptor()).addPathPatterns("/msgReply");// 过滤 响应微信 回调验证接口 get方法
		registry.addInterceptor(new DecryptInterceptor()).excludePathPatterns("/msgReply","/setMenu").addPathPatterns("/**");// 拦截数据  加解密 验签
	}
 
//    @Override
//    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//    	// TODO Auto-generated method stub
//    	super.configureMessageConverters(converters);
//    	converters.add(new MyConverter());
//    }

}
