package com.rkele.yxhshop.order.vo;

import com.rkele.yxhshop.order.po.Order;

public class OrderSubmitResultVO {

    private Order orderInfo;

    public OrderSubmitResultVO() {
    }

    public OrderSubmitResultVO(Order orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Order getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Order orderInfo) {
        this.orderInfo = orderInfo;
    }
}
