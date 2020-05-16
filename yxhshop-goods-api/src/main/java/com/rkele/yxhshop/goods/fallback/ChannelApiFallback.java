package com.rkele.yxhshop.goods.fallback;

import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.goods.api.ChannelApi;
import com.rkele.yxhshop.goods.po.Channel;

@Component
public class ChannelApiFallback extends ApiFallback<Channel> implements ChannelApi {

}
