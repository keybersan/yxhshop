package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.po.Topic;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.user.api.TopicApi;

/**
 * @author zhs
 */
@Component
public class TopicApiFallback extends ApiFallback<Topic> implements TopicApi {
}
