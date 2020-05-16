package com.rkele.yxhshop.order.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.enums.YxhshopWechatResultStatus;
import com.rkele.yxhshop.common.exception.YxhshopException;
import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.common.utils.StringUtils;
import com.rkele.yxhshop.common.utils.WebUtil;
import com.rkele.yxhshop.order.api.WxPayApi;
import com.rkele.yxhshop.order.enums.OrderStatusEnum;
import com.rkele.yxhshop.order.enums.PayStatusEnum;
import com.rkele.yxhshop.order.enums.WechatPayResultStatus;
import com.rkele.yxhshop.order.exception.WeshopPayException;
import com.rkele.yxhshop.order.po.Order;
import com.rkele.yxhshop.order.service.OrderService;
import com.rkele.yxhshop.order.service.PayService;
import com.rkele.yxhshop.user.api.UserApi;
import com.rkele.yxhshop.user.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by keybersan on 2020/5/3.
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    OrderService orderService;

    @Autowired
    UserApi userApi;
    private WxPayService wxService = new WxPayServiceImpl();

    @Override
    public WxPayMpOrderResult prepay(Integer orderId) throws WxPayException {
        Order order = orderService.queryById(orderId);
        if (order == null) {
            //订单已取消
            throw new YxhshopException(YxhshopWechatResultStatus.ORDER_CANCELED);
        }
        if (order.getPayStatus() == PayStatusEnum.PAID) {
            throw new YxhshopException(YxhshopWechatResultStatus.ORDER_PAID);
        }
        String wechatOpenId = userApi.queryOneByCriteria(Criteria.of(User.class).fields(User::getWechatOpenId).andEqualTo(User::getId, order.getUserId()))
            .orElseGetData(() -> new User()).getWechatOpenId();
        //不存在openid，说明不是微信下的单
        if (StringUtils.isBlank(wechatOpenId)) {
            throw new YxhshopException(YxhshopWechatResultStatus.WECHAT_PAY_FAIL);
        }
        //统一下单
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
            .openid(wechatOpenId)
            .body("订单编号：" + order.getOrderSN())
            .outTradeNo(order.getOrderSN())
            .totalFee(order.getActualPrice().multiply(new BigDecimal(100)).intValue())//订单总金额，单位为分
            .spbillCreateIp(WebUtil.getInstance().getIpAddress())
            .build();
        Result<WxPayMpOrderResult> result = wxService.createOrder(wxPayUnifiedOrderRequest);
        if (!result.isSuccess()) {
            throw new YxhshopException(YxhshopWechatResultStatus.WECHAT_PAY_FAIL);
        }
        return result.getData();
    }

    @Override
    public String notify(String xml) {
        WxPayOrderNotifyResult result = null;
        try {
            result = wxService.parseOrderNotifyResult(xml);
        } catch (WxPayException e) {
            e.printStackTrace();
            throw new WeshopPayException(WechatPayResultStatus.WECHAT_PAY_FAIL);
        }

        String orderSN = result.getOutTradeNo();

        Order order = orderService.queryOneByCriteria(Criteria.of(Order.class).andEqualTo(Order::getOrderSN, orderSN));
        if (order == null) {
            return JWTConstants.XML_PAY_ORDER_NOT_FOUND;
        }
        //订单状态仓库配送，支付状态变为已付款
        Integer updateResult = orderService.updateNotNull(order.setOrderStatus(OrderStatusEnum.WAREHOUSE_DISTRIBUTION).setPayStatus(PayStatusEnum.PAID));
        if (updateResult != 1) {
            return JWTConstants.XML_PAY_ORDER_NOT_FOUND;
        }
        return JWTConstants.XML_PAY_ORDER_OK;
    }
}
