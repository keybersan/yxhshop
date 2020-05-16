package com.rkele.yxhshop.user.rocketmq;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.rocketmq.RocketmqEvent;
import com.rkele.yxhshop.user.po.Footprint;
import com.rkele.yxhshop.user.service.FootprintService;

/**
 * Created by keybersan on 2019/12/26.
 */
@Component
public class FootprintReceiverConsumer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FootprintService footprintService;
    /**
     * 监听这个时间footprintReceiver
     * @param event footprint
     */
    @EventListener(condition = "#event.msgs[0].topic=='footprintReceiver' && #event.msgs[0].tags=='footprint'")
    public void rocketmqMsgListen(RocketmqEvent event) {
        //DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            //一次可消费多个消息
            System.out.println("FootprintReceiverConsumer监听到一个消息达到：" + new String(event.getMsgs().get(0).getBody()));
            Footprint footprint = JSONObject.parseObject(new String(event.getMsgs().get(0).getBody()),Footprint.class);
            footprintService.create(footprint);
            //userService.updateNotNull()
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
