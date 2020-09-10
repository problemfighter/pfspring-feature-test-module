package com.problemfighter.pfspring.webtestmodule.othertask.kafka.service;

import com.problemfighter.pfspring.webtestmodule.othertask.kafka.model.ProduceMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {


    @KafkaListener(topics = "bismillah", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory", autoStartup = "false")
    public void produceMessageListener(ProduceMessage produceMessage){
        System.out.println("Receive: " + produceMessage);
    }

}
