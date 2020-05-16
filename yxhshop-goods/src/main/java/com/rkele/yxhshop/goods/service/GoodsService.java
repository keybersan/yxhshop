package com.rkele.yxhshop.goods.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.goods.po.Goods;
import com.rkele.yxhshop.goods.query.GoodsSearchQuery;
import com.rkele.yxhshop.goods.vo.GoodsCategoryVO;
import com.rkele.yxhshop.goods.vo.GoodsDetailVO;
import com.rkele.yxhshop.goods.vo.GoodsListVO;
import com.rkele.yxhshop.goods.vo.GoodsResultVO;
import com.rkele.yxhshop.user.po.User;

import java.util.List;

/**
 * @author zhs@outlook.com
 */
public interface GoodsService extends IService<Goods> {

    List<Goods> queryListByCategoryIdIn(List<Integer> categoryIdList);

    GoodsResultVO queryList(GoodsSearchQuery goodsSearchQuery);

    GoodsDetailVO queryGoodsDetail(Integer goodsId,User userInfo);

    List<GoodsListVO> queryRelatedGoods(Integer goodsId);

    GoodsCategoryVO queryGoodsCategory(Integer categoryId);

}
