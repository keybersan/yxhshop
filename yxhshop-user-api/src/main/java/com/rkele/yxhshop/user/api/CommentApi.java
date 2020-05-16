package com.rkele.yxhshop.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.fallback.CommentApiFallback;
import com.rkele.yxhshop.user.po.Comment;
import com.rkele.yxhshop.user.query.CommentQuery;

import java.util.List;

@FeignClient(value = "yxhshop-user", path = "comment", fallback = CommentApiFallback.class)
public interface CommentApi extends Api<Comment> {

    @GetMapping("/queryIfRequirePictureList")
    Result<List<Comment>> queryIfRequirePictureList(@RequestBody CommentQuery commentQuery);

    @GetMapping("/countIfRequirePictureList")
    Result<Integer> countIfRequirePictureList(@RequestBody CommentQuery commentQuery);

}
