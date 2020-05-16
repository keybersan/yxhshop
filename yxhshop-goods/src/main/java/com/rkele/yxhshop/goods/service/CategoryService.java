package com.rkele.yxhshop.goods.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.goods.po.Category;
import com.rkele.yxhshop.goods.vo.CategoryIndexVO;
import com.rkele.yxhshop.goods.vo.CategoryVO;

import java.util.List;

/**
 * @author zhs@outlook.com
 */
public interface CategoryService extends IService<Category> {

    List<Integer> queryIdsByParentId(Integer parentId);

    List<Integer> queryParentIdsByIdIn(List<Integer> ids);

    List<Category> queryByIdIn(List<Integer> ids);
    public CategoryIndexVO index(Integer categoryId);
    public CategoryVO current(Integer id);
}
