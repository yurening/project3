package com.stylefeng.guns.rest.mq;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;

@Component
public class MqProducer {
    @Value("${mq.nameserver.address}")
    private String address;
    @Value("${mq.topic}")
    private String topic;
    @Value("${mq.producergroup}")
    private String groupName;

    private DefaultMQProducer producer;

    @PostConstruct
    private void init() {
        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(address);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(MoocOrderT moocOrderT) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("orderId",moocOrderT.getUuid());
        String s = JSON.toJSONString(hashMap);
        Message message = new Message(topic,s.getBytes(Charset.forName("utf-8")));
        message.setDelayTimeLevel(3);
        try {
            SendResult send = producer.send(message);
            System.out.println(send);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
