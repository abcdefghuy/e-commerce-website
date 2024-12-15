package org.example.ecommercewebsite.services;

import org.example.ecommercewebsite.DTO.request.CustomerRequest;
import org.example.ecommercewebsite.DTO.response.CustomerResponse;

import java.util.List;

public interface IManagermentCustomerService {
    List<CustomerResponse> getAllCustomers(CustomerRequest reqDTO);
    void lockCustomerStatus(List<Long> customerIds, String reason);
    void unlockCustomerStatus(List<Long> customerIds);
    CustomerResponse getCustomerById(Long customerId);
}
