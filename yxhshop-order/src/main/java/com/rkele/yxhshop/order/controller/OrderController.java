package com.rkele.yxhshop.order.controller;

import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.order.api.OrderApi;
import com.rkele.yxhshop.order.po.Order;
import com.rkele.yxhshop.order.po.OrderExpress;
import com.rkele.yxhshop.order.query.OrderQuery;
import com.rkele.yxhshop.order.service.OrderExpressService;
import com.rkele.yxhshop.order.service.OrderService;
import com.rkele.yxhshop.order.vo.OrderDetailVO;
import com.rkele.yxhshop.order.vo.OrderListVO;
import com.rkele.yxhshop.order.vo.OrderSubmitParamVO;
import com.rkele.yxhshop.order.vo.OrderSubmitResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.rkele.yxhshop.common.api.BaseApi;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseApi<Order> implements OrderApi {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderExpressService orderExpressService;


    @GetMapping({"/list"})
    public Result<List<OrderListVO>> queryOrderList(OrderQuery orderQuery,HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        return Result.success(orderService.queryOrderList(orderQuery,token));
    }

    @GetMapping("/detail")
    public Result<OrderDetailVO> queryOrderDetail(@NotNull Integer orderId) {
        return Result.success(orderService.queryOrderDetail(orderId));
    }

    /**
     * @return
     */
    @PostMapping("/submit")
    public Result<OrderSubmitResultVO> submitOrder(@Validated @RequestBody OrderSubmitParamVO orderSubmitParamDTO,HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        return Result.success(orderService.submitOrder(orderSubmitParamDTO,token));
    }

    /**
     * 获取最新的订单物流信息
     *
     * @param orderId
     * @return
     */
    @GetMapping("/express")
    public Result<OrderExpress> queryLatestExpressInfo(@NotNull Integer orderId) {
        return Result.success(orderExpressService.queryOne(new OrderExpress().setOrderId(orderId)));
    }
}
