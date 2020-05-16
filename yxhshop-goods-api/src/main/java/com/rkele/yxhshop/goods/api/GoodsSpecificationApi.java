package com.rkele.yxhshop.goods.api;

import com.rkele.yxhshop.goods.fallback.GoodsSpecificationApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.dto.GoodsSpecificationDTO;
import com.rkele.yxhshop.goods.po.GoodsSpecification;

import java.util.List;

@FeignClient(value = "yxhshop-goods", path = "goodsSpecification", fallback = GoodsSpecificationApiFallback.class)
public interface GoodsSpecificationApi extends Api<GoodsSpecification> {

    @GetMapping("/queryGoodsDetailSpecificationByGoodsId")
    Result<List<GoodsSpecificationDTO>> queryGoodsDetailSpecificationByGoodsId(@RequestParam("goodsId") Integer goodsId);

    @GetMapping("/queryValueByGoodsIdAndIdIn")
    Result<List<String>> queryValueByGoodsIdAndIdIn(@RequestParam("goodsId") Integer goodsId, @RequestParam("goodsSpecificationIds") List<Integer> goodsSpecificationIds);
}
