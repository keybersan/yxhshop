package com.rkele.yxhshop.common.rocketmq;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rkele.yxhshop.common.config.RocketmqProperties;

import java.util.List;

/**
 * Created by Administrator on 2019/12/16.
 * producer和consumer初始化类
 */
@Configuration
@EnableConfigurationProperties(RocketmqProperties.class)
@ConditionalOnProperty(prefix = RocketmqProperties.PREFIX, value = "namesrvAddr")
public class RocketmqAutoConfiguration {
    private static final Logger log = LogManager.getLogger(RocketmqAutoConfiguration.class);
    @Autowired
    private RocketmqProperties properties;
    @Autowired
    private ApplicationEventPublisher publisher;

    private static boolean isFirstSub = true;

    private static long startTime = System.currentTimeMillis();

    /**
     * 初始化向rocketmq发送普通消息的生产者
     */
    @Bean
    @ConditionalOnProperty(prefix = RocketmqProperties.PREFIX, value = "producerInstanceName")
    @ConditionalOnBean(RocketmqAutoConfiguration.class)
    public DefaultMQProducer defaultProducer() throws MQClientException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer(properties.getProducerGroupName());
        producer.setNamesrvAddr(properties.getNamesrvAddr());
        producer.setInstanceName(properties.getProducerInstanceName());
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();
        log.info("RocketMq defaultProducer Started.");
        return producer;
    }


    /**
     * 初始化向rocketmq发送事务消息的生产者
     */
    @Bean
    @ConditionalOnProperty(prefix = RocketmqProperties.PREFIX, value = "producerTranInstanceName")
    @ConditionalOnBean(RocketmqAutoConfiguration.class)
    public TransactionMQProducer transactionProducer() throws MQClientException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        TransactionMQProducer producer = new TransactionMQProducer(properties.getTransactionProducerGroupName());
        producer.setNamesrvAddr(properties.getNamesrvAddr());
        producer.setInstanceName(properties.getProducerTranInstanceName());
        producer.setRetryTimesWhenSendAsyncFailed(10);

        // 事务回查最小并发数
        producer.setCheckThreadPoolMinSize(2);
        // 事务回查最大并发数
        producer.setCheckThreadPoolMaxSize(2);
        // 队列数
        producer.setCheckRequestHoldMax(2000);

        // TODO 由于社区版本的服务器阉割调了消息回查的功能，所以这个地方没有意义(不置可否)
         /*TransactionCheckListener transactionCheckListener = new TransactionCheckListenerImpl();
         producer.setTransactionCheckListener(transactionCheckListener);*/

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();

        System.out.println("RocketMq TransactionMQProducer Started.");
        System.out.println(producer);
        return producer;
    }

    /**
     * 初始化rocketmq消息监听方式的消费者
     */
    @Bean
    @ConditionalOnProperty(prefix = RocketmqProperties.PREFIX, value = "consumerInstanceName")
    @ConditionalOnBean(RocketmqAutoConfiguration.class)
    public DefaultMQPushConsumer pushConsumer() throws MQClientException {
        //broker 推的模式 DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(properties.getConsumerGroupName());
        consumer.setNamesrvAddr(properties.getNamesrvAddr());
        consumer.setInstanceName(properties.getConsumerInstanceName());
       /* if (properties.isConsumerBroadcasting()) {
            //指定消费者模式，广播模式或者负载均衡模式
            //默认为负载均衡模式
            //广播为每个消费者都可以消费一次；
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }*/
        consumer.setConsumeMessageBatchMaxSize(
            properties.getConsumerBatchMaxSize() == 0 ? 1 : properties.getConsumerBatchMaxSize());// 设置批量消费，以提升消费吞吐量，默认是1
        /**
         * 订阅指定topic下tags
         */
        List<String> subscribeList = properties.getSubscribe();
        for (String sunscribe : subscribeList) {
            consumer.subscribe(sunscribe.split(":")[0], sunscribe.split(":")[1]);
        }
        //不要用内部类进行监听，不然只会消费一次，然后消费者就 挂了……挂了……挂了…… 监听类要单独写
        if (properties.isEnableOrderConsumer()) {//顺序消费
           OrderMessageListener myMessageListener = new OrderMessageListener(publisher,properties,consumer,isFirstSub,startTime);
            consumer.registerMessageListener(myMessageListener);

        } else {
            //没想好怎么写
            DefaultMessageListener defaultMessageListener = new DefaultMessageListener(publisher,properties,consumer,isFirstSub,startTime);
            consumer.registerMessageListener(defaultMessageListener);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);// 延迟5秒再启动，主要是等待spring事件监听相关程序初始化完成，否则，回出现对RocketMQ的消息进行消费后立即发布消息到达的事件，然而此事件的监听程序还未初始化，从而造成消息的丢失
                    try {
                        consumer.start();
                    } catch (Exception e) {
                        log.info("RocketMq pushConsumer Start failure!!!.");
                        log.error(e.getMessage(), e);
                    }
                    log.info("RocketMq pushConsumer Started.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        return consumer;
    }



}
