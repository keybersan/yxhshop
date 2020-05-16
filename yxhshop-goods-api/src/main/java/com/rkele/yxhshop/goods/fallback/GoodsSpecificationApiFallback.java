package com.rkele.yxhshop.goods.fallback;

import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.api.GoodsSpecificationApi;
import com.rkele.yxhshop.goods.dto.GoodsSpecificationDTO;
import com.rkele.yxhshop.goods.po.GoodsSpecification;

import java.util.List;

@Component
public class GoodsSpecificationApiFallback extends ApiFallback<GoodsSpecification> implements GoodsSpecificationApi {
    @Override
    public Result<List<GoodsSpecificationDTO>> queryGoodsDetailSpecificationByGoodsId(Integer goodsId) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<List<String>> queryValueByGoodsIdAndIdIn(Integer goodsId, List<Integer> goodsSpecificationIds) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
