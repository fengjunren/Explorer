package cn.explo.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import cn.explo.resolver.JsonPathArgumentResolver;

@Configuration
public class AddMethodArgResolverConfig {
	@Autowired
	private RequestMappingHandlerAdapter adapter;

	@PostConstruct
	public void injectSelfMethodArgumentResolver() {
		List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
		argumentResolvers.add(new JsonPathArgumentResolver());
//		System.out.println(adapter.getArgumentResolvers());
		argumentResolvers.addAll(adapter.getArgumentResolvers());
		adapter.setArgumentResolvers(argumentResolvers);
	}
}
