package com.rkele.yxhshop.user.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.user.po.Keywords;
import com.rkele.yxhshop.user.vo.SearchIndexVO;

import java.util.List;

public interface KeywordsService extends IService<Keywords> {

    List<String> queryByKeyword(String keyword);
    public SearchIndexVO index(String token);
}
