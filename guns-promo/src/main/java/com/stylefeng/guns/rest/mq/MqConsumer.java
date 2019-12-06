package com.stylefeng.guns.rest.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
public class MqConsumer {
    @Value("${mq.nameserver.address}")
    private String address;
    @Value("${mq.topic}")
    private String topic;
    @Value("${mq.comsumergroup}")
    private String groupName;

    private DefaultMQPushConsumer mqPushConsumer;

    @Autowired
    MtimePromoMapper mtimePromoMapper;

    @PostConstruct
    private void init() {
        mqPushConsumer = new DefaultMQPushConsumer();
        mqPushConsumer.setNamesrvAddr(address);
        mqPushConsumer.setConsumerGroup(groupName);
        try {
            mqPushConsumer.subscribe(topic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        mqPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = list.get(0);
                byte[] body = messageExt.getBody();
                String jsonString = new String(body);
                HashMap<String, Integer> hashMap = JSON.parseObject(jsonString, HashMap.class);
                Integer promoId = hashMap.get("promoId");
                Integer amount = hashMap.get("amount");
                Integer affectedRows = mtimePromoMapper.decreaseStock(promoId, amount);
                if (affectedRows < 1) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            mqPushConsumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
