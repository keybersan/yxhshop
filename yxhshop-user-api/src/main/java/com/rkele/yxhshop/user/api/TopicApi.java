package com.rkele.yxhshop.user.api;

import com.rkele.yxhshop.user.fallback.TopicApiFallback;
import com.rkele.yxhshop.user.po.Topic;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;

@FeignClient(value = "yxhshop-user", path = "topic", fallback = TopicApiFallback.class)
public interface TopicApi extends Api<Topic> {
}
