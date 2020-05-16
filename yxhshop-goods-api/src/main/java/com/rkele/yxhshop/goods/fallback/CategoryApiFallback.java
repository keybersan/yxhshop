package com.rkele.yxhshop.goods.fallback;

import com.rkele.yxhshop.goods.po.Category;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.api.CategoryApi;

import java.util.List;

@Component
public class CategoryApiFallback extends ApiFallback<Category> implements CategoryApi {
    @Override
    public Result<List<Integer>> queryIdsByParentId(Integer parentId) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<List<Integer>> queryParentIdsByIdIn(List<Integer> ids) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<List<Category>> queryByIdIn(List<Integer> ids) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
