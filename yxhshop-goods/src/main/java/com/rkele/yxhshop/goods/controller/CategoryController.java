package com.rkele.yxhshop.goods.controller;

import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.goods.po.Category;
import com.rkele.yxhshop.goods.po.Goods;
import com.rkele.yxhshop.goods.service.CategoryService;
import com.rkele.yxhshop.goods.service.GoodsService;
import com.rkele.yxhshop.goods.vo.CategoryIndexVO;
import com.rkele.yxhshop.goods.vo.CategoryVO;
import com.rkele.yxhshop.goods.vo.HomeCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.api.CategoryApi;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseApi<Category> implements CategoryApi {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    GoodsService goodsService;

    @Override
    public Result<List<Integer>> queryIdsByParentId(Integer parentId) {
        return Result.success(categoryService.queryIdsByParentId(parentId));
    }

    @Override
    public Result<List<Integer>> queryParentIdsByIdIn(List<Integer> ids) {
        return Result.success(categoryService.queryParentIdsByIdIn(ids));
    }

    @Override
    public Result<List<Category>> queryByIdIn(List<Integer> ids) {
        return Result.success(categoryService.queryByIdIn(ids));
    }
    @GetMapping("/index")
    public Result<CategoryIndexVO> index(Integer id) {
        return Result.success(categoryService.index(id));
    }

    @GetMapping("/current")
    public Result<CategoryVO> current(@NotNull @RequestParam("id") Integer id) {
        return Result.success(categoryService.current(id));
    }

    @GetMapping("/list")
    public Result<List<HomeCategoryVO>> list() {
        List<HomeCategoryVO> categoryList = new LinkedList<>();

        this.queryByCriteria(
            Criteria.of(Category.class).fields(Category::getId, Category::getName).andEqualTo(Category::getParentId, 0)
        ).getData().forEach(c -> {

            List<Integer> categoryIdList = this.queryByCriteria(Criteria.of(Category.class).fields(Category::getId).andEqualTo(Category::getParentId, c.getId())).getData().stream()
                .map(Category::getId)
                .collect(Collectors.toList());

            List<Goods> goodsList = goodsService.queryByCriteria(Criteria.of(Goods.class).fields(Goods::getId, Goods::getListPicUrl, Goods::getName, Goods::getRetailPrice).andIn(Goods::getCategoryId, categoryIdList).page(1, 3));
            categoryList.add(new HomeCategoryVO(c.getId(), c.getName(), goodsList));
        });
        return Result.success(categoryList);
    }
}
