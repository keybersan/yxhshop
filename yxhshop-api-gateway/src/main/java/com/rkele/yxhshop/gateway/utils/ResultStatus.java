package com.rkele.yxhshop.gateway.utils;

/**
 * 返回码，子系统继承实现自己的业务码
 *
 * @author zhs
 */
public interface ResultStatus {
    /**
     * 错误码
     */
    String getCode();

    /**
     * 错误信息
     */
    String getMsg();
}
