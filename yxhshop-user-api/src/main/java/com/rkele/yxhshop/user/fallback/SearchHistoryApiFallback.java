package com.rkele.yxhshop.user.fallback;

import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.user.api.SearchHistoryApi;
import com.rkele.yxhshop.user.po.SearchHistory;

@Component
public class SearchHistoryApiFallback extends ApiFallback<SearchHistory> implements SearchHistoryApi {
}
