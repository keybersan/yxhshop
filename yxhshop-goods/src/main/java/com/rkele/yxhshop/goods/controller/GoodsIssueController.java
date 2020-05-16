package com.rkele.yxhshop.goods.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.goods.api.GoodsIssueApi;
import com.rkele.yxhshop.goods.po.GoodsIssue;

@RestController
@RequestMapping("/goodsIssue")
public class GoodsIssueController extends BaseApi<GoodsIssue> implements GoodsIssueApi {

}
