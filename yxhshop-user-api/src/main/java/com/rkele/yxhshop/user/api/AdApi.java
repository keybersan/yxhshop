package com.rkele.yxhshop.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.user.fallback.AdApiFallback;
import com.rkele.yxhshop.user.po.Ad;

@FeignClient(value = "yxhshop-user", path = "ad", fallback = AdApiFallback.class)
public interface AdApi extends Api<Ad> {
}
