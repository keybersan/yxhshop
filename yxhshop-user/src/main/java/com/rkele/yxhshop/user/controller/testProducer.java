package com.rkele.yxhshop.user.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.rkele.yxhshop.user.po.Footprint;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/12/16.
 */
@RestController
@RequestMapping("/mqpro")
public class testProducer {
    @Autowired
    private DefaultMQProducer defaultProducer;

    @Autowired
    private TransactionMQProducer transactionProducer;

    private int i = 0;

    /**
     * 订阅消息
     */
    //@RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
    @GetMapping("/sendMsg")
    public void sendMsg(@RequestParam(value="tags") String tags) {

        Message msg = new Message("TopicTest1", // topic
            tags, // tag
            "OrderID00" + i, // key
            ("Hello zebra mq=" + tags).getBytes());// body
        try {
            defaultProducer.send(msg, new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("onSuccess"+JSONObject.toJSONString(sendResult));
                    // TODO 发送成功处理
                }
                @Override
                public void onException(Throwable e) {
                    System.out.println(e);
                    // TODO 发送失败处理
                }
            });
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/sendFootprint")
    public void sendFootprint() {
        Footprint footprint = new Footprint()
            .setUserId(1)
            .setGoodsId(2);
        Message msg = new Message("footprintReceiver", // topic
            "footprint", // tag
            "footprint"+1+2 , // key
            JSONObject.toJSONString(footprint).getBytes());// body
        try {
            defaultProducer.send(msg, new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("onSuccess"+JSONObject.toJSONString(sendResult));
                    // TODO 发送成功处理
                }
                @Override
                public void onException(Throwable e) {
                    System.out.println(e);
                    // TODO 发送失败处理
                }
            });
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@RequestMapping(value = "/sendTransactionMsg", method = RequestMethod.GET)
    @GetMapping("/sendTransactionMsg")
    @Transactional
    public String sendTransactionMsg() {
        SendResult sendResult = null;
        try {


            // // TODO 由于社区版本的服务器阉割调了消息回查的功能，所以这个地方没有意义  使用transactionProducer.setTransactionListener();代替
            //服务器回调producer,检查本地事务分支成功还是失
            /*transactionProducer.setTransactionCheckListener(new TransactionCheckListener() {

                @Override
                public LocalTransactionState checkLocalTransactionState(MessageExt messageExt) {
                    System.out.println("state --" + new String(messageExt.getBody()));
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
            });
            //客户端回调
            // 发送事务消息，LocalTransactionExecute的executeLocalTransactionBranch方法中执行本地逻辑
            TransactionExecuterImpl tranExecuter = new TransactionExecuterImpl();
            sendResult = transactionProducer.sendMessageInTransaction(msg,tranExecuter,JSONObject.toJSON("{'id':2}"));//
            */
            System.out.println(transactionProducer);
            transactionProducer.setTransactionListener(new TransactionListener() {
                //执行本地事物
                @Override
                public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                    if("TagA".equals(message.getTags())){
                        return LocalTransactionState.COMMIT_MESSAGE;
                    }else if("TagB".equals(message.getTags())){
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                    }else {
                        return LocalTransactionState.UNKNOW;
                    }
                }

                @Override
                public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                    System.out.println("mq发起回查本地事物是否提交");
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
            });
            String[] tags = {"TagA","TagB","TagC"};
            for(int i=0;i<tags.length;i++){
                Message msg = new Message("TopicTest1", // topic
                    tags[i], // tag
                    "OrderID00"+i, // key
                    ("{'id':"+i+",'TAG':"+tags[i]+"}").getBytes());// body
                sendResult = transactionProducer.sendMessageInTransaction(msg,null);//对所有事物生产者进行事物控制，
                System.out.println(sendResult);
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendResult==null?"error":sendResult.toString();
    }

    //生成顺序消费消息
    @RequestMapping(value = "/sendMsgOrder", method = RequestMethod.GET)
    public void sendMsgOrder() {
        Message msg = new Message("TopicTest1", // topic
            "TagA", // tag
            "OrderID00" + i, // key
            ("Hello zebra mq" + i).getBytes());// body
        msg.setDelayTimeLevel(2);
        try {
            defaultProducer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    System.out.println("MessageQueue" + arg);
                    int index = ((Integer) arg) % mqs.size();
                    return mqs.get(index);
                }
            }, i);// i==arg
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
