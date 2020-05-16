package com.rkele.yxhshop.goods.api;

import com.rkele.yxhshop.goods.fallback.RelatedGoodsApiFallback;
import com.rkele.yxhshop.goods.po.RelatedGoods;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;

@FeignClient(value = "yxhshop-goods", path = "related-goods", fallback = RelatedGoodsApiFallback.class)
public interface RelatedGoodsApi extends Api<RelatedGoods> {
}
