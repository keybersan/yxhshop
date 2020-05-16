package com.rkele.yxhshop.order.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.order.po.Order;
import com.rkele.yxhshop.order.query.OrderQuery;
import com.rkele.yxhshop.order.vo.OrderDetailVO;
import com.rkele.yxhshop.order.vo.OrderListVO;
import com.rkele.yxhshop.order.vo.OrderSubmitParamVO;
import com.rkele.yxhshop.order.vo.OrderSubmitResultVO;

import java.util.List;

/**
 * @author zhs@outlook.com
 */

public interface OrderService extends IService<Order> {
    public List<OrderListVO> queryOrderList(OrderQuery orderQuery, String token);
    public OrderDetailVO queryOrderDetail(Integer orderId);
    public OrderSubmitResultVO submitOrder(OrderSubmitParamVO orderSubmitParamDTO, String token);

}
