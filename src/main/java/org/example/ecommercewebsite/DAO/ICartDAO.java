package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Cart;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Furniture;

public interface ICartDAO {
    public void insert(Cart cart);
    public Cart getCart(Customer customer);
    public boolean addToCart(Customer customer, Furniture furniture);
    public Cart removeToCart(Customer customer, Furniture furniture);
}
