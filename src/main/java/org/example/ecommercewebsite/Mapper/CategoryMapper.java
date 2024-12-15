package org.example.ecommercewebsite.Mapper;

import org.example.ecommercewebsite.DTO.request.CategoryRequest;
import org.example.ecommercewebsite.DTO.response.CategoryResponse;
import org.example.ecommercewebsite.business.Category;
import org.modelmapper.ModelMapper;

public class CategoryMapper {
    private static ModelMapper modelMapper = new ModelMapper();
    public static CategoryResponse convertToDTO(Category category) {
        CategoryResponse dto = modelMapper.map(category, CategoryResponse.class);
        return dto;
    }
    public static Category convertToEntity(CategoryRequest categoryRequest) {
        return modelMapper.map(categoryRequest, Category.class);
    }
}

