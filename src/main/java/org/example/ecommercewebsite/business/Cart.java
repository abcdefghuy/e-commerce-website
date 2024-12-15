/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.business;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ASUS
 */
@Entity
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    private List<Furniture> listFurniture;
    
    @OneToOne
    @JoinColumn(name = "CUSTOMERID") // Tên cột trong bảng Cart để lưu trữ ID của Customer
    private Customer customer;

    public Cart() {
    }

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Furniture> getListFurniture() {
        return listFurniture;
    }

    public void setListFurniture(List<Furniture> listFurniture) {
        this.listFurniture = listFurniture;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
