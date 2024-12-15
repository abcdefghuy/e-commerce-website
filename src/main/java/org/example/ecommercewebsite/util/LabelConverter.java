package org.example.ecommercewebsite.util;

public class LabelConverter {
    public static String convertStatus(String status) {
        switch (status) {
            case "WAITING_PROCESS":
                return "Đang chờ xử lý";
            case "CANCELED":
                return "Đã hủy";
            case "DELIVERING":
                return "Đang vận chuyển";
            case "DELIVERED":
                return "Đã vận chuyển";
            case "ACCEPTED":
                return "Đã xác nhận";
            case "REFUNDED":
                return "Đã trả hàng";
            case "FEEDBACKED":
                return "Đã đánh giá";
            default:
                return "Trạng thái không xác định"; // Giá trị mặc định
        }
    }
}
