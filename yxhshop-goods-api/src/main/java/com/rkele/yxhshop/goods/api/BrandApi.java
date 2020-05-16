package com.rkele.yxhshop.goods.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.goods.fallback.BrandApiFallback;
import com.rkele.yxhshop.goods.po.Brand;

/**
 * @author zhs@outlook.com
 */

@FeignClient(value = "yxhshop-goods", path = "brand", fallback = BrandApiFallback.class)
public interface BrandApi extends Api<Brand> {
}
