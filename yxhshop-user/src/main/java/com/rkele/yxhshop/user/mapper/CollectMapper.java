package com.rkele.yxhshop.user.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.user.dto.GoodsCollectDTO;
import com.rkele.yxhshop.user.po.Collect;

import java.util.List;

public interface CollectMapper extends MyMapper<Collect> {

    List<GoodsCollectDTO> selectGoodsCollectByUserId(Integer userId);

}
