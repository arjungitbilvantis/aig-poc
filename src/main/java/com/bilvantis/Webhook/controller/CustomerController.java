package com.bilvantis.Webhook.controller;

import com.bilvantis.Webhook.DTO.CustomerDTO;
import com.bilvantis.Webhook.service.CustomerService;
import com.bilvantis.Webhook.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    @Qualifier("customerServiceImpl")
    private CustomerService<CustomerDTO, Long> customerService;

    @Autowired
    private WebhookService webhookService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    public void updateCustomerAddress(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(customerId, customerDTO);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> printNotification(@RequestBody String requestBody){
        System.out.println("####### WebHook ############"+ requestBody);
        return new ResponseEntity<>(requestBody,HttpStatus.OK);
    }

    @PostMapping("/notification")
    public ResponseEntity<String> receiveNotification(@RequestBody String payload) {
        try {
            // Process the incoming payload, if needed
            System.out.println("Received webhook notification: " + payload);

            // Trigger the webhook service
            webhookService.triggerWebhook();

            return ResponseEntity.ok("Notification received and webhook triggered successfully");
        } catch (Exception e) {
            // Handle exceptions, log, or return an error response
            return ResponseEntity.status(500).body("Error processing webhook notification");
        }
    }

}
