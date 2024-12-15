package org.example.ecommercewebsite.services;

import org.example.ecommercewebsite.DTO.request.OrderRequest;
import org.example.ecommercewebsite.DTO.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    List<OrderResponse> getOrder(OrderRequest orderRequest);
    Long totalPriceOfOrder(Long orderId);
}
