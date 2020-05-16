package com.rkele.yxhshop.user.api;

import com.rkele.yxhshop.user.po.Region;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.fallback.RegionApiFallback;

@FeignClient(value = "yxhshop-user", path = "region", fallback = RegionApiFallback.class)
public interface RegionApi extends Api<Region> {

    @GetMapping("/queryNameById")
    Result<String> queryNameById(@RequestParam("id") Short id);
}
