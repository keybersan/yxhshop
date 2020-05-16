package com.rkele.yxhshop.goods.service.impl;

import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.goods.mapper.CategoryMapper;
import com.rkele.yxhshop.goods.vo.CategoryIndexVO;
import com.rkele.yxhshop.goods.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;
import com.rkele.yxhshop.goods.po.Category;
import com.rkele.yxhshop.goods.service.CategoryService;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhs@outlook.com
 */
@Service
public class CategoryServiceImpl extends BaseService<Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Integer> queryIdsByParentId(Integer parentId) {
        return categoryMapper.selectIdsByParentId(parentId);
    }

    @Override
    public List<Integer> queryParentIdsByIdIn(List<Integer> ids) {
        return categoryMapper.selectParentIdsByIdIn(ids);
    }

    @Override
    public List<Category> queryByIdIn(List<Integer> ids) {
        return categoryMapper.selectByIdIn(ids);
    }

    @Override
    public CategoryIndexVO index(Integer categoryId) {
        List<CategoryVO> categoryList = new LinkedList<>();
        this.queryByCriteria(Criteria.of(Category.class).andEqualTo(Category::getParentId, 0).page(1, 10)).forEach(c -> {
            CategoryVO categoryDTO = new CategoryVO(c);
            List<Category> subCategoryList = this.queryList(new Category().setParentId(c.getId()));
            categoryDTO.setSubCategoryList(subCategoryList);
            categoryList.add(categoryDTO);
        });

        CategoryVO currentCategory;
        if (categoryId == null) {
            currentCategory = categoryList.get(0);
        } else {
            currentCategory = new CategoryVO(this.queryById(categoryId));
        }
        return new CategoryIndexVO(categoryList, currentCategory);
    }

    @Override
    public CategoryVO current(Integer id) {
        CategoryVO categoryDTO = new CategoryVO(this.queryById(id));
        List<Category> subCategoryList = this.queryList(new Category().setParentId(id));
        categoryDTO.setSubCategoryList(subCategoryList);
        return categoryDTO;
    }
}
