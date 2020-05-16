package com.rkele.yxhshop.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.dto.GoodsFootprintDTO;
import com.rkele.yxhshop.user.fallback.FootprintApiFallback;
import com.rkele.yxhshop.user.po.Footprint;

import java.util.List;

@FeignClient(value = "yxhshop-user", path = "footprint", fallback = FootprintApiFallback.class)
public interface FootprintApi extends Api<Footprint> {

    @GetMapping("/queryGoodsFootprintByUserId")
    Result<List<GoodsFootprintDTO>> queryGoodsFootprintByUserId(@RequestParam("id") Integer userId);
}
