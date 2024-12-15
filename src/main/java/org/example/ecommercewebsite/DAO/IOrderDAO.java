package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.DTO.request.OrderRequest;
import org.example.ecommercewebsite.ENumeration.EOrderStatus;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Order;

import java.util.ArrayList;
import java.util.List;

public interface IOrderDAO {
    List<Order> getOrder(OrderRequest orderRequest);
    public List<Order> getAllOrders();
    public void updateOrderStatus(Long orderId, String newStatus);
    public List<Order> filterOrdersByStatus(String status);
    public boolean insertOrder(Order order);
    public boolean updateOrderStatus(Long orderId, EOrderStatus orderStatus);
    public Order getOrder(Long id);
    public ArrayList<Order> filterOrders(Customer customer, EOrderStatus orderStatus);
    public ArrayList<Order> loadOrdersOfCustomer(Customer customer);
}
