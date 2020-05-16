package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.api.UserApi;
import com.rkele.yxhshop.user.po.Admin;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.service.UserService;
import com.rkele.yxhshop.user.vo.LoginAuthParamVO;
import com.rkele.yxhshop.user.vo.LoginAuthResultVO;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;

@RestController
@RequestMapping("/user")
public class UserController extends BaseApi<User> implements UserApi {
    @Autowired
    UserService userService;

    /**
     * 微信小程序登录
     * @param loginAuthParamVO
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/login")
    public Result<LoginAuthResultVO> loginByWeixin(@RequestBody @Validated LoginAuthParamVO loginAuthParamVO) throws WxErrorException {
        return Result.success(userService.login(loginAuthParamVO));
    }

    /**
     * 后台登陆
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/loginByAdmin")
    public Result<LoginAuthResultVO> loginByAdmin(@RequestBody @Validated Admin admin) throws WxErrorException {
        return userService.loginByAdmin(admin);
    }
}
