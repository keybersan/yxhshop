package com.rkele.yxhshop.user.fallback;

import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.api.CollectApi;
import com.rkele.yxhshop.user.dto.GoodsCollectDTO;
import com.rkele.yxhshop.user.po.Collect;

import java.util.List;

@Component
public class CollectApiFallback extends ApiFallback<Collect> implements CollectApi {

    @Override
    public Result<List<GoodsCollectDTO>> queryGoodsCollectList(Integer userId) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
