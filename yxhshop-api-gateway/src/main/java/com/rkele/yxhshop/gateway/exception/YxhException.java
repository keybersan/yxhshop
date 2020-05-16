package com.rkele.yxhshop.gateway.exception;


import com.rkele.yxhshop.gateway.utils.ResultStatus;

public class YxhException extends YxhshopException {

    public YxhException(ResultStatus status) {
        super(status);

    }

}
