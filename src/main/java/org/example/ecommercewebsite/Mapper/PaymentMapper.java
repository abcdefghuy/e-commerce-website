package org.example.ecommercewebsite.Mapper;

import org.example.ecommercewebsite.DTO.response.PaymentResponse;
import org.example.ecommercewebsite.business.Payment;
import org.modelmapper.ModelMapper;

public class PaymentMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    public static PaymentResponse convertToPeyment (Payment payment) {
        PaymentResponse dto = modelMapper.map(payment, PaymentResponse.class);
        return dto;
    }
}
