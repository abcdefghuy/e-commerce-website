package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.DTO.request.CustomerRequest;
import org.example.ecommercewebsite.business.Customer;

import java.util.List;

public interface IManagermentCustomerDAO {
    List<Customer> getAllCustomer(CustomerRequest reqDTO);
    Customer findById(Long customerId);
    void updateCustomerStatus(List<Long> customerIds, String status);

}