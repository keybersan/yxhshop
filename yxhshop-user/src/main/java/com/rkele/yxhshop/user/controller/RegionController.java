package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.user.api.RegionApi;
import com.rkele.yxhshop.user.po.Region;
import com.rkele.yxhshop.user.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.common.utils.Result;

import java.util.List;

@RestController
@RequestMapping("region")
public class RegionController extends BaseApi<Region> implements RegionApi {

    @Autowired
    private RegionService regionService;

    @Override
    public Result<String> queryNameById(Short id) {
        return Result.success(regionService.queryNameById(id));
    }

    @GetMapping("/list")
    public Result<List<Region>> queryList(Short parentId) {
        return this.queryList(new Region().setParentId(parentId));
    }
}
