package com.rkele.yxhshop.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.user.api.FootprintApi;
import com.rkele.yxhshop.user.po.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.dto.GoodsFootprintDTO;
import com.rkele.yxhshop.user.po.Footprint;
import com.rkele.yxhshop.user.service.FootprintService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("footprint")
public class FootprintController extends BaseApi<Footprint> implements FootprintApi {

    @Autowired
    private FootprintService footprintService;

    @Override
    public Result<List<GoodsFootprintDTO>> queryGoodsFootprintByUserId(Integer userId) {
        List<GoodsFootprintDTO> goodsFootprintList = footprintService.queryGoodsFootprintByUserId(userId);

        return Result.success(goodsFootprintList);
    }



    @PostMapping("/deleteGoodsFootprint")
    public Result deleteGoodsFootprint(@NotNull Integer goodsId,HttpServletRequest request) throws Exception {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        return this.delete(new Footprint().setGoodsId(goodsId).setUserId(user.getId()));
    }

    @GetMapping("/list")
    public Result<List<List<GoodsFootprintDTO>>> queryGoodsFootprintList(HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        return Result.success(footprintService.queryGoodsFootprintTimeLine(user));
    }
}
