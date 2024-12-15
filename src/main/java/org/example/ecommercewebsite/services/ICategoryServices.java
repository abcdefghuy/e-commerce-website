package org.example.ecommercewebsite.services;

import org.example.ecommercewebsite.DTO.request.CategoryRequest;
import org.example.ecommercewebsite.DTO.response.CategoryResponse;

import java.util.List;

public interface ICategoryServices {
    List<CategoryResponse> getListCategory();
    CategoryResponse getCategoryById(Long id);
    boolean addCategory(CategoryRequest category);
    void updateCategory(CategoryRequest category);
}
