package com.rkele.yxhshop.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

// @EnableZuulProxy
//@EnableWebMvc
//@EnableSwagger2
@ComponentScan("com.rkele.yxhshop")
@SpringCloudApplication
public class YxhshopApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxhshopApiGatewayApplication.class, args);
    }


}

