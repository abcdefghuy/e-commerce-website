package org.example.ecommercewebsite.services.impl;

import org.example.ecommercewebsite.DAO.IOrderDAO;
import org.example.ecommercewebsite.DAO.Impl.OrderDAOImpl;
import org.example.ecommercewebsite.DTO.request.OrderRequest;
import org.example.ecommercewebsite.DTO.response.OrderResponse;
import org.example.ecommercewebsite.business.Order;
import org.example.ecommercewebsite.Mapper.OrderMapper;
import org.example.ecommercewebsite.services.IOrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements IOrderService {
    private IOrderDAO orderDAO = new OrderDAOImpl();
    private OrderMapper orderMapper = new OrderMapper();
    @Override
    public List<OrderResponse> getOrder(OrderRequest orderRequest) {
       List<Order> orders = orderDAO.getOrder(orderRequest);
       List<OrderResponse> orderResponses = new ArrayList<>();
       for (Order order : orders) {
           OrderResponse orderResponse =new OrderResponse();
           orderResponse = orderMapper.convertToDTO(order);
           orderResponses.add(orderResponse);
       }
        return orderResponses;
    }

    @Override
    public Long totalPriceOfOrder(Long orderId) {
        return 0l;
    }
}
