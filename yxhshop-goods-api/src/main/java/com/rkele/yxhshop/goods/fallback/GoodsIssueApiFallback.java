package com.rkele.yxhshop.goods.fallback;

import com.rkele.yxhshop.goods.api.GoodsIssueApi;
import com.rkele.yxhshop.goods.po.GoodsIssue;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

@Component
public class GoodsIssueApiFallback extends ApiFallback<GoodsIssue> implements GoodsIssueApi {
}
