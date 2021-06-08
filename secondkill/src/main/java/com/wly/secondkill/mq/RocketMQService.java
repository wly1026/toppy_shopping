package com.wly.secondkill.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RocketMQService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic,String body) throws Exception{
        Message message = new Message(topic,body.getBytes());
        DefaultMQProducer producer = rocketMQTemplate.getProducer();
        producer.setSendMsgTimeout(30000);
        producer.send(message);

    }

    public void sendDelayMessage(String topic, String body, int delayTimeLevel) throws Exception {
        Message message = new Message(topic, body.getBytes());
        message.setDelayTimeLevel(delayTimeLevel);
        rocketMQTemplate.getProducer().send(message);
    }
}
