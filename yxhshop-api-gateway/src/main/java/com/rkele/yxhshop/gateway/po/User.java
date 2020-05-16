package com.rkele.yxhshop.gateway.po;


import com.rkele.yxhshop.gateway.enums.GenderEnum;

import java.util.Date;


public class User {

    private Integer id;

    private String username;

    private String password;


    private GenderEnum gender;

    private Date birthday;


    private Date registerTime;


    private Date lastLoginTime;


    private String lastLoginIp;


    private Byte userLevelId;

    private String nickname;

    private String mobile;


    private String registerIp;

    private String avatar;


    private String wechatOpenId;


}
