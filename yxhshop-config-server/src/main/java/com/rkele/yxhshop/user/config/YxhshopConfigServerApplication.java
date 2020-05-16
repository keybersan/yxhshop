package com.rkele.yxhshop.user.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class YxhshopConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(YxhshopConfigServerApplication.class, args);
	}

}

