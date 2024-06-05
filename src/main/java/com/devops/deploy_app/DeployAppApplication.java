package com.devops.deploy_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ConfigurationPropertiesScan("com.devops.deploy_app")
@ComponentScan(basePackages = "com.devops.deploy_app")
public class DeployAppApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DeployAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DeployAppApplication.class, args);
	}

}
