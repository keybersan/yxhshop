package com.rkele.yxhshop.user.api;

import com.rkele.yxhshop.user.fallback.UserLevelApiFallback;
import com.rkele.yxhshop.user.po.UserLevel;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;

@FeignClient(value = "yxhshop-user", path = "userLevel", fallback = UserLevelApiFallback.class)
public interface UserLevelApi extends Api<UserLevel> {
}
