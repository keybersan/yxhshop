package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.user.api.KeywordsApi;
import com.rkele.yxhshop.user.service.KeywordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.po.Keywords;

import java.util.List;

@RestController
@RequestMapping("/keywords")
public class KeywordsController extends BaseApi<Keywords> implements KeywordsApi {

    @Autowired
    private KeywordsService keywordsService;

    @Override
    public Result<List<String>> queryByKeyword(String keyword) {
        return Result.success(keywordsService.queryByKeyword(keyword));
    }
}
