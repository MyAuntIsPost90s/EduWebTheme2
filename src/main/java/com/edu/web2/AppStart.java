package com.edu.web2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.edu.web2.service.common.util.LogUtil;

@SpringBootApplication
public class AppStart extends SpringBootServletInitializer {

	public static void main(String[] args) {
		try {
			SpringApplication.run(AppStart.class, args);
		} catch (Exception e) {
			LogUtil.e(e);
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppStart.class);
	}

}
