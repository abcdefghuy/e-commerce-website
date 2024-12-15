/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.ENumeration;

/**
 *
 * @author ASUS
 */
public enum EOrderStatus {
    WAITING_PROCESS("Đang chờ xử lý"),
    CANCELED("Đã hủy"),
    DELIVERING("Đang giao hàng"),
    DELIVERED("Đã giao hàng"),
    ACCEPTED("Đã chấp nhận"),
    REFUNDED("Đã hoàn trả"),
    FEEDBACKED("Đã nhận phản hồi");

    // Thêm trường giá trị hiển thị
    private final String value;

    // Constructor để gán giá trị
    EOrderStatus(String value) {
        this.value = value;
    }

    // Phương thức lấy giá trị
    public String getValue() {
        return value;
    }

    // Override toString để in giá trị
    @Override
    public String toString() {
        return value;
    }
}