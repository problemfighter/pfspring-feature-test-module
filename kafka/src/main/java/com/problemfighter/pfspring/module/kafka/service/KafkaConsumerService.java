package com.problemfighter.pfspring.module.kafka.service;

import com.problemfighter.pfspring.module.kafka.model.ProduceMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {


    @KafkaListener(topics = "bismillah", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory", autoStartup = "false")
    public void produceMessageListener(ProduceMessage produceMessage){
        System.out.println("Receive: " + produceMessage);
    }

}
