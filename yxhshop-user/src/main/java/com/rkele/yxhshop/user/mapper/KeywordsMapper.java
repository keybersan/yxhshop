package com.rkele.yxhshop.user.mapper;


import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.user.po.Keywords;

import java.util.List;

public interface KeywordsMapper extends MyMapper<Keywords> {

    List<String> selectByKeywordLike(String keyword);
}
