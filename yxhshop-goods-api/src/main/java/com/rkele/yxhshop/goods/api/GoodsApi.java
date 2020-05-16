package com.rkele.yxhshop.goods.api;

import com.rkele.yxhshop.goods.fallback.GoodsApiFallback;
import com.rkele.yxhshop.goods.po.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;

/**
 * @author zhs@outlook.com
 */
@FeignClient(value = "yxhshop-goods", path = "goods", fallback = GoodsApiFallback.class)
public interface GoodsApi extends Api<Goods> {
}
