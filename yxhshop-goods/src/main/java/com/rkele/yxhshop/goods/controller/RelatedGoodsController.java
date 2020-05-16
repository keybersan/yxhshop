package com.rkele.yxhshop.goods.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.goods.api.RelatedGoodsApi;
import com.rkele.yxhshop.goods.po.RelatedGoods;

@RequestMapping("related-goods")
@RestController
public class RelatedGoodsController extends BaseApi<RelatedGoods> implements RelatedGoodsApi {

}
