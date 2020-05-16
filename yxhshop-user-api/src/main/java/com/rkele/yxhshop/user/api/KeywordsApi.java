package com.rkele.yxhshop.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.fallback.KeywordsApiFallback;
import com.rkele.yxhshop.user.po.Keywords;

import java.util.List;

@FeignClient(value = "yxhshop-user", path = "keywords", fallback = KeywordsApiFallback.class)
public interface KeywordsApi extends Api<Keywords> {

    @GetMapping("/queryByKeyword")
    Result<List<String>> queryByKeyword(@RequestParam("keyword") String keyword);
}
