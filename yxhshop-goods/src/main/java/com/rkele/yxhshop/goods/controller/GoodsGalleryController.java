package com.rkele.yxhshop.goods.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.goods.api.GoodsGalleryApi;
import com.rkele.yxhshop.goods.po.GoodsGallery;

@RestController
@RequestMapping("/goodsGallery")
public class GoodsGalleryController extends BaseApi<GoodsGallery> implements GoodsGalleryApi {
}
