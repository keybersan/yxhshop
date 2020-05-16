package com.rkele.yxhshop.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "yxhshop")
public class YxhshopProperties {

    private Swagger swagger = new Swagger();

    public Swagger getSwagger() {
        return swagger;
    }

    public YxhshopProperties setSwagger(Swagger swagger) {
        this.swagger = swagger;
        return this;
    }

    public static class Swagger {
        @NotNull
        private String basePackage;
        @Value("${spring.application.name}")
        private String title;
        private String description = "YXHSHOP | 又一个小程序商城应用";

        public String getBasePackage() {
            return basePackage;
        }

        public Swagger setBasePackage(String basePackage) {
            this.basePackage = basePackage;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Swagger setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public Swagger setDescription(String description) {
            this.description = description;
            return this;
        }
    }
}
