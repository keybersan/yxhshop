package com.rkele.yxhshop.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.fallback.CommentPictureApiFallback;
import com.rkele.yxhshop.user.po.CommentPicture;

import java.util.List;

@FeignClient(value = "yxhshop-user", path = "commentPicture", fallback = CommentPictureApiFallback.class)
public interface CommentPictureApi extends Api<CommentPicture> {

    @GetMapping("/queryPicUrlByCommentId")
    Result<List<String>> queryPicUrlByCommentId(@RequestParam("commentId") Integer commentId);

}
