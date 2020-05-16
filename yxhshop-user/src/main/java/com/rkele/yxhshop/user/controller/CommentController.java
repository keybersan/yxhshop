package com.rkele.yxhshop.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.user.api.CommentApi;
import com.rkele.yxhshop.user.po.Comment;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.service.CommentService;
import com.rkele.yxhshop.user.vo.CommentCountVO;
import com.rkele.yxhshop.user.vo.CommentPostVO;
import com.rkele.yxhshop.user.vo.CommentResultVO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.user.query.CommentQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseApi<Comment> implements CommentApi {

    @Autowired
    private CommentService commentService;

    @Override
    public Result<List<Comment>> queryIfRequirePictureList(CommentQuery commentQuery) {
        return Result.success(commentService.queryIfRequirePictureList(commentQuery));
    }

    @Override
    public Result<Integer> countIfRequirePictureList(CommentQuery commentQuery) {
        return Result.success(commentService.countIfRequirePictureList(commentQuery));
    }

    @GetMapping("/list")
    public Result<List<CommentResultVO>> queryList(@Validated CommentQuery commentQuery) {
        return Result.success(commentService.queryList(commentQuery));
    }

    @GetMapping("/count")
    public Result<CommentCountVO> countList(@Validated CommentQuery commentQuery) {
        return Result.success(commentService.countList(commentQuery));
    }

    @PostMapping("post")
    public Result postComment(@RequestBody @Validated CommentPostVO commentPostDTO, HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        return this.create(commentPostDTO.toPO(user.getId()));
    }
}
