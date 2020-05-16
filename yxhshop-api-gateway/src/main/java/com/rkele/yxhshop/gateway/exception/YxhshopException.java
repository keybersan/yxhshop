package com.rkele.yxhshop.gateway.exception;


import com.rkele.yxhshop.gateway.utils.ResultStatus;

public class YxhshopException extends RuntimeException {

    ResultStatus status;

	public YxhshopException(ResultStatus status) {
        //不生成栈追踪信息
        super(status.getMsg(), null, false, false);
        this.status = status;
    }


    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }
}
