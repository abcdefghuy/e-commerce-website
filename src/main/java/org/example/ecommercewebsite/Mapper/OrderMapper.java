package org.example.ecommercewebsite.Mapper;

import org.example.ecommercewebsite.DTO.response.OrderResponse;
import org.example.ecommercewebsite.business.Order;
import org.modelmapper.ModelMapper;

public class OrderMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    // Phương thức chuyển đổi từ Order sang OrderResponseDTO
    public static OrderResponse convertToDTO(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }
}
