package com.rkele.yxhshop.goods.api;

import com.rkele.yxhshop.goods.fallback.CategoryApiFallback;
import com.rkele.yxhshop.goods.po.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;

import java.util.List;

/**
 * @author zhs@outlook.com
 */
@FeignClient(value = "yxhshop-goods", path = "category", fallback = CategoryApiFallback.class)
public interface CategoryApi extends Api<Category> {

    @GetMapping("/queryIdsByParentId")
    Result<List<Integer>> queryIdsByParentId(@RequestBody Integer parentId);

    @GetMapping("/queryParentIdsByIdIn")
    Result<List<Integer>> queryParentIdsByIdIn(@RequestBody List<Integer> ids);

    @GetMapping("/queryByIdIn")
    Result<List<Category>> queryByIdIn(@RequestBody List<Integer> ids);

}
