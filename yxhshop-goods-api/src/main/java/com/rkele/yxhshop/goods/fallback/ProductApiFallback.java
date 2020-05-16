package com.rkele.yxhshop.goods.fallback;

import com.rkele.yxhshop.goods.api.ProductApi;
import com.rkele.yxhshop.goods.po.Product;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

@Component
public class ProductApiFallback extends ApiFallback<Product> implements ProductApi {
}
