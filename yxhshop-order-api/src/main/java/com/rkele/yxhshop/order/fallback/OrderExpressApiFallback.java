package com.rkele.yxhshop.order.fallback;

import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.order.api.OrderExpressApi;
import com.rkele.yxhshop.order.po.OrderExpress;

@Component
public class OrderExpressApiFallback extends ApiFallback<OrderExpress> implements OrderExpressApi {
}
