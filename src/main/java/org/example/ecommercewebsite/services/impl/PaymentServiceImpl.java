package org.example.ecommercewebsite.services.impl;


import org.example.ecommercewebsite.DAO.IPaymentDAO;
import org.example.ecommercewebsite.DAO.Impl.PaymentDAOImpl;
import org.example.ecommercewebsite.DTO.response.PaymentResponse;
import org.example.ecommercewebsite.Mapper.PaymentMapper;
import org.example.ecommercewebsite.business.Payment;
import org.example.ecommercewebsite.services.IFurnitureOfOrderService;
import org.example.ecommercewebsite.services.IPaymentService;

public class PaymentServiceImpl implements IPaymentService {
    private IPaymentDAO paymentDAO = new PaymentDAOImpl();
    private PaymentMapper paymentMapper = new PaymentMapper();
    private IFurnitureOfOrderService furnitureOfOrderService = new FurnitureOfOrderServiceImpl();
    @Override
    public PaymentResponse getPayment(Long orderID) {
        Payment payment = paymentDAO.getPayment(orderID);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse = paymentMapper.convertToPeyment(payment);
        Long totalPriceFurniture = furnitureOfOrderService.totalPriceOfOrder(orderID);
        paymentResponse.setTotalPrice(totalPriceFurniture);
        return paymentResponse;
    }

}
