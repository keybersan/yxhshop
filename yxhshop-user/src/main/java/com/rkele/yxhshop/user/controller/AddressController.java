package com.rkele.yxhshop.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.api.AddressApi;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.service.AddressService;

import com.rkele.yxhshop.user.vo.AddressVO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.user.po.Address;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseApi<Address> implements AddressApi {
    @Autowired
    AddressService addressService;

    @GetMapping("/list")
    public Result<List<AddressVO>> queryList(HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        return Result.success(addressService.queryDetailList(user));
    }

    @GetMapping("/detail")
    public Result<AddressVO> queryDetail(Integer id) {
        return Result.success(addressService.queryDetail(id));
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody Address entity,HttpServletRequest request)  {

        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        entity.setUserId(user.getId());
        if(entity.getId()!=null&&entity.getId()!=0){
            return this.updateNotNull(entity);
        }
        return this.create(entity);
    }

    @PostMapping("/update")
    public Result update(@Validated @RequestBody Address entity,HttpServletRequest request) {

        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        entity.setUserId(user.getId());
        addressService.updateNotNull(entity);
        return Result.success("消费成功", "200");
    }

    @PostMapping("/delete")
    public Result delete(@Validated @RequestBody Address entity) {
        addressService.deleteById(entity.getId());
        return Result.success("消费成功", "200");
    }

}
