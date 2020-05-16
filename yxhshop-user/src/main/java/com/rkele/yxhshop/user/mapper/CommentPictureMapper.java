package com.rkele.yxhshop.user.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.user.po.CommentPicture;

import java.util.List;

public interface CommentPictureMapper extends MyMapper<CommentPicture> {

    List<String> selectPicUrlByCommentId(Integer commentId);

}
