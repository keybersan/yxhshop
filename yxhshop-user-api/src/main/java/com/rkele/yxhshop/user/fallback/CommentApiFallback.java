package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.CommentApi;
import com.rkele.yxhshop.user.po.Comment;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.query.CommentQuery;

import java.util.List;

@Component
public class CommentApiFallback extends ApiFallback<Comment> implements CommentApi {

    @Override
    public Result<List<Comment>> queryIfRequirePictureList(CommentQuery commentQuery) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<Integer> countIfRequirePictureList(CommentQuery commentQuery) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
