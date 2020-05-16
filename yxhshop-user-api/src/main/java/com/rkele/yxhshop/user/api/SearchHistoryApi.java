package com.rkele.yxhshop.user.api;

import com.rkele.yxhshop.user.fallback.SearchHistoryApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.user.po.SearchHistory;

@FeignClient(value = "yxhshop-user", path = "searchHistory", fallback = SearchHistoryApiFallback.class)
public interface SearchHistoryApi extends Api<SearchHistory> {
}
