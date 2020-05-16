package com.rkele.yxhshop.user.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.user.po.Comment;
import com.rkele.yxhshop.user.query.CommentQuery;
import com.rkele.yxhshop.user.vo.CommentCountVO;
import com.rkele.yxhshop.user.vo.CommentResultVO;

import java.util.List;

/**
 * @author zhs@outlook.com
 */
public interface CommentService extends IService<Comment> {

    List<Comment> queryIfRequirePictureList(CommentQuery commentQuery);

    Integer countIfRequirePictureList(CommentQuery commentQuery);
    public List<CommentResultVO> queryList(CommentQuery commentQuery);
    public CommentCountVO countList(CommentQuery commentQuery);
}
