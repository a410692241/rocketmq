package com.wei.controller;


import com.wei.config.ProducerConfig;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class RocketController {

    @Autowired
    private DefaultMQProducer producer;

    @Autowired
    private ProducerConfig rocketmqConfig;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/send")
    public void sendMsg(String msg, String group) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message(group,"Tag1",msg.getBytes());
        //5秒就可以发完10000条
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        for (int i = 0; i < 10000; i++) {
//            producer.send(message);
//        }
//        stopWatch.stop();
//        long lastTaskTimeMillis = stopWatch.getTotalTimeMillis();
//        System.out.println(lastTaskTimeMillis);
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("发送成功!");
            }

            @Override
            public void onException(Throwable throwable) {
                logger.info("发送失败");
            }
        });

    }
}
