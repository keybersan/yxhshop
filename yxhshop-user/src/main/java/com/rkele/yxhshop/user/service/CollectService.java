package com.rkele.yxhshop.user.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.user.dto.GoodsCollectDTO;
import com.rkele.yxhshop.user.po.Collect;
import com.rkele.yxhshop.user.vo.CollectAddOrDeleteParamVO;
import com.rkele.yxhshop.user.vo.CollectAddOrDeleteResultVO;

import java.util.List;

/**
 * @author zhs@outlook.com
 */
public interface CollectService extends IService<Collect> {

    List<GoodsCollectDTO> queryGoodsCollectList(Integer userId);
    public CollectAddOrDeleteResultVO addOrDelete(CollectAddOrDeleteParamVO dto,String token);

}
