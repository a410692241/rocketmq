package com.wei.config;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Configuration
@Log4j2
public class ConsumerConfigure implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConsumerConfig consumerConfig;

    // 开启消费者监听服务
    public void listener(String topic, String tag) throws MQClientException {
        log.info("开启" + topic + ":" + tag + "消费者-------------------");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(topic);
        consumer.setNamesrvAddr(consumerConfig.getNamesrvAddr());
        consumer.subscribe(topic, tag);
        // 开启内部类实现监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                log.info("topic:" + context.getMessageQueue() + "接收到数据");
                return ConsumerConfigure.this.dealBody(msgs);
            }
        });

        consumer.start();

        log.info("rocketmq启动成功---------------------------------------");

    }

    public ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs) {
        for (MessageExt msg : msgs) {
            try {
                String msgStr = new String(msg.getBody(), "utf-8");
                log.info("接收到的消息----->" + msgStr);
            } catch (UnsupportedEncodingException e) {
                log.error("body转字符串解析失败");
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            this.listener("orderTopic", "Tag1");
            this.listener("TopicTest", "Tag1");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}