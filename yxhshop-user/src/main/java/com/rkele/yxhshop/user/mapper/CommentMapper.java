package com.rkele.yxhshop.user.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.user.po.Comment;
import com.rkele.yxhshop.user.query.CommentQuery;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;



public interface CommentMapper extends MyMapper<Comment> {

    /**
     * 查询有图片的评论
     *
     * @return
     */
    List<Comment> selectIfRequirePictureList(CommentQuery commentQuery, int offset, int limit);

    Integer countIfRequirePictureList(CommentQuery commentQuery);

}
