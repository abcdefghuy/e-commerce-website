package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Category;
import org.example.ecommercewebsite.business.Order;
import org.example.ecommercewebsite.business.Payment;

import java.util.List;

public interface IStatisticDAO {
    public List<List<Double>> getRevenueAndSalesData(int year);
    public List<List<Double>> getAllRevenueAndSalesData(int year);
    public List<Double> getTotalRevenueAndSales(int year);
    public List<List<Double>> getRevenueAndSalesDataForAllYears();
    public List<Payment> getPayments(int year);
    public List<Payment> getAllPayments();
    public List<List<Integer>> getDeliveredAndCanceledData(int year);
    public Category findCategoryByName(String categoryName);
    public List<Integer> getOrderYears();
    public long getTotalSalesByCategory();
    public List<List<Object>> getAllRevenueAndSalesByCategory();
    public long getTotalSalesByCategoryByYear(int year);
    public List<List<Object>> getRevenueAndSalesByCategoryByYear(int year);
    public List<Order> getDeliveredOrCanceledOrders();
    public List<Order> getCompletedAndCanceledOrders(int year);
    public long getTotalOrders();
    public long getTotalOrdersByYear(int year);
    public List<List<Integer>> getDeliveredAndCanceledOrdersForAllYears();
}
