package com.rkele.yxhshop.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.vo.CollectAddOrDeleteParamVO;
import com.rkele.yxhshop.user.vo.CollectAddOrDeleteResultVO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.api.CollectApi;
import com.rkele.yxhshop.user.dto.GoodsCollectDTO;
import com.rkele.yxhshop.user.po.Collect;
import com.rkele.yxhshop.user.service.CollectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectController extends BaseApi<Collect> implements CollectApi {

    @Autowired
    private CollectService collectService;

    @Override
    public Result<List<GoodsCollectDTO>> queryGoodsCollectList(Integer userId) {
        return Result.success(collectService.queryGoodsCollectList(userId));
    }
    @PostMapping("/add-or-delete")
    public Result<CollectAddOrDeleteResultVO> addOrDelete(@RequestBody CollectAddOrDeleteParamVO collectAddOrDeleteParamDTO,HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        return Result.success(collectService.addOrDelete(collectAddOrDeleteParamDTO,token));
    }

    @GetMapping("/list")
    public Result queryList(Integer typeId,HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        return this.queryGoodsCollectList(user.getId());
    }
}
