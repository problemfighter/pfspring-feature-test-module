package com.problemfighter.pfspring.webtestmodule.othertask.kafka.controller;


import com.problemfighter.pfspring.webtestmodule.othertask.kafka.model.ProduceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/kafka")
@RestController
public class KafkaMessagingController {

    @Autowired
    private KafkaTemplate<String, ProduceMessage> kafkaTemplate;

    private static final String TOPIC = "bismillah";


    @GetMapping("/produce-message")
    public String kafkaProduceMessage(){
        kafkaTemplate.send(TOPIC, new ProduceMessage(1, "Bismillah", "Bismillahir Rahmanir Rahim"));
        return "Send the message";
    }


}
