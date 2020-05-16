package com.rkele.yxhshop.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.user.mapper.CollectMapper;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.vo.CollectAddOrDeleteParamVO;
import com.rkele.yxhshop.user.vo.CollectAddOrDeleteResultVO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.service.BaseService;
import com.rkele.yxhshop.user.dto.GoodsCollectDTO;
import com.rkele.yxhshop.user.po.Collect;
import com.rkele.yxhshop.user.service.CollectService;

import java.util.List;

/**
 * @author zhs@outlook.com
 */
@RestController
public class CollectServiceImpl extends BaseService<Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    public List<GoodsCollectDTO> queryGoodsCollectList(Integer userId) {
        return collectMapper.selectGoodsCollectByUserId(userId);
    }

    @Override
    public CollectAddOrDeleteResultVO addOrDelete(CollectAddOrDeleteParamVO dto,String token) {

        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        List<Collect> data = this.queryByCriteria(
            Criteria.of(Collect.class).andEqualTo(Collect::getTypeId, dto.getTypeId())
                .andEqualTo(Collect::getValueId, dto.getValueId())
                .andEqualTo(Collect::getUserId, user.getId()));
        //添加收藏
        if (data.size() == 0) {
            this.create(new Collect()
                .setTypeId(dto.getTypeId())
                .setValueId(dto.getValueId())
                .setUserId(user.getId()));
            return new CollectAddOrDeleteResultVO(true);
        } else {
            this.delete(new Collect()
                .setTypeId(dto.getTypeId())
                .setValueId(dto.getValueId())
                .setUserId(user.getId()));
            return new CollectAddOrDeleteResultVO(false);
        }
    }
}
