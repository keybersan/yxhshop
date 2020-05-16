package com.rkele.yxhshop.goods.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.goods.fallback.GoodsGalleryApiFallback;
import com.rkele.yxhshop.goods.po.GoodsGallery;

@FeignClient(value = "yxhshop-goods", path = "goodsGallery", fallback = GoodsGalleryApiFallback.class)
public interface GoodsGalleryApi extends Api<GoodsGallery> {

}
