package com.rkele.yxhshop.user.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.user.po.CommentPicture;

import java.util.List;

public interface CommentPictureService extends IService<CommentPicture> {

    List<String> queryPicUrlByCommentId(Integer commentId);

}
