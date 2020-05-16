package com.rkele.yxhshop.user.service.impl;

import com.rkele.yxhshop.user.mapper.CommentPictureMapper;
import com.rkele.yxhshop.user.po.CommentPicture;
import com.rkele.yxhshop.user.service.CommentPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;

import java.util.List;

@Service
public class CommentPictureServiceImpl extends BaseService<CommentPicture> implements CommentPictureService {

    @Autowired
    private CommentPictureMapper commentPictureMapper;

    @Override
    public List<String> queryPicUrlByCommentId(Integer commentId) {
        return commentPictureMapper.selectPicUrlByCommentId(commentId);
    }
}
