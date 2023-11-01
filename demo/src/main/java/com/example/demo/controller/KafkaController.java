package com.example.demo.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
	
	@Autowired 
	KafkaTemplate<String, String> kafkaTemplate;
	
	private final String topicName = "cdc";
	
	@GetMapping("/publish/{message}")
    public String publishMessage(@PathVariable("message") final String message) {
        // Sending the message
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
		
		try {
			SendResult<String, String> result = future.get();
			return result.toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
        return "Sth got wrong...";
    }
	
	@KafkaListener(topics = topicName, groupId = "demo-group")
	public void listenGroupFoo(String message) {
	    System.out.println("Received Message in group: " + message);
	}
}
