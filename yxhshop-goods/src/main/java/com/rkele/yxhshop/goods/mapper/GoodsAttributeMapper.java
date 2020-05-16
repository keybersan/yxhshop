package com.rkele.yxhshop.goods.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.goods.dto.GoodsAttributeDTO;
import com.rkele.yxhshop.goods.po.GoodsAttribute;

import java.util.List;

public interface GoodsAttributeMapper extends MyMapper<GoodsAttribute> {

    List<GoodsAttributeDTO> selectGoodsDetailAttributeByGoodsId(Integer goodsId);
}
