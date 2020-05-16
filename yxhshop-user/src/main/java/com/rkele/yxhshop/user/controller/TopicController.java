package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.user.api.TopicApi;
import com.rkele.yxhshop.user.po.Topic;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController extends BaseApi<Topic> implements TopicApi {

    @GetMapping
    public Result<Topic> query(Integer id) {
        return this.queryById(id);
    }

    @GetMapping("/related")
    public Result<List<Topic>> relatedTopic() {
        return this.queryByCriteria(Criteria.of(Topic.class).page(1,4));
    }

    @GetMapping("/list")
    public Result<List<Topic>> list(Topic topic, Integer pageSize, Integer pageNum) {
        //FIXME 此处需要条件查询
        Criteria<Topic, Object> criteria = Criteria.of(Topic.class).page(pageNum, pageSize);

        return this.queryByCriteria(criteria)
            .addExtra("total", this.countByCriteria(criteria));
    }
}
