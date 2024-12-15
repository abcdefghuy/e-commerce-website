package org.example.ecommercewebsite.DAO.Impl;
import org.example.ecommercewebsite.DAO.ICategoryDAO;

import org.example.ecommercewebsite.business.Category;
import org.example.ecommercewebsite.util.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements ICategoryDAO {

    @Override
    public void addCategory(Category category){
        EntityManager em = DBUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(category);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw new RuntimeException("Error adding category: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    @Override
    public void editCategory(Category category) {
        EntityManager em = DBUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            if (category.getId() == null) {
                throw new IllegalArgumentException("Category ID cannot be null.");
            }

            Category t = em.find(Category.class, category.getId());
            if (t == null) {
                throw new IllegalArgumentException("Category not found with the given ID: " + category.getId());
            }
            // Cập nhật các trường dữ liệu
            t.setCategoryName(category.getCategoryName());
            t.setManufacture(category.getManufacture());
            t.setCategoryDescription(category.getCategoryDescription());
            trans.begin();
            em.merge(t);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
        } finally {
            em.close();
        }
    }
    @Override
    public List<Category> getCategoryList() {
        EntityManager em = DBUtil.getEntityManager();
        try {
            // Chỉ lấy danh sách Category từ cơ sở dữ liệu, không thêm logic sắp xếp
            return em.createQuery("SELECT c FROM Category c", Category.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    @Override
    public  Category getCategoryByID(Long id) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // Sử dụng phương thức find của EntityManager để tìm Category theo ID
            return entityManager.find(Category.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Đảm bảo đóng EntityManager để tránh rò rỉ tài nguyên
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
    @Override
    public boolean existsByCategoryName(String categoryName) {
        EntityManager em = DBUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Category c WHERE c.categoryName = :categoryName";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("categoryName", categoryName);
            Long count = query.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
