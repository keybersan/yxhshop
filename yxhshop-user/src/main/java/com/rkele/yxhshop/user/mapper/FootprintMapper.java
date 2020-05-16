package com.rkele.yxhshop.user.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.user.po.Footprint;
import com.rkele.yxhshop.user.dto.GoodsFootprintDTO;

import java.util.List;

public interface FootprintMapper extends MyMapper<Footprint> {

    List<GoodsFootprintDTO> selectGoodsFootprintByUserId(Integer userId);

}
