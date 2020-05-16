package com.rkele.yxhshop.user.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.user.po.Region;

public interface RegionMapper extends MyMapper<Region> {

    String selectNameById(Short id);
}
