package com.wei.provider;

import com.alibaba.fastjson.JSON;
import com.wei.bo.User;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * *普通发送rocketMQ消息
 */
public class Provider {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        producer.setNamesrvAddr("localhost:9876");
        //订阅的实例名
        producer.setInstanceName("rmq-instance");
        producer.start();
        try {
            for (int i = 0; i < 100; i++) {
                User user = new User();
                user.setLoginName("用户名" + i);
                user.setPwd(String.valueOf(i));
                Message message = new Message("log-topic", "user-tag", JSON.toJSONString(user).getBytes());
                System.out.println("生产者发送消息:" + JSON.toJSONString(user));
                SendResult result = producer.send(message);
                System.err.println("发送响应:msgId" + result.getMsgId() + ",发送状态" + result.getSendStatus());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }


}

