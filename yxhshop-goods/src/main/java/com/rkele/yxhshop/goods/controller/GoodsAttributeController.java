package com.rkele.yxhshop.goods.controller;

import com.rkele.yxhshop.goods.api.GoodsAttributeApi;
import com.rkele.yxhshop.goods.dto.GoodsAttributeDTO;
import com.rkele.yxhshop.goods.po.GoodsAttribute;
import com.rkele.yxhshop.goods.service.GoodsAttributeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.common.utils.Result;

import java.util.List;

@RestController
@RequestMapping("/goodsAttribute")
public class GoodsAttributeController extends BaseApi<GoodsAttribute> implements GoodsAttributeApi {

    private GoodsAttributeService goodsAttributeService;

    @Override
    public Result<List<GoodsAttributeDTO>> queryGoodsDetailAttributeByGoodsId(Integer goodsId) {
        return Result.success(goodsAttributeService.queryGoodsDetailAttributeByGoodsId(goodsId));
    }
}
