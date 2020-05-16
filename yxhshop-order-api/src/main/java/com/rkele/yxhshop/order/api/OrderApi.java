package com.rkele.yxhshop.order.api;

import com.rkele.yxhshop.order.fallback.OrderApiFallback;
import com.rkele.yxhshop.order.po.Order;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;

@FeignClient(value = "yxhshop-order", path = "order", fallback = OrderApiFallback.class)
public interface OrderApi extends Api<Order> {
}
