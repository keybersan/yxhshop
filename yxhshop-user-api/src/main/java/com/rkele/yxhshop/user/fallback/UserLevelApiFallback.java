package com.rkele.yxhshop.user.fallback;

import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.user.api.UserLevelApi;
import com.rkele.yxhshop.user.po.UserLevel;

@Component
public class UserLevelApiFallback extends ApiFallback<UserLevel> implements UserLevelApi {
}
