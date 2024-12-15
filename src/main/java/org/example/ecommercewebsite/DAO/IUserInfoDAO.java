package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Staff;

public interface IUserInfoDAO  {
    public Customer getCustomerInfoById(Long id);
    public Staff getStaffInfoById(Long id);
}
