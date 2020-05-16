package com.rkele.yxhshop.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.exception.YxhshopException;
import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.user.po.SearchHistory;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.service.SearchHistoryService;
import com.rkele.yxhshop.user.vo.SearchIndexVO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;
import com.rkele.yxhshop.user.mapper.KeywordsMapper;
import com.rkele.yxhshop.user.po.Keywords;
import com.rkele.yxhshop.user.service.KeywordsService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeywordsServiceImpl extends BaseService<Keywords> implements KeywordsService {

    @Autowired
    private KeywordsMapper keywordsMapper;
    @Autowired
    SearchHistoryService searchHistoryService;

    @Override
    public List<String> queryByKeyword(String keyword) {
        return keywordsMapper.selectByKeywordLike(keyword);
    }

    @Override
    public SearchIndexVO index(String token) {
        // 取出输入框默认的关键词
        Keywords defaultKeyword = this.queryOneByCriteria(Criteria.of(Keywords.class).andEqualTo(Keywords::getIsDefault, true).page(1, 1));
        // 取出热闹关键词
        List<Keywords> hotKeywordList = this.queryByCriteria(Criteria.of(Keywords.class).andEqualTo(Keywords::getHot, true).page(1, 10));
        List<String> historyKeywordList = Collections.emptyList();
        try {
            Claims claims = JwtHelper.parseJWT(token);
            User user  = JSONObject.parseObject(claims.getSubject(),User.class);
            historyKeywordList = searchHistoryService.queryByCriteria(Criteria.of(SearchHistory.class).andEqualTo(SearchHistory::getUserId, user.getId()).page(1, 10)).stream()
                .map(SearchHistory::getKeyword)
                .collect(Collectors.toList());
        } catch (YxhshopException e) {
            log.info("用户未登陆，不查询热闹关键词");
        }
        return new SearchIndexVO()
            .setDefaultKeyword(defaultKeyword)
            .setHotKeywordList(hotKeywordList)
            .setHistoryKeywordList(historyKeywordList);
    }
}
