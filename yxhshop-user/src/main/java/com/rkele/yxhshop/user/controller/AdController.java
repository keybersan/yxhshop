package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.user.api.AdApi;
import com.rkele.yxhshop.user.po.Ad;

import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController extends BaseApi<Ad> implements AdApi {


    @GetMapping("/list")
    public Result<List<Ad>> queryGoodsPageInfo(Ad ad) {
        return this.queryByCriteria(Criteria.of(Ad.class).fields(Ad::getId, Ad::getLink, Ad::getImageUrl).andEqualTo(Ad::getAdPositionId, ad.getAdPositionId()));
    }
}
