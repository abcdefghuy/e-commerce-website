package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Payment;

public interface IPaymentDAO {
    Payment getPayment(Long orderID);
    public boolean insert(Payment payment);
    public void updatePaymentDate(Long orderID);
}
