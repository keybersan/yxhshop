package com.rkele.yxhshop.user.service.impl;

import com.rkele.yxhshop.user.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;
import com.rkele.yxhshop.user.dto.GoodsFootprintDTO;
import com.rkele.yxhshop.user.mapper.FootprintMapper;
import com.rkele.yxhshop.user.po.Footprint;
import com.rkele.yxhshop.user.service.FootprintService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhs@outlook.com
 */
@Service
public class FootprintServiceImpl extends BaseService<Footprint> implements FootprintService {

    @Autowired
    private FootprintMapper footprintMapper;

    @Override
    public List<GoodsFootprintDTO> queryGoodsFootprintByUserId(Integer userId) {
        return footprintMapper.selectGoodsFootprintByUserId(userId);
    }

    @Override
    public List<List<GoodsFootprintDTO>> queryGoodsFootprintTimeLine(User user) {

        List<GoodsFootprintDTO> goodsFootprintList = this.queryGoodsFootprintByUserId(user.getId());

        return goodsFootprintList.stream()
            .collect(Collectors.groupingBy(gf -> gf.getCreateTime()))
            .entrySet()
            .stream()
            .sorted((e1, e2) -> {
                Long d1 = e1.getKey().toEpochDay();
                Long d2 = e2.getKey().toEpochDay();
                return d2.compareTo(d1);
            }).map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }
}
