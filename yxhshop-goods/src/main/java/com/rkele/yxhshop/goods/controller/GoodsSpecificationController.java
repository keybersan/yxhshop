package com.rkele.yxhshop.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.api.GoodsSpecificationApi;
import com.rkele.yxhshop.goods.dto.GoodsSpecificationDTO;
import com.rkele.yxhshop.goods.po.GoodsSpecification;
import com.rkele.yxhshop.goods.service.GoodsSpecificationService;

import java.util.List;

@RestController
@RequestMapping("/goodsSpecification")
public class GoodsSpecificationController extends BaseApi<GoodsSpecification> implements GoodsSpecificationApi {

    @Autowired
    private GoodsSpecificationService goodsSpecificationService;

    @Override
    public Result<List<GoodsSpecificationDTO>> queryGoodsDetailSpecificationByGoodsId(Integer goodsId) {
        return Result.success(goodsSpecificationService.queryGoodsDetailSpecificationByGoodsId(goodsId));
    }

    @Override
    public Result<List<String>> queryValueByGoodsIdAndIdIn(Integer goodsId, List<Integer> goodsSpecificationIds) {
        return Result.success(goodsSpecificationService.queryValueByGoodsIdAndIdIn(goodsId, goodsSpecificationIds));
    }
}
