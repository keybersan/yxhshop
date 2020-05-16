package com.rkele.yxhshop.goods.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.goods.po.Goods;

import java.util.List;

public interface GoodsMapper extends MyMapper<Goods> {

    List<Goods> selectByIdIn(List<Integer> ids);
}
