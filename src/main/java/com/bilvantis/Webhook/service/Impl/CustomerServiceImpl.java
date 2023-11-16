package com.bilvantis.Webhook.service.Impl;

import com.bilvantis.Webhook.DTO.CustomerDTO;
import com.bilvantis.Webhook.model.Customer;
import com.bilvantis.Webhook.repository.CustomerRepo;
import com.bilvantis.Webhook.service.CustomerService;
import com.bilvantis.Webhook.util.CustomerSupport;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bilvantis.Webhook.util.CustomerSupport.convertCustomerDTOToCustomerEntity;
import static com.bilvantis.Webhook.util.CustomerSupport.convertCustomerEntityToCustomerDTO;

@Service("customerServiceImpl")
public class CustomerServiceImpl implements CustomerService<CustomerDTO, Long> {

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private EntityManager entityManager;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertCustomerDTOToCustomerEntity(customerDTO);
        customer = customerRepo.save(customer);
        return convertCustomerEntityToCustomerDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepo.findAll();
        if (CollectionUtils.isNotEmpty(customerList)) {
            return customerList.stream()
                    .map(CustomerSupport::convertCustomerEntityToCustomerDTO)
                    .collect(Collectors.toList());

        } else {
            throw new ApplicationContextException("failed to get customer details");
        }
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepo.findById(id);
        if (optionalCustomer.isPresent()) {
            return convertCustomerEntityToCustomerDTO(optionalCustomer.get());
        }
        throw new ApplicationContextException("Customer not present in db");
    }


    @Transactional
    @Override
    public void updateCustomer(Long customerId, CustomerDTO customerDTO) {
        Customer customer = entityManager.find(Customer.class, customerId);
        if (customer != null) {
            // Update only non-null fields from the DTO
            if (customerDTO.getFirstName() != null) {
                customer.setFirstName(customerDTO.getFirstName());
            }
            if (customerDTO.getLastName() != null) {
                customer.setLastName(customerDTO.getLastName());
            }
            if (customerDTO.getPhoneNumber() != null) {
                customer.setPhoneNumber(customerDTO.getPhoneNumber());
            }
            if (customerDTO.getAddress() != null) {
                customer.setAddress(customerDTO.getAddress());
            }
        } else {
            System.out.println("Customer not found with ID: " + customerId);
        }
    }
}
