package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.KeywordsApi;
import com.rkele.yxhshop.user.po.Keywords;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Result;

import java.util.List;

@Component
public class KeywordsApiFallback extends ApiFallback<Keywords> implements KeywordsApi {
    @Override
    public Result<List<String>> queryByKeyword(String keyword) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
