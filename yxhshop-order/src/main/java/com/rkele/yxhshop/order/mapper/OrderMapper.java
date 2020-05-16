package com.rkele.yxhshop.order.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.order.po.Order;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface OrderMapper extends MyMapper<Order> {

    public Integer insertOrder(Order order);
}
