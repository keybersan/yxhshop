package com.rkele.yxhshop.order.api;

import com.rkele.yxhshop.order.fallback.OrderExpressApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.order.po.OrderExpress;

@FeignClient(value = "yxhshop-order", path = "orderExpress", fallback = OrderExpressApiFallback.class)
public interface OrderExpressApi extends Api<OrderExpress> {
}
