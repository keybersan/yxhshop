package com.rkele.yxhshop.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.order.api.OrderExpressApi;
import com.rkele.yxhshop.order.po.OrderExpress;

@RestController
@RequestMapping("/orderExpress")
public class OrderExpressController extends BaseApi<OrderExpress> implements OrderExpressApi {
}
