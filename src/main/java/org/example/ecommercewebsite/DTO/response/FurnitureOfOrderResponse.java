package org.example.ecommercewebsite.DTO.response;


public class FurnitureOfOrderResponse {
    private String categoryName;
    private String categoryDescription;
    private Long furniturePrice;
    private String furnitureStatus;
    private Long    quantity;
    private Long    totalPrice;
    private String avatar;
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Long getFurniturePrice() {
        return furniturePrice;
    }

    public void setFurniturePrice(Long furniturePrice) {
        this.furniturePrice = furniturePrice;
    }

    public String getFurnitureStatus() {
        return furnitureStatus;
    }

    public void setFurnitureStatus(String furnitureStatus) {
        this.furnitureStatus = furnitureStatus;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
