package com.rkele.yxhshop.common.rocketmq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * Created by keybersan on 2019/12/27.
 */
public class FootPrintSendCallback implements SendCallback {
    @Override
    public void onSuccess(SendResult sendResult) {
        System.out.println("onSuccess"+ JSONObject.toJSONString(sendResult));
    }

    @Override
    public void onException(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
}
