package com.stylefeng.guns.rest.mq;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.rest.common.persistence.StockLogStatus;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.rest.service.PromoService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Value("${mq.transactionproducergroup}")
    private String groupName;

    @Autowired
    MtimePromoMapper mtimePromoMapper;

    @Autowired
    PromoService promoService;

    private TransactionMQProducer producer;

    @PostConstruct
    private void init() {
        producer = new TransactionMQProducer(groupName);
        producer.setNamesrvAddr(address);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object args) {
                try {
                    promoService.createPromoOrder((HashMap) args);
                } catch (Exception e) {
                    e.printStackTrace();
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                byte[] body = messageExt.getBody();
                String jsonString = new String(body);
                HashMap hashMap = JSON.parseObject(jsonString, HashMap.class);
                String stockLogId = (String) hashMap.get("stockLogId");
                Integer status = promoService.getStatusByStockLogId(stockLogId);
                if (StockLogStatus.FAIL.getCode() == status) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                if (StockLogStatus.SUCCESS.getCode() == status) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.UNKNOW;
            }
        });
    }

    public Boolean sendStockMessageInTransaction(Integer promoId, Integer amount, Integer userId, String stockLogId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("promoId", promoId);
        hashMap.put("amount", amount);
        hashMap.put("userId", userId);
        hashMap.put("stockLogId", stockLogId);
        String jsonString = JSON.toJSONString(hashMap);
        Message message = new Message(topic, jsonString.getBytes(Charset.forName("utf-8")));
        TransactionSendResult transactionSendResult = null;
        try {
            transactionSendResult = producer.sendMessageInTransaction(message, hashMap);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        if (transactionSendResult == null) {
            return false;
        }
        LocalTransactionState localTransactionState = transactionSendResult.getLocalTransactionState();
        return LocalTransactionState.COMMIT_MESSAGE.equals(localTransactionState);
    }
}
