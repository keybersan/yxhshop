package com.rkele.yxhshop.order.fallback;

import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.order.api.OrderGoodsApi;
import com.rkele.yxhshop.order.po.OrderGoods;

@Component
public class OrderGoodsApiFallback extends ApiFallback<OrderGoods> implements OrderGoodsApi {

}
