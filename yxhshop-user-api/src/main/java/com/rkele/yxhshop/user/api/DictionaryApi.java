package com.rkele.yxhshop.user.api;

import com.rkele.yxhshop.user.po.Dictionary;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;

import com.rkele.yxhshop.user.fallback.DictionaryApiFallback;

/**
 * Created by keybersan on 2020/2/9.
 */
@FeignClient(value = "yxhshop-user", path = "dictionary", fallback = DictionaryApiFallback.class)
public interface DictionaryApi extends Api<Dictionary> {
}
