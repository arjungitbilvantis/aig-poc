package com.bilvantis.Webhook.service.Impl;

import com.bilvantis.Webhook.model.TrackingRecord;
import com.bilvantis.Webhook.repository.TrackingRecordRepository;
import com.bilvantis.Webhook.service.TrackingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("trackingRecordServiceImpl")
public class TrackingRecordServiceImpl implements TrackingRecordService {

    @Autowired
    private TrackingRecordRepository trackingRecordRepository;

    @Override
    public void saveTrackingRecord(TrackingRecord trackingRecord) {
        trackingRecordRepository.save(trackingRecord);
    }
}
