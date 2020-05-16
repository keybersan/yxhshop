package com.rkele.yxhshop.goods.controller;

import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.po.Goods;
import com.rkele.yxhshop.goods.query.GoodsSearchQuery;
import com.rkele.yxhshop.goods.vo.GoodsResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.goods.api.BrandApi;
import com.rkele.yxhshop.goods.po.Brand;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController extends BaseApi<Brand> implements BrandApi {


    @GetMapping("/list")
    public Result<List<Brand>> queryGoodsPageInfo(Brand brand) {
        Criteria<Brand, Object> criteria = Criteria.of(Brand.class);
        if (brand.getNewly() != null) {
            criteria.andEqualTo(Brand::getNewly, brand.getNewly());
        }
        return this.queryByCriteria(criteria);
    }
}
