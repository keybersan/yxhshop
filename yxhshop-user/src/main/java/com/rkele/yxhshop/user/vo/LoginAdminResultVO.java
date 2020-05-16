package com.rkele.yxhshop.user.vo;


import com.rkele.yxhshop.user.po.Admin;

public class LoginAdminResultVO {

    private String token;

    private Admin userInfo;

    public LoginAdminResultVO() {
    }

    public LoginAdminResultVO(String token, Admin userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Admin getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Admin userInfo) {
        this.userInfo = userInfo;
    }
}
