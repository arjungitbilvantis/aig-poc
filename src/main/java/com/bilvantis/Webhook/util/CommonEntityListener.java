package com.bilvantis.Webhook.util;

import com.bilvantis.Webhook.DTO.CustomerDTO;
import com.bilvantis.Webhook.model.Customer;
import com.bilvantis.Webhook.model.TrackingRecord;
import com.bilvantis.Webhook.repository.TrackingRecordRepository;
import com.bilvantis.Webhook.service.CustomerService;
import com.bilvantis.Webhook.service.TrackingRecordService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bilvantis.Webhook.util.CustomerSupport.convertCustomerDTOToCustomerEntity;

@Component
public class CommonEntityListener {

    @Autowired
    private TrackingRecordService trackingRecordService;


    private static CustomerService<CustomerDTO, Long> customerService;

    @Autowired
    public void setCustomerService(CustomerService<CustomerDTO, Long> customerService) {
        CommonEntityListener.customerService = customerService;
    }

    private static TrackingRecordRepository trackingRecordRepository;

    @Autowired
    public void setTrackingRecordRepository(TrackingRecordRepository trackingRecordRepository) {
        CommonEntityListener.trackingRecordRepository = trackingRecordRepository;
    }


    @PostPersist
    public void onPostPersist(Customer customer) {
        System.out.println("Customer data persisted: " + customer.toString());
    }

    @PostUpdate
    public void onPostUpdate(Customer customer) {
        System.out.println("Customer data updated: " + customer.toString());

        Customer oldCustomer = convertCustomerDTOToCustomerEntity(customerService.getCustomerById(customer.getId()));
        TrackingRecord trackingRecord = new TrackingRecord();
        trackingRecord.setFieldName("first_name");
        trackingRecord.setOldValue(oldCustomer.getFirstName());
        trackingRecord.setNewValue(customer.getFirstName());
        trackingRecord.setNewRead(true);
        trackingRecordRepository.save(trackingRecord);
    }
}
