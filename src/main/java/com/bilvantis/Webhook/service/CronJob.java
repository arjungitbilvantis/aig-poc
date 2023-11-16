package com.bilvantis.Webhook.service;

import com.bilvantis.Webhook.model.TrackingRecord;
import com.bilvantis.Webhook.repository.TrackingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CronJob {

    @Autowired
    private WebhookService webhookService;
    @Autowired
    private TrackingRecordRepository trackingRecordRepository;

    @Scheduled(cron = "*/30 * * * * *")
    public void runJob() {
        List<TrackingRecord> newReadTrackingRecords = trackingRecordRepository.findByNewReadTrue();

        if (!newReadTrackingRecords.isEmpty()) {
            webhookService.triggerWebhook();
            newReadTrackingRecords.forEach(record -> {
                record.setNewRead(false);
                trackingRecordRepository.save(record);
            });
        } else {
            System.out.println("No tracking records with newRead set to true. Skipping webhook trigger.");
        }
    }
}
