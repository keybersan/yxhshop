package com.rkele.yxhshop.goods.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.goods.po.Category;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;


public interface CategoryMapper extends MyMapper<Category> {

    List<Integer> selectIdsByParentId(Integer parentId);

    List<Integer> selectParentIdsByIdIn(List<Integer> ids);

    List<Category> selectByIdIn(List<Integer> ids);

}
