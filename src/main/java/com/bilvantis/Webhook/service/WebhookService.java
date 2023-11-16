package com.bilvantis.Webhook.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

//    private static final String WEBHOOK_URL = "http://localhost:8080/customer/notification"; // Replace with your actual webhook URL

    public void triggerWebhook() {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.postForObject(WEBHOOK_URL, null, String.class);
        System.out.println("Webhook triggered: 200 OK");
    }
}

