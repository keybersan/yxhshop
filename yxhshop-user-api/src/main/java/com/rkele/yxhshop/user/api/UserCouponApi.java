package com.rkele.yxhshop.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.user.fallback.UserCouponApiFallback;
import com.rkele.yxhshop.user.po.UserCoupon;

@FeignClient(value = "yxhshop-user", path = "userCoupon", fallback = UserCouponApiFallback.class)
public interface UserCouponApi extends Api<UserCoupon> {
}
