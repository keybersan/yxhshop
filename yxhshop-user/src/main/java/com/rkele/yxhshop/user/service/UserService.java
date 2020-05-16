package com.rkele.yxhshop.user.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.po.Admin;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.vo.LoginAuthParamVO;
import com.rkele.yxhshop.user.vo.LoginAuthResultVO;
import me.chanjar.weixin.common.error.WxErrorException;

public interface UserService extends IService<User> {
    LoginAuthResultVO login(LoginAuthParamVO loginAuthParamVO) throws WxErrorException;
    Result loginByAdmin(Admin admin);
}
