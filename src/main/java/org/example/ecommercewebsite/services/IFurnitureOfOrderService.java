package org.example.ecommercewebsite.services;

import org.example.ecommercewebsite.DTO.response.FurnitureOfOrderResponse;
import org.example.ecommercewebsite.DTO.request.FurnitureRequest;

import java.util.List;

public interface IFurnitureOfOrderService {
    List<FurnitureOfOrderResponse> getProductOfOrder(Long orderID);
    Long totalPriceOfOrder(Long orderId);
    List<FurnitureOfOrderResponse> getFurnituresByCustomerId(FurnitureRequest furnitureRequest) ;
}
