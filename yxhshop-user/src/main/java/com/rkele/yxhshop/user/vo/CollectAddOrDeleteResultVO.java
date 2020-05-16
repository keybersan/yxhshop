package com.rkele.yxhshop.user.vo;

public class CollectAddOrDeleteResultVO {

    private boolean userHasCollect;

    public CollectAddOrDeleteResultVO() {
    }

    public CollectAddOrDeleteResultVO(boolean userHasCollect) {
        this.userHasCollect = userHasCollect;
    }

    public boolean isUserHasCollect() {
        return userHasCollect;
    }

    public void setUserHasCollect(boolean userHasCollect) {
        this.userHasCollect = userHasCollect;
    }
}
