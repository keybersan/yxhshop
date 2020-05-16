package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.RegionApi;
import com.rkele.yxhshop.user.po.Region;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Result;

@Component
public class RegionApiFallback extends ApiFallback<Region> implements RegionApi {
    @Override
    public Result<String> queryNameById(Short id) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
