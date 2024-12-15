package org.example.ecommercewebsite.services.impl;

import org.example.ecommercewebsite.DAO.Impl.CategoryDAOImpl;
import org.example.ecommercewebsite.DTO.request.CategoryRequest;
import org.example.ecommercewebsite.DTO.response.CategoryResponse;
import org.example.ecommercewebsite.Mapper.CategoryMapper;
import org.example.ecommercewebsite.business.Category;
import org.example.ecommercewebsite.services.ICategoryServices;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements ICategoryServices {

    private final CategoryDAOImpl categoryDAOImpl;
    //     Constructor để inject CategoryDAO
    public CategoryServiceImpl() {
        this.categoryDAOImpl = new CategoryDAOImpl();  // Khởi tạo CategoryDAO
    }

    @Override
    public List<CategoryResponse> getListCategory() {
        //todo
        // call dbutil, get list model, transfer model to dto, return dto
        List<Category> categories = categoryDAOImpl.getCategoryList();
        categories.sort(Comparator.comparing(Category::getId));
        return categories.stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id){
        Category category = categoryDAOImpl.getCategoryByID(id);
        if (category == null) {
            return null;
        }
        return CategoryMapper.convertToDTO(category);
    }
    @Override
    public boolean addCategory(CategoryRequest categoryRequest) {
        // Kiểm tra đầu vào của DTO nếu cần
        if (categoryRequest == null) {
            throw new IllegalArgumentException("CategoryDTO cannot be null");
        }
        boolean exists = categoryDAOImpl.existsByCategoryName(categoryRequest.getCategoryName());
        if (exists) {
            // Nếu tồn tại, trả về false để báo hiệu lỗi trùng lặp
            return false;
        }
        categoryDAOImpl.addCategory(CategoryMapper.convertToEntity(categoryRequest));
        return true;
    }
    @Override
    public void updateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            throw new IllegalArgumentException("CategoryDTO cannot be null");
        }
        categoryDAOImpl.editCategory(CategoryMapper.convertToEntity(categoryRequest));
    }
}
