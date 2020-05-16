package com.rkele.yxhshop.goods.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.goods.fallback.ProductApiFallback;
import com.rkele.yxhshop.goods.po.Product;

@FeignClient(value = "yxhshop-goods", path = "product", fallback = ProductApiFallback.class)
public interface ProductApi extends Api<Product> {
}
