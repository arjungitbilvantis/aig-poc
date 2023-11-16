package com.bilvantis.Webhook.repository;

import com.bilvantis.Webhook.model.TrackingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRecordRepository extends JpaRepository<TrackingRecord,Long> {
    List<TrackingRecord> findByNewReadTrue();
}
