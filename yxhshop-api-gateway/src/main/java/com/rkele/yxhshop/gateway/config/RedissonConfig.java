package com.rkele.yxhshop.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@RefreshScope
public class RedissonConfig {

    @Value("${spring.redis.host}")
	private String host;

    @Value("${spring.redis.port}")
	private String port;
    @Value("${spring.redis.password}")
	private String password;

	@Bean
    RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://" + host + ":" + port);
		if(StringUtils.isNotBlank(password)) {
			config.useSingleServer().setPassword(password);
		}
		return Redisson.create(config);
	}



}
