package org.example.ecommercewebsite.DTO.response;

import java.util.List;

public class DetailOrderAndPayment {
    private List<FurnitureOfOrderResponse> furnitureOfOrderResponse;
    private PaymentResponse paymentResponse;

    public List<FurnitureOfOrderResponse> getFurnitureOfOrderResponseDTO() {
        return furnitureOfOrderResponse;
    }

    public void setFurnitureOfOrderResponseDTO(List<FurnitureOfOrderResponse> furnitureOfOrderResponse) {
        this.furnitureOfOrderResponse = furnitureOfOrderResponse;
    }

    public PaymentResponse getPaymentResponseDTO() {
        return paymentResponse;
    }

    public void setPaymentResponseDTO(PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }
}
