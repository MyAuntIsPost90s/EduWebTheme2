package com.edu.web2;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

import lingshi.gateway.filter.EncodeFilter;
import lingshi.gateway.filter.TokenCheckFilter;

@Configuration
@MapperScan(basePackages = "com.edu.**.dao")
@ImportResource(locations = { "classpath:/spring/applicationContext.xml" })
public class AppStartConfig {
	
	@Resource
	private Environment environment;

	/**
	 * 配置编码过滤器
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<EncodeFilter> encodeFilterRegistration() {
		FilterRegistrationBean<EncodeFilter> registration = new FilterRegistrationBean<EncodeFilter>();
		registration.setFilter(new EncodeFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("charset", environment.getProperty("filter.charset"));
		registration.setName("EncodeFilter");
		return registration;
	}

	/**
	 * 配置token校验过滤器
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<TokenCheckFilter> tokenCheckFilterRegistration() {
		FilterRegistrationBean<TokenCheckFilter> registration = new FilterRegistrationBean<TokenCheckFilter>();
		registration.setFilter(new TokenCheckFilter());
		registration.setName("TokenCheckFilter");
		registration.addUrlPatterns("/app/*","/sys/*","/base/*");
		registration.addInitParameter("isCross", environment.getProperty("filter.isCross"));
		registration.addInitParameter("allowPath", environment.getProperty("filter.allowPath"));
		return registration;
	}
	
	/**  
     * 文件上传配置，限制10M
     * 
     * @return  
     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        factory.setMaxFileSize("10240KB"); 
        factory.setMaxRequestSize("102400KB");  
        return factory.createMultipartConfig();  
    } 
}
