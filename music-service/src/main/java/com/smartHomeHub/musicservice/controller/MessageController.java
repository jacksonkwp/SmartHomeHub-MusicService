package com.smartHomeHub.musicservice.controller;

import com.smartHomeHub.musicservice.event.KafkaProducer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private final KafkaProducer kafkaProducer;

    @GetMapping("/send")
    public String sendMessageToKafka(@RequestParam String message) {
        kafkaProducer.sendMessage("test", "test", message);
        return "Message sent to Kafka: " + message;
    }
}

