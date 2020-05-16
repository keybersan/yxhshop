package com.rkele.yxhshop.order.exception;

import com.rkele.yxhshop.common.exception.YxhshopException;
import com.rkele.yxhshop.common.utils.ResultStatus;

public class WeshopPayException extends YxhshopException {

    public WeshopPayException(ResultStatus status) {
        super(status);
    }
}
