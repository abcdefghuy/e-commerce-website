package org.example.ecommercewebsite.DTO.response;

import org.example.ecommercewebsite.business.Category;

public class CategoryResponse {
    private long categoryID;
    private String categoryName;
    private String categoryDescription;
    private String manufacture;
    public CategoryResponse(Category category) {
        super();
        this.categoryID = category.getId();
        this.categoryDescription = category.getCategoryDescription();
        this.manufacture = category.getManufacture();
        this.categoryName = category.getCategoryName();
    }
    public CategoryResponse() {
        super();
    }
    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
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

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }
}
