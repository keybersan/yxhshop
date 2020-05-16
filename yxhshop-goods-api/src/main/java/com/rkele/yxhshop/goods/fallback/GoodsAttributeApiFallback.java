package com.rkele.yxhshop.goods.fallback;

import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.api.GoodsAttributeApi;
import com.rkele.yxhshop.goods.dto.GoodsAttributeDTO;
import com.rkele.yxhshop.goods.po.GoodsAttribute;

import java.util.List;

@Component
public class GoodsAttributeApiFallback extends ApiFallback<GoodsAttribute> implements GoodsAttributeApi {

    @Override
    public Result<List<GoodsAttributeDTO>> queryGoodsDetailAttributeByGoodsId(Integer goodsId) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
