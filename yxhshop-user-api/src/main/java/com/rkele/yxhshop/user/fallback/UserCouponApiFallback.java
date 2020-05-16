package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.UserCouponApi;
import com.rkele.yxhshop.user.po.UserCoupon;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

@Component
public class UserCouponApiFallback extends ApiFallback<UserCoupon> implements UserCouponApi {
}
