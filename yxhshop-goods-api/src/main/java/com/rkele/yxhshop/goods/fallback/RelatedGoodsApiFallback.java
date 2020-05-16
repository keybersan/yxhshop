package com.rkele.yxhshop.goods.fallback;

import com.rkele.yxhshop.goods.po.RelatedGoods;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.goods.api.RelatedGoodsApi;

@Component
public class RelatedGoodsApiFallback extends ApiFallback<RelatedGoods> implements RelatedGoodsApi {
}
