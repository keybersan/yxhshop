package com.rkele.yxhshop.user.vo;

import com.rkele.yxhshop.user.po.Comment;
import com.rkele.yxhshop.user.po.User;

import javax.validation.constraints.NotNull;
import java.util.Base64;

public class CommentPostVO {

    @NotNull
    private Byte typeId;

    @NotNull
    private Integer valueId;

    /**
     * 储存为base64编码
     */
    @NotNull
    private String content;

    @NotNull
    private Integer userId;

    public Comment toPO(Integer userId) {
        Comment comment = new Comment();
        comment.setTypeId(typeId);
        comment.setValueId(valueId);
        comment.setContent(Base64.getEncoder().encodeToString(content.getBytes()));
        comment.setUserId(userId);
        return comment;
    }

    public Byte getTypeId() {
        return typeId;
    }

    public CommentPostVO setTypeId(Byte typeId) {
        this.typeId = typeId;
        return this;
    }

    public Integer getValueId() {
        return valueId;
    }

    public CommentPostVO setValueId(Integer valueId) {
        this.valueId = valueId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentPostVO setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public CommentPostVO setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

}
