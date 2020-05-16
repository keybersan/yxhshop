package com.rkele.yxhshop.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.user.fallback.UserApiFallback;
import com.rkele.yxhshop.user.po.User;

@FeignClient(value = "yxhshop-user", path = "user", fallback = UserApiFallback.class)
public interface UserApi extends Api<User> {
}
