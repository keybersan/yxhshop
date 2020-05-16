package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.user.api.UserCouponApi;
import com.rkele.yxhshop.user.po.UserCoupon;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;

@RestController
@RequestMapping("/UserCoupon")
public class UserCouponController extends BaseApi<UserCoupon> implements UserCouponApi {
}
