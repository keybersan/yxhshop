package com.rkele.yxhshop.goods.fallback;

import com.rkele.yxhshop.goods.api.BrandApi;
import com.rkele.yxhshop.goods.po.Brand;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

@Component
public class BrandApiFallback extends ApiFallback<Brand> implements BrandApi {


}
