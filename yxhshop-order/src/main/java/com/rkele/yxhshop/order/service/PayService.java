package com.rkele.yxhshop.order.service;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;

/**
 * Created by keybersan on 2020/5/3.
 */
public interface PayService {
    public WxPayMpOrderResult prepay(Integer orderId) throws WxPayException;
    public String notify(String xml);
}
