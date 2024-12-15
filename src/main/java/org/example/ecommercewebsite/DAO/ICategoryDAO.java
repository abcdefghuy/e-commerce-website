package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Category;

import java.util.List;

public interface ICategoryDAO {
    public void addCategory(Category category);
    public void editCategory(Category category);
    public List<Category> getCategoryList();
    public  Category getCategoryByID(Long id);
    public boolean existsByCategoryName(String categoryName);
}
