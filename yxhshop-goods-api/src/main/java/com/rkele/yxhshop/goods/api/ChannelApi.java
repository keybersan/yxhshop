package com.rkele.yxhshop.goods.api;

import com.rkele.yxhshop.goods.fallback.ChannelApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.goods.po.Channel;

@FeignClient(value = "yxhshop-goods", path = "channel", fallback = ChannelApiFallback.class)
public interface ChannelApi extends Api<Channel> {

}
