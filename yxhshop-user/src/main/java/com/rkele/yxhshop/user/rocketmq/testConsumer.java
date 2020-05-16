package com.rkele.yxhshop.user.rocketmq;

import com.rkele.yxhshop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.rocketmq.RocketmqEvent;

/**
 * Created by Administrator on 2019/12/16.
*/
@Component
public class testConsumer {
    @Autowired
    UserService userService;
    /**
     * 监听这个事件
     * @param event
     */
    @EventListener(condition = "#event.msgs[0].topic=='TopicTest1' && #event.msgs[0].tags=='TagA'")
    public void rocketmqMsgListen(RocketmqEvent event) {
//      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            System.out.println("com.guosen.client.controller.consumerDemo监听到一个消息达到：" +new String(event.getMsgs().get(0).getBody()));
            //userService.updateNotNull()
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
