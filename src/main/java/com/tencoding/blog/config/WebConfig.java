package com.tencoding.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Value("${file.path}")
	private String uploadFolder;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	file:///C:/springImageDir/tecoblog/upload/22cd9a47-f170-4fcc-b997-3cfc948bc7b0_Ryan Gosling.png
		WebMvcConfigurer.super.addResourceHandlers(registry);
		System.out.println("=====================================");
		System.out.println("file:///" + uploadFolder);
		System.out.println("=====================================");
		
		registry.addResourceHandler("/upload/**") // URL 패턴 /upload/ -> 낚아챔
		.addResourceLocations("file:///" + uploadFolder) // 실제 물리적인 경로
		.setCachePeriod(60 * 10 * 6) // 캐시의 지속시간 설정(초)
		.resourceChain(true) // 리소스 찾는것을 최적화하기 위해서
		.addResolver(new PathResourceResolver());
	}
	
	@Bean // IoC에 등록된다
	public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {

		FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new XssEscapeServletFilter());
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/*");

		return filterRegistrationBean;
	}

	
}
