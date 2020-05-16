package com.rkele.yxhshop.goods.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.goods.dto.GoodsAttributeDTO;
import com.rkele.yxhshop.goods.po.GoodsAttribute;

import java.util.List;

public interface GoodsAttributeService extends IService<GoodsAttribute> {

    List<GoodsAttributeDTO> queryGoodsDetailAttributeByGoodsId(Integer goodsId);

}
