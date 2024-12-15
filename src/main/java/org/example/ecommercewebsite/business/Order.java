/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.business;

import org.example.ecommercewebsite.ENumeration.EOrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "Orders") // Renaming the table in the database
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Furniture> listFurniture;

    @ManyToOne
    @JoinColumn(name = "CUSTOMERID")
    private Customer customer;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @OneToOne(mappedBy = "order")  // Mối quan hệ với Payment
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    public Order() {
    }

    public Order(List<Furniture> listFurniture, Customer customer, Date orderDate, EOrderStatus status) {
        this.listFurniture = listFurniture;
        this.customer = customer;
        this.orderDate = orderDate;
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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
        for (Furniture furniture : listFurniture) {
            furniture.setOrder(this);  // Đồng bộ quan hệ hai chiều
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public EOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EOrderStatus status) {
        this.status = status;
    }

    public Long getTotalAmount() {
        double totalAmount = 0;
        Set<Long> categoryIds = new HashSet<>(); // Set để lưu trữ categoryID duy nhất
        Long categoryPrice = 0L; //Biến để lưu giá tiền của từng category
        for (Furniture item : listFurniture) {
            // Kiểm tra nếu categoryID chưa có trong Set
            if (!categoryIds.contains(item.getCategory().getId())) {
                // Nếu chưa có, thêm vào Set và tính tổng cho category này
                categoryIds.add(item.getCategory().getId());
                // Lấy giá tiền của các sản phẩm cùng category
                categoryPrice = item.getFurniturePrice();

                // Đếm số lượng sản phẩm cùng category
                long count = 0;

                for (Furniture furniture : listFurniture) {
                    if (furniture.getCategory().getId().equals(item.getCategory().getId())) {
                        count++; // Tăng số lượng sản phẩm trong category
                    }
                }
                totalAmount += categoryPrice * count; // Cộng giá trị của sản phẩm đại diện nhân với số lượng
            }
        }
        return (long)totalAmount;
    }

    public Object[][] getFurnitureQuantity() {
        // Tạo một Map để lưu trữ categoryId và số lượng của các furniture thuộc category đó
        Map<Long, Integer> furnitureCountMap = new HashMap<>(); // Dùng categoryId làm khóa

        // Duyệt qua từng món furniture trong danh sách
        for (Furniture furniture : listFurniture) {
            Long categoryId = furniture.getCategory().getId(); // Lấy categoryId của furniture

            // Kiểm tra nếu categoryId đã có trong Map, nếu có thì tăng số lượng, nếu chưa thì tạo mới số lượng
            furnitureCountMap.put(categoryId, furnitureCountMap.getOrDefault(categoryId, 0) + 1);
        }

        // Tạo mảng 2 chiều với số lượng phần tử bằng kích thước Map
        Object[][] result = new Object[furnitureCountMap.size()][2];
        int i = 0;

        // Lặp qua các entry của Map và điền vào mảng 2 chiều
        for (Map.Entry<Long, Integer> entry : furnitureCountMap.entrySet()) {
            Long categoryId = entry.getKey(); // Lấy categoryId
            int quantity = entry.getValue();  // Lấy số lượng furniture trong category

            // Lấy một Furniture bất kỳ từ nhóm này (vì tất cả các furniture trong nhóm có cùng categoryId)
            Furniture sampleFurniture = null;
            for (Furniture furniture : listFurniture) {
                if (furniture.getCategory().getId().equals(categoryId)) {
                    sampleFurniture = furniture; // Lấy một furniture trong category
                    break;  // Dừng ngay khi tìm thấy một furniture trong category này
                }
            }

            // Lưu Furniture vào mảng 2 chiều
            result[i][0] = sampleFurniture; // Lưu một Furniture đại diện cho category
            result[i][1] = quantity;        // Lưu số lượng của Furniture trong category
            i++;
        }

        return result; // Trả về mảng 2 chiều
    }

}
