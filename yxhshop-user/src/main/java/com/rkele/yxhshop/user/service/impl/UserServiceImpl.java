package com.rkele.yxhshop.user.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.*;
import com.rkele.yxhshop.user.api.AdminApi;
import com.rkele.yxhshop.user.config.WxMaConfiguration;
import com.rkele.yxhshop.user.enums.GenderEnum;
import com.rkele.yxhshop.user.mapper.UserMapper;
import com.rkele.yxhshop.user.po.Admin;
import com.rkele.yxhshop.user.service.AdminService;
import com.rkele.yxhshop.user.vo.LoginAdminResultVO;
import com.rkele.yxhshop.user.vo.LoginAuthParamVO;
import com.rkele.yxhshop.user.vo.LoginAuthResultVO;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.service.UserService;

import java.util.Date;
import java.util.UUID;

/**
 * @author zhs@outlook.com
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Value("${yxhshop.wx.miniapp.appid}")
    private String appid;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminService adminService;

    @Override
    public LoginAuthResultVO login(LoginAuthParamVO loginAuthParamVO) throws WxErrorException {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        WxMaJscode2SessionResult sessionInfo = wxService.getUserService().getSessionInfo(loginAuthParamVO.getCode());
        LoginAuthParamVO.UserInfoX.UserInfo userInfo = loginAuthParamVO.getUserInfo().getUserInfo();
        //根据openId查询用户是否已经注册
        User user = this.queryOneByCriteria(Criteria.of(User.class).andEqualTo(User::getWechatOpenId, sessionInfo.getOpenid()));
        if (user == null) {
            //注册
            user = new User();
            user.setUsername("微信用户" + UUID.randomUUID().toString());
            user.setPassword("");
            user.setRegisterTime(new Date());
            user.setRegisterIp(WebUtil.getInstance().getIpAddress());
            user.setMobile("");
            user.setWechatOpenId(sessionInfo.getOpenid());
            user.setAvatar(userInfo.getAvatarUrl());
            user.setGender(EnumUtils.getEnum(GenderEnum.class, userInfo.getGender()));
            user.setNickname(userInfo.getNickName());
            this.create(user);
        }
        //查询用户信息
        User newUser = this.queryOneByCriteria(Criteria.of(User.class).andEqualTo(User::getWechatOpenId, sessionInfo.getOpenid()));
        newUser.setLastLoginTime(new Date());
        newUser.setLastLoginIp(WebUtil.getInstance().getIpAddress());
        //更新登陆信息
        this.updateNotNull(newUser);
        //生成token
        String token = JwtHelper.createJWT("wechat", JsonUtils.toJson(newUser), JWTConstants.JWT_TTL);
        return new LoginAuthResultVO(token, newUser);
    }

    @Override
    public Result loginByAdmin(Admin old) {

         Admin admin = adminService.queryOneByCriteria(Criteria.of(Admin.class).andEqualTo(Admin::getUsername,old.getUsername()).andEqualTo(Admin::getPassword,old.getPassword()));
        if (admin == null) {
            return Result.failure("-1","无用户信息");
        }
        //查询用户信息

        admin.setLastLoginTime(new Date());
        admin.setLastLoginIp(WebUtil.getInstance().getIpAddress());
        //更新登陆信息
        adminService.updateNotNull(admin);
        //生成token
        String token = JwtHelper.createJWT("admin", JsonUtils.toJson(admin), JWTConstants.JWT_TTL);
        return Result.success(new LoginAdminResultVO(token, admin));
    }
}
