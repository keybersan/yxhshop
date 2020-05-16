package com.rkele.yxhshop.goods.mapper;

import org.apache.ibatis.annotations.Param;
import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.goods.dto.GoodsSpecificationDTO;
import com.rkele.yxhshop.goods.po.GoodsSpecification;

import java.util.List;

public interface GoodsSpecificationMapper extends MyMapper<GoodsSpecification> {

    List<GoodsSpecificationDTO> selectGoodsDetailSpecificationByGoodsId(Integer goodsId);

    List<String> selectValueByGoodsIdAndIdIn(Integer goodsId, @Param("list") List<Integer> goodsSpecificationIds);
}
