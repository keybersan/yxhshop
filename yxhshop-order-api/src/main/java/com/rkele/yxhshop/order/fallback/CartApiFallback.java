package com.rkele.yxhshop.order.fallback;

import com.rkele.yxhshop.order.api.CartApi;
import com.rkele.yxhshop.order.po.Cart;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

@Component
public class CartApiFallback extends ApiFallback<Cart> implements CartApi {
}
