package com.rkele.yxhshop.order;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringCloudApplication
@EnableWebMvc
@EnableSwagger2
@ComponentScan(value = "com.rkele.yxhshop")
@EnableFeignClients("com.rkele.yxhshop.*.api")
@EnableCaching
@MapperScan(basePackages = "com.rkele.yxhshop.*.mapper")
public class YxhshopOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(YxhshopOrderApplication.class, args);
	}
}
