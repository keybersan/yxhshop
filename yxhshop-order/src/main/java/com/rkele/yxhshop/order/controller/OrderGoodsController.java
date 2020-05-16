package com.rkele.yxhshop.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.order.api.OrderGoodsApi;
import com.rkele.yxhshop.order.po.OrderGoods;

@RestController
@RequestMapping("/orderGoods")
public class OrderGoodsController extends BaseApi<OrderGoods> implements OrderGoodsApi {
}
