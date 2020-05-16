package com.rkele.yxhshop.goods.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.goods.dto.GoodsSpecificationDTO;
import com.rkele.yxhshop.goods.po.GoodsSpecification;

import java.util.List;

public interface GoodsSpecificationService extends IService<GoodsSpecification> {

    List<GoodsSpecificationDTO> queryGoodsDetailSpecificationByGoodsId(Integer goodsId);

    List<String> queryValueByGoodsIdAndIdIn(Integer goodsId, List<Integer> goodsSpecificationIds);
}
