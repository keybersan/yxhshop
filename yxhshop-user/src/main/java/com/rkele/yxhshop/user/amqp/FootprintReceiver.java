package com.rkele.yxhshop.user.amqp;

import com.rkele.yxhshop.user.po.Footprint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import com.rkele.yxhshop.user.service.FootprintService;

/**
 * 用户足迹Receiver
 *
 * @author zhs@outlook.com
 */
//@Component
//@RabbitListener(queues = "weshop.topic.footprint")
public class FootprintReceiver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FootprintService footprintService;

    //@RabbitHandler
    public void process(Footprint footprint) {
        logger.info("process footprint message,message is:{}", footprint);
        footprintService.create(footprint);
    }

}
