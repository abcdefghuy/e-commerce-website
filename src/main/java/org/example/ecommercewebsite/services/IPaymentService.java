package org.example.ecommercewebsite.services;

import org.example.ecommercewebsite.DTO.response.PaymentResponse;

public interface IPaymentService {
    PaymentResponse getPayment(Long orderID);
}
