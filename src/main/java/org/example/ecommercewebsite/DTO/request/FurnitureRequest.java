package org.example.ecommercewebsite.DTO.request;

import java.util.List;

public class FurnitureRequest {
    private Long id;
    private String furnitureColor;
    private Long furniturePrice;
    private String furnitureDescription;
    private String furnitureStatus; // Trạng thái dưới dạng chuỗi
    private Long categoryId;        // ID của Category liên quan
    private int quantity;           // Số lượng sản phẩm
    private List<String> base64Images; // Danh sách ảnh dạng Base64 (từ frontend)
    private List<Long> removedImageIds; // List of image IDs to be removed
    private String categoryName;
    private Long priceStart;
    private Long priceEnd;
    private Long CustomerId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(Long priceStart) {
        this.priceStart = priceStart;
    }

    public Long getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Long priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Long customerId) {
        CustomerId = customerId;
    }

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFurnitureColor() {
        return furnitureColor;
    }

    public void setFurnitureColor(String furnitureColor) {
        this.furnitureColor = furnitureColor;
    }

    public Long getFurniturePrice() {
        return furniturePrice;
    }

    public void setFurniturePrice(Long furniturePrice) {
        this.furniturePrice = furniturePrice;
    }

    public String getFurnitureDescription() {
        return furnitureDescription;
    }

    public void setFurnitureDescription(String furnitureDescription) {
        this.furnitureDescription = furnitureDescription;
    }

    public String getFurnitureStatus() {
        return furnitureStatus;
    }

    public void setFurnitureStatus(String furnitureStatus) {
        this.furnitureStatus = furnitureStatus;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getBase64Images() {
        return base64Images;
    }

    public void setBase64Images(List<String> base64Images) {
        this.base64Images = base64Images;
    }
    public List<Long> getRemovedImageIds() {
        return removedImageIds;
    }

    public void setRemovedImageIds(List<Long> removedImageIds) {
        this.removedImageIds = removedImageIds;
    }
}

