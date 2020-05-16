package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.CommentPictureApi;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.po.CommentPicture;

import java.util.List;

@Component
public class CommentPictureApiFallback extends ApiFallback<CommentPicture> implements CommentPictureApi {
    @Override
    public Result<List<String>> queryPicUrlByCommentId(Integer commentId) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
