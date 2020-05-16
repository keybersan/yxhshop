package com.rkele.yxhshop.order.fallback;

import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.order.api.OrderApi;
import com.rkele.yxhshop.order.po.Order;

@Component
public class OrderApiFallback extends ApiFallback<Order> implements OrderApi {
}
