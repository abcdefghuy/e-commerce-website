package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Customer;

public interface ICustomerDAO {
    public Customer getCustomerById(Long id);
    public void updateCustomer(Customer customer);
    public boolean updatePassword(Long personID, String hashedPassword);
    public boolean emailExists(String email);
    public Customer getCustomer(Long customerID);
    public Customer getCustomerByEmailPass(String email, String password);
    public Customer getCustomerByGoogleLogin(String googleLogin);
    public int insert(Customer customer);
}
