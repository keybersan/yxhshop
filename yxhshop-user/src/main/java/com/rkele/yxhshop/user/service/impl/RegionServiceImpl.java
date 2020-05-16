package com.rkele.yxhshop.user.service.impl;

import com.rkele.yxhshop.user.mapper.RegionMapper;
import com.rkele.yxhshop.user.po.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.service.BaseService;
import com.rkele.yxhshop.user.service.RegionService;

/**
 * @author zhs@outlook.com
 */
@RestController
public class RegionServiceImpl extends BaseService<Region> implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public String queryNameById(Short id) {
        return regionMapper.selectNameById(id);
    }
}
