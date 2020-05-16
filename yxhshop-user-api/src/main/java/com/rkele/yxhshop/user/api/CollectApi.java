package com.rkele.yxhshop.user.api;

import com.rkele.yxhshop.user.fallback.CollectApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.dto.GoodsCollectDTO;
import com.rkele.yxhshop.user.po.Collect;

import java.util.List;

@FeignClient(value = "yxhshop-user", path = "collect", fallback = CollectApiFallback.class)
public interface CollectApi extends Api<Collect> {

    @GetMapping("/queryGoodsCollectList")
    Result<List<GoodsCollectDTO>> queryGoodsCollectList(Integer userId);
}
