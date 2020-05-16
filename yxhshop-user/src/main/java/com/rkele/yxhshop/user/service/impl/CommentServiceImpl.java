package com.rkele.yxhshop.user.service.impl;

import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.api.CommentPictureApi;
import com.rkele.yxhshop.user.api.UserApi;
import com.rkele.yxhshop.user.po.Comment;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.service.CommentPictureService;
import com.rkele.yxhshop.user.service.UserService;
import com.rkele.yxhshop.user.vo.CommentCountVO;
import com.rkele.yxhshop.user.vo.CommentResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;
import com.rkele.yxhshop.user.mapper.CommentMapper;
import com.rkele.yxhshop.user.query.CommentQuery;
import com.rkele.yxhshop.user.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhs@outlook.com
 */
@Service
public class CommentServiceImpl extends BaseService<Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    CommentPictureService commentPictureService;

    @Autowired
    UserService userService;

    @Override
    public List<Comment> queryIfRequirePictureList(CommentQuery commentQuery) {
        int limit = commentQuery.getPageSize();
        int offset = (commentQuery.getPageNum() - 1) * limit;
        return commentMapper.selectIfRequirePictureList(commentQuery, offset, limit);
    }

    @Override
    public Integer countIfRequirePictureList(CommentQuery commentQuery) {
        return commentMapper.countIfRequirePictureList(commentQuery);
    }

    @Override
    public List<CommentResultVO> queryList(CommentQuery commentQuery) {
        List<Comment> commentRes =  this.queryIfRequirePictureList(commentQuery);
        if(commentRes==null){
            return null;
        }
        List<CommentResultVO> commentResultList = commentRes.stream()
            .map(CommentResultVO::new)  //map 将对象转为其他对象
            .collect(Collectors.toList());
        for (CommentResultVO commentResultDTO : commentResultList) {
            commentResultDTO.setPicList(commentPictureService.queryPicUrlByCommentId(commentResultDTO.getId()));
            User user = Optional.ofNullable(userService.queryById(commentResultDTO.getUserId())).orElseGet(() -> new User());
            commentResultDTO.setUserInfo(new CommentResultVO.UserInfoVO(user));
        }
        return commentResultList;
    }

    @Override
    public CommentCountVO countList(CommentQuery commentQuery) {
        Integer hasPicCount = this.countIfRequirePictureList(commentQuery.setRequirePicture(true));
        Integer allCount = this.countIfRequirePictureList(commentQuery.setRequirePicture(false));
        return new CommentCountVO(allCount, hasPicCount);
    }
}
