package com.rkele.yxhshop.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.service.KeywordsService;
import com.rkele.yxhshop.user.vo.SearchIndexVO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.user.api.SearchHistoryApi;
import com.rkele.yxhshop.user.po.SearchHistory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/searchHistory")
public class SearchHistoryController extends BaseApi<SearchHistory> implements SearchHistoryApi {
    @Autowired
    KeywordsService keywordsService;

    @GetMapping("/helper")
    public Result<List<String>> helper(String keyword) {
        return Result.success(keywordsService.queryByKeyword(keyword));
    }

    @PostMapping("/clear-history")
    public Result clearHistory(HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        this.delete(new SearchHistory().setUserId(user.getId()));
        return Result.success("消费成功", "200");
    }

    @GetMapping("/index")
    public Result<SearchIndexVO> index(HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        return Result.success(keywordsService.index(token));
    }
}
