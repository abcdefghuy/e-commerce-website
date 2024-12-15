package org.example.ecommercewebsite.DAO.Impl;

import org.example.ecommercewebsite.DAO.ICartDAO;
import org.example.ecommercewebsite.business.Cart;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Furniture;
import org.example.ecommercewebsite.util.DBUtil;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements ICartDAO {
    @Override
    public void insert(Cart cart) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(cart);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }
    @Override
    public Cart getCart(Customer customer) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Cart c " +
                "WHERE c.customer = :customer";
        TypedQuery<Cart> q = em.createQuery(qString, Cart.class);
        q.setParameter("customer", customer);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Khong co khach hang");
            return null;
        } finally {
            em.close();
        }
    }
    @Override
    public boolean addToCart(Customer customer, Furniture furniture) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            Cart cart = getCart(customer);
            if (cart == null) {
                // Nếu chưa có Cart, tạo Cart mới
                cart = new Cart();
                cart.setCustomer(customer);

                // Tạo danh sách mới và thêm Furniture vào danh sách
                List<Furniture> listFurniture = new ArrayList<>();
                listFurniture.add(furniture);
                cart.setListFurniture(listFurniture);

                insert(cart); // Thêm giỏ hàng vào database
                trans.commit();
                return true; // Thêm thành công
            } else {
                // Nếu đã có Cart, kiểm tra xem Furniture đã có trong Cart chưa
                boolean exists = cart.getListFurniture().stream()
                        .anyMatch(f -> f.getCategory().getId().equals(furniture.getCategory().getId()));

                if (!exists) {
                    // Nếu Furniture chưa có, thêm vào giỏ hàng
                    cart.getListFurniture().add(furniture);
                    em.merge(cart); // Cập nhật giỏ hàng
                    trans.commit();
                    return true; // Thêm thành công
                } else {
                    // Furniture đã tồn tại trong giỏ hàng
                    trans.rollback();
                    return false; // Không thêm vì đã có
                }
            }
        } catch (Exception e) {
            System.out.println("Có lỗi: " + e.getMessage());
            if (trans.isActive()) {
                trans.rollback();
            }
            return false; // Gặp lỗi trong quá trình thêm
        } finally {
            em.close();
        }
    }
    @Override
    public Cart removeToCart(Customer customer, Furniture furniture) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();

        try {
            // Lấy Cart của Customer, chắc chắn cart không null
            Cart cart = getCart(customer);

            // Xóa Furniture khỏi listFurniture nếu tồn tại
            cart.getListFurniture().removeIf(f -> f.getCategory().getId().equals(furniture.getCategory().getId()));

            // Cập nhật Cart trong database
            em.merge(cart);
            trans.commit();

            return cart; // Trả về Cart đã cập nhật

        } catch (Exception e) {
            System.out.println("Lỗi: " + e);
            trans.rollback();
            return null; // Trả về null nếu có lỗi
        } finally {
            em.close();
        }
    }
}
