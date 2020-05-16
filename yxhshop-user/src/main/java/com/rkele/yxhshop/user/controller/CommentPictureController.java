package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.user.service.CommentPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.api.CommentPictureApi;
import com.rkele.yxhshop.user.po.CommentPicture;

import java.util.List;

@RestController
@RequestMapping("/commentPicture")
public class CommentPictureController extends BaseApi<CommentPicture> implements CommentPictureApi {

    @Autowired
    private CommentPictureService commentPictureService;

    @Override
    public Result<List<String>> queryPicUrlByCommentId(Integer commentId) {
        return Result.success(commentPictureService.queryPicUrlByCommentId(commentId));
    }
}
