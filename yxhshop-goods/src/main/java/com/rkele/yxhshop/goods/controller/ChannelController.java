package com.rkele.yxhshop.goods.controller;

import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.api.ChannelApi;
import com.rkele.yxhshop.goods.po.Channel;
import com.rkele.yxhshop.user.po.Ad;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;

import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChannelController extends BaseApi<Channel> implements ChannelApi {

    @GetMapping("/list")
    public Result<List<Channel>> queryGoodsPageInfo() {
        return this.queryByCriteria(Criteria.of(Channel.class).fields(Channel::getId, Channel::getIconUrl, Channel::getName, Channel::getUrl).sort(Channel::getSortOrder));
    }
}
