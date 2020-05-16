package com.rkele.yxhshop.goods.service.impl;

import com.rkele.yxhshop.goods.dto.GoodsAttributeDTO;
import com.rkele.yxhshop.goods.mapper.GoodsAttributeMapper;
import com.rkele.yxhshop.goods.service.GoodsAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;
import com.rkele.yxhshop.goods.po.GoodsAttribute;

import java.util.List;

@Service
public class GoodsAttributeServiceImpl extends BaseService<GoodsAttribute> implements GoodsAttributeService {

    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;

    @Override
    public List<GoodsAttributeDTO> queryGoodsDetailAttributeByGoodsId(Integer goodsId) {
        return goodsAttributeMapper.selectGoodsDetailAttributeByGoodsId(goodsId);
    }
}
