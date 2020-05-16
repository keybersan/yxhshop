package com.rkele.yxhshop.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableConfigurationProperties(YxhshopProperties.class)
@Configuration
public class YshshopAutoConfiguration {

	private final YxhshopProperties properties;

	public YshshopAutoConfiguration(YxhshopProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "yxhshop.swagger", name = {"title", "basePackage", "description"})
	public Docket createRestApi() {
		YxhshopProperties.Swagger swagger = properties.getSwagger();
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
			.paths(PathSelectors.any())
			.build();
	}

	private ApiInfo apiInfo() {
		YxhshopProperties.Swagger swagger = properties.getSwagger();
		return new ApiInfoBuilder()
			.title(swagger.getTitle())
			.description(swagger.getDescription())
			.termsOfServiceUrl("https://github.com/keybesan")
			.version("1.0")
			.build();
	}

}
