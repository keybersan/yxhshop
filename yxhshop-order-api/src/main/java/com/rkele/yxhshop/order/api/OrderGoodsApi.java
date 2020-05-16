package com.rkele.yxhshop.order.api;

import com.rkele.yxhshop.order.fallback.OrderGoodsApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.order.po.OrderGoods;

/**
 * weshop-order  注册中心的服务名
 * name：微服务的名称，一定要以eureka后台配置的保持一致。
 url：可以手动指定feign的调用地址
 fallback：标记容错后执行的类
 * create 2018-07-09
 **/
@FeignClient(value = "yxhshop-order", path = "orderGoods", fallback = OrderGoodsApiFallback.class)
public interface OrderGoodsApi extends Api<OrderGoods> {
}
