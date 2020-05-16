package com.rkele.yxhshop.goods.api;

import com.rkele.yxhshop.goods.fallback.GoodsAttributeApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.dto.GoodsAttributeDTO;
import com.rkele.yxhshop.goods.po.GoodsAttribute;

import java.util.List;

@FeignClient(value = "yxhshop-goods", path = "goodsAttribute", fallback = GoodsAttributeApiFallback.class)
public interface GoodsAttributeApi extends Api<GoodsAttribute> {

    @GetMapping("/queryGoodsDetailAttributeByGoodsId")
    Result<List<GoodsAttributeDTO>> queryGoodsDetailAttributeByGoodsId(@RequestParam("goodsId") Integer goodsId);

}
