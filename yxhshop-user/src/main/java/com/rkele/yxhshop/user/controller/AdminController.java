package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.user.api.AdminApi;
import com.rkele.yxhshop.user.po.Admin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkele.yxhshop.common.api.BaseApi;

/**
 * Created by keybersan on 2020/2/27.
 * 注意需要创建service 实体，否则iservice 没办法进行代理生成实例
 * 注意创建po 以及mapper文件
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseApi<Admin> implements AdminApi {
}
