package com.stylefeng.guns.rest.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
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
    MoocOrderTMapper moocOrderTMapper;
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
                MessageExt msg = list.get(0);
                byte[] body = msg.getBody();
                String s = new String(body);
                HashMap hashMap = JSON.parseObject(s, HashMap.class);
                String uuid = (String) hashMap.get("orderId");
                EntityWrapper<MoocOrderT> wrapper = new EntityWrapper<>();
                wrapper.eq("UUID",uuid);
                List<MoocOrderT> moocOrderTS = moocOrderTMapper.selectList(wrapper);
                if (moocOrderTS.size() == 0){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                MoocOrderT moocOrderT = moocOrderTS.get(0);
                if (!moocOrderT.getOrderStatus().equals(1)){
                    moocOrderT.setOrderStatus(2);
                    Integer update = moocOrderTMapper.update(moocOrderT, wrapper);
                    if (update < 1){
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
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
