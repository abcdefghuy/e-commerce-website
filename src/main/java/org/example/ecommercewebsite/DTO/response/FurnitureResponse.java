package org.example.ecommercewebsite.DTO.response;


import java.util.List;

public class FurnitureResponse {
    private Long id;
    private String furnitureDescription;
    private Long furniturePrice;
    private String furnitureColor;
    private String furnitureStatus;
    private Long categoryID;
    private String categoryName;
    private String manufacture;
    private Long quantity;
    private List<String> base64ImageData;
    private List<Long> imageID;

    public FurnitureResponse() {
    }

    public FurnitureResponse(Long id, Long categoryID, String categoryName, String furnitureStatus, String manufacture,
                             String furnitureDescription, Long furniturePrice, String furnitureColor, Long quantity,
                             List<String> base64ImageData, List<Long> imageID) {
        this.id = id;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.furnitureStatus = furnitureStatus;
        this.manufacture = manufacture;
        this.furnitureDescription = furnitureDescription;
        this.furniturePrice = furniturePrice;
        this.furnitureColor = furnitureColor;
        this.quantity = quantity;
        this.base64ImageData = base64ImageData;
        this.imageID = imageID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFurnitureStatus() {
        return furnitureStatus;
    }

    public void setFurnitureStatus(String furnitureStatus) {
        this.furnitureStatus = furnitureStatus;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getFurnitureDescription() {
        return furnitureDescription;
    }

    public void setFurnitureDescription(String furnitureDescription) {
        this.furnitureDescription = furnitureDescription;
    }

    public Long getFurniturePrice() {
        return furniturePrice;
    }

    public void setFurniturePrice(Long furniturePrice) {
        this.furniturePrice = furniturePrice;
    }

    public String getFurnitureColor() {
        return furnitureColor;
    }

    public void setFurnitureColor(String furnitureColor) {
        this.furnitureColor = furnitureColor;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public List<String> getBase64ImageData() {
        return base64ImageData;
    }

    public void setBase64ImageData(List<String> base64ImageData) {
        this.base64ImageData = base64ImageData;
    }
    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }
    public List<Long> getImageID() {
        return imageID;
    }

    public void setImageID(List<Long> imageID) {
        this.imageID = imageID;
    }
}
