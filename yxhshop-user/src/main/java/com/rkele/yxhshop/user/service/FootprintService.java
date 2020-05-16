package com.rkele.yxhshop.user.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.user.dto.GoodsFootprintDTO;
import com.rkele.yxhshop.user.po.Footprint;
import com.rkele.yxhshop.user.po.User;

import java.util.List;

/**
 * @author zhs@outlook.com
 */
public interface FootprintService extends IService<Footprint> {

    List<GoodsFootprintDTO> queryGoodsFootprintByUserId(Integer userId);
    public List<List<GoodsFootprintDTO>> queryGoodsFootprintTimeLine(User user);

}
