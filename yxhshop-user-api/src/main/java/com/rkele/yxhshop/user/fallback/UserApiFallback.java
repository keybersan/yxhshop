package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.UserApi;
import com.rkele.yxhshop.user.po.User;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

@Component
public class UserApiFallback extends ApiFallback<User> implements UserApi {
}
