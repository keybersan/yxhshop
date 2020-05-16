package com.rkele.yxhshop.goods.api;

import com.rkele.yxhshop.goods.po.GoodsIssue;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.goods.fallback.GoodsIssueApiFallback;

@FeignClient(value = "yxhshop-goods", path = "goodsIssue", fallback = GoodsIssueApiFallback.class)
public interface GoodsIssueApi extends Api<GoodsIssue> {

}
