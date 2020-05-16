package com.rkele.yxhshop.gateway.filter;

import com.rkele.yxhshop.gateway.config.RedissonConfig;
import com.rkele.yxhshop.gateway.contant.JWTConstants;
import com.rkele.yxhshop.gateway.contant.YxhshopWechatResultStatus;
import com.rkele.yxhshop.gateway.exception.YxhException;
import com.rkele.yxhshop.gateway.exception.YxhshopException;
import com.rkele.yxhshop.gateway.utils.JsonUtils;
import com.rkele.yxhshop.gateway.utils.JwtHelper;
import com.rkele.yxhshop.gateway.utils.PathPatterns;
import com.rkele.yxhshop.gateway.utils.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 2020/4/28.
 * 通过访问localhost:8089/actuator/refresh  更新配置
 */
@Component
@ConfigurationProperties("org.my.jwt")
@Setter
@Getter
@Slf4j
@RefreshScope
public class JwtTokenFilter implements GlobalFilter,Ordered {

    private String[] skipAuthUrls;


    @Autowired
    RedissonClient redissonClient;
    @Autowired
    PathPatterns pathPatterns;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        System.out.println(url);
        //跳过不需要验证的路径
        if(null != skipAuthUrls &&  pathPatterns.checkWhiteList(skipAuthUrls, url)){
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(JWTConstants.JWT_KEY_NAME);

        ServerHttpResponse resp = exchange.getResponse();
        if(StringUtils.isBlank(token)){
            //没有token
            return authErro(resp,"请先登录");
        }else{
            //有token
            try {
                Claims claims = JwtHelper.parseJWT(token);
               /* RBucket<Claims> bucket = redissonClient.getBucket(token);
                bucket.set(claims);*/
                return chain.filter(exchange);
            }catch (ExpiredJwtException e){
                if(e.getMessage().contains("Allowed clock skew")){
                    //throw new YxhshopException(YxhshopWechatResultStatus.LOGIN_ERROR);
                    return authErro(resp,"认证过期");
                }else{
                    return authErro(resp,"认证失败");
                    //throw new YxhshopException(YxhshopWechatResultStatus.LOGIN_ERROR);
                }
            }catch (Exception e) {

                return authErro(resp,"认证失败");
            }
        }

    }
    /**
     * 认证错误输出
     * @param resp 响应对象
     * @param mess 错误信息
     * @return
     */
    private Mono<Void> authErro(ServerHttpResponse resp, String mess) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        Result ret = Result.failure(YxhshopWechatResultStatus.LOGIN_ERROR);

        String returnStr = "";
        returnStr = JsonUtils.toJson(ret);

        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Mono.just(buffer));
    }





    @Override
    public int getOrder() {
        return -100;
    }
}
