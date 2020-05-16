package com.rkele.yxhshop.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class YxhshopEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(YxhshopEurekaServerApplication.class, args);
	}

}
