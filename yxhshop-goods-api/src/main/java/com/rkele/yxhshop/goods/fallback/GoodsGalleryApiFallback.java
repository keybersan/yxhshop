package com.rkele.yxhshop.goods.fallback;

import com.rkele.yxhshop.goods.api.GoodsGalleryApi;
import com.rkele.yxhshop.goods.po.GoodsGallery;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

@Component
public class GoodsGalleryApiFallback extends ApiFallback<GoodsGallery> implements GoodsGalleryApi {
}
