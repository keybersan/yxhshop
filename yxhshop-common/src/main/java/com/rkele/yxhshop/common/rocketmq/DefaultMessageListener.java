package com.rkele.yxhshop.common.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationEventPublisher;
import com.rkele.yxhshop.common.config.RocketmqProperties;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2019/12/16.
 */
public class DefaultMessageListener implements MessageListenerConcurrently {
    private ApplicationEventPublisher publisher;
    private RocketmqProperties properties;
    private DefaultMQPushConsumer consumer;
    private boolean isFirstSub;
    private long startTime ;

    public DefaultMessageListener(ApplicationEventPublisher publisher,RocketmqProperties properties,

                                DefaultMQPushConsumer consumer,boolean isFirstSub,long startTime){
        this.publisher = publisher;
        this.properties = properties;
        this.consumer = consumer;
        this.isFirstSub = isFirstSub;
        this.startTime = startTime;
    }


    /**
     * 接受消息内容的方法
     * @param msgs
     * @param context
     * @return
     */
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            System.out.println("-------------------------===aaa-"+new String(msgs.get(0).getBody()));

            /*msgs=filter(msgs);
            if(msgs.size()==0) return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;*/
            //推送给@EventListener
            this.publisher.publishEvent(new RocketmqEvent(msgs, consumer));
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        // 如果没有return success，consumer会重复消费此信息，直到success。
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private List<MessageExt> filter(List<MessageExt> msgs){
        if(isFirstSub&&!properties.isEnableHisConsumer()){
            msgs =msgs.stream().filter(item ->startTime - item.getBornTimestamp() < 0).collect(Collectors.toList());
        }
        if(isFirstSub && msgs.size()>0){
            isFirstSub = false;
        }
        return msgs;
    }
}
