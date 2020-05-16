package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.user.po.UserLevel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.user.api.UserLevelApi;

@RestController
@RequestMapping("/userLevel")
public class UserLevelController extends BaseApi<UserLevel> implements UserLevelApi {
}
