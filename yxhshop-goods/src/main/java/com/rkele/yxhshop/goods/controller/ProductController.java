package com.rkele.yxhshop.goods.controller;

import com.rkele.yxhshop.goods.api.ProductApi;
import com.rkele.yxhshop.goods.po.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;

@RequestMapping("/product")
@RestController
public class ProductController extends BaseApi<Product> implements ProductApi {
}
