package com.rkele.yxhshop.common.rocketmq;

import com.rkele.yxhshop.common.config.RocketmqProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2019/12/16.
 * 自定义消费者监听
 * 同一个队列顺序消费   一个队列只有一个线程
 */
public class OrderMessageListener implements MessageListenerOrderly {

    private ApplicationEventPublisher publisher;
    private RocketmqProperties properties;
    private DefaultMQPushConsumer consumer;
    private boolean isFirstSub;
    private long startTime ;

    public OrderMessageListener(ApplicationEventPublisher publisher,RocketmqProperties properties,
                                DefaultMQPushConsumer consumer,boolean isFirstSub,long startTime){
        this.publisher = publisher;
        this.properties = properties;
        this.consumer = consumer;
        this.isFirstSub = isFirstSub;
        this.startTime = startTime;
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        try {
            System.out.println("===aaa-"+msgs.get(0).getMsgId());
            //// 设置自动提交
            context.setAutoCommit(true);
            msgs =filter(msgs);
            if(msgs.size()==0) return ConsumeOrderlyStatus.SUCCESS;
            this.publisher.publishEvent(new RocketmqEvent(msgs, consumer));
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        // 如果没有return success，consumer会重复消费此信息，直到success。
        return ConsumeOrderlyStatus.SUCCESS;
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
