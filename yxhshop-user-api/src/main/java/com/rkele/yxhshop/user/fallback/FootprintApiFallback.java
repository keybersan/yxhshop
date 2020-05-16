package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.FootprintApi;
import com.rkele.yxhshop.user.po.Footprint;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.dto.GoodsFootprintDTO;

import java.util.List;

@Component
public class FootprintApiFallback extends ApiFallback<Footprint> implements FootprintApi {

    @Override
    public Result<List<GoodsFootprintDTO>> queryGoodsFootprintByUserId(Integer userId) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
