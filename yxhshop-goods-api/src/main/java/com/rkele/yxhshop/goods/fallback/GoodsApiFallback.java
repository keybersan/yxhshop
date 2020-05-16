package com.rkele.yxhshop.goods.fallback;

import com.rkele.yxhshop.goods.po.Goods;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.goods.api.GoodsApi;

@Component
public class GoodsApiFallback extends ApiFallback<Goods> implements GoodsApi {
}
