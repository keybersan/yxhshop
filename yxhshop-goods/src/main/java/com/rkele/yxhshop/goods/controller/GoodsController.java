package com.rkele.yxhshop.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.query.GoodsSearchQuery;
import com.rkele.yxhshop.goods.service.GoodsService;

import com.rkele.yxhshop.goods.vo.*;
import com.rkele.yxhshop.user.po.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.goods.api.GoodsApi;
import com.rkele.yxhshop.goods.po.Goods;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseApi<Goods> implements GoodsApi {
    @Autowired
    GoodsService goodsService;

    @GetMapping("/count")
    public Result count() {
        return this.count(new Goods().setDelete(false).setOnSale(true));
    }

    @GetMapping("/related")
    public Result<List<GoodsListVO>> queryRelatedGoods(@NotNull @RequestParam("id") Integer id) {
        return Result.success(goodsService.queryRelatedGoods(id));
    }

    /**
     * 新品首发
     *
     * @return
     */
    @GetMapping("/new")
    public Result<BannerInfoVO> newGoods() {
        BannerInfoVO bannerInfo = new BannerInfoVO();
        bannerInfo.setName("坚持初心，为你寻觅世间好物");
        bannerInfo.setImgUrl("http://yanxuan.nosdn.127.net/8976116db321744084774643a933c5ce.png");
        return Result.success(bannerInfo);
    }

    /**
     * 人气推荐
     *
     * @return
     */
    @GetMapping("/hot")
    public Result<BannerInfoVO> hotGoods() {
        BannerInfoVO bannerInfo = new BannerInfoVO();
        bannerInfo.setName("大家都在买的严选好物");
        bannerInfo.setImgUrl("http://yanxuan.nosdn.127.net/8976116db321744084774643a933c5ce.png");
        return Result.success(bannerInfo);
    }

    @GetMapping("/category")
    public Result<GoodsCategoryVO> queryGoodsCategory(@RequestParam("categoryId") @NotNull Integer categoryId) {
        return Result.success(goodsService.queryGoodsCategory(categoryId));
    }

    @GetMapping("/list")
    public Result<GoodsResultVO> queryGoodsPageInfo(GoodsSearchQuery goodsSearchQuery) {
        return Result.success(goodsService.queryList(goodsSearchQuery));
    }

    @GetMapping("/detail")
    public Result<GoodsDetailVO> queryGoodsDetail(@RequestParam("id") @NotNull Integer id,HttpServletRequest request) throws Exception {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        return Result.success(goodsService.queryGoodsDetail(id,user));
    }


}
