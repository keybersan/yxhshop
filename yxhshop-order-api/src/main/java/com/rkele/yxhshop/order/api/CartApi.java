package com.rkele.yxhshop.order.api;

import com.rkele.yxhshop.order.po.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.order.fallback.CartApiFallback;

@FeignClient(value = "yxhshop-order", path = "cart", fallback = CartApiFallback.class)
public interface CartApi extends Api<Cart> {

}
