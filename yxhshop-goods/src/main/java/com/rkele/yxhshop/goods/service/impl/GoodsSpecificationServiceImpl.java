package com.rkele.yxhshop.goods.service.impl;

import com.rkele.yxhshop.goods.dto.GoodsSpecificationDTO;
import com.rkele.yxhshop.goods.mapper.GoodsSpecificationMapper;
import com.rkele.yxhshop.goods.po.GoodsSpecification;
import com.rkele.yxhshop.goods.service.GoodsSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;

import java.util.List;

@Service
public class GoodsSpecificationServiceImpl extends BaseService<GoodsSpecification> implements GoodsSpecificationService {

    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Override
    public List<GoodsSpecificationDTO> queryGoodsDetailSpecificationByGoodsId(Integer goodsId) {
        return goodsSpecificationMapper.selectGoodsDetailSpecificationByGoodsId(goodsId);
    }

    @Override
    public List<String> queryValueByGoodsIdAndIdIn(Integer goodsId, List<Integer> goodsSpecificationIds) {
        return goodsSpecificationMapper.selectValueByGoodsIdAndIdIn(goodsId, goodsSpecificationIds);
    }
}
