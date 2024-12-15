package org.example.ecommercewebsite.business;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

@Entity
public class Customer extends Person {
    private String googleLogin;
    private String status;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public Customer() {

    }

    public Customer(String name, Date birthDate, Address address, String email, String password, String phone, byte[] avatar, String googleLogin, String status) {
        super(name, birthDate, address, email, password, phone, avatar);
        this.googleLogin = googleLogin;
        this.status = status;
    }

    public Customer(String name, String email, String password) {
        super(name, email, password);
    }

    public String getGoogleLogin() {
        return googleLogin;
    }

    public void setGoogleLogin(String googleLogin) {
        this.googleLogin = googleLogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        for (Order order : orders) {
            order.setCustomer(this); // Đảm bảo quan hệ hai chiều
        }
    }
}
