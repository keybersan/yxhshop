package com.rkele.yxhshop.common.exception;


import com.rkele.yxhshop.common.utils.ResultStatus;

public class YxhException extends YxhshopException {

    public YxhException(ResultStatus status) {
        super(status);
    }
}
