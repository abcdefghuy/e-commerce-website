package org.example.ecommercewebsite.DAO.Impl;

import org.example.ecommercewebsite.DAO.ICouponDAO;
import org.example.ecommercewebsite.business.Coupon;
import org.example.ecommercewebsite.business.Furniture;
import org.example.ecommercewebsite.data.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import org.example.ecommercewebsite.business.Category;

public class CouponDAOImpl implements ICouponDAO {

    @Override
    public void insert(Coupon coupon) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(coupon);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
        } finally {
            em.close();
        }
    }
    @Override
    // Update an existing coupon
    public void update(Coupon coupon) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.merge(coupon);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
        } finally {
            em.close();
        }
    }
    @Override
    public void delete(String couponId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Coupon coupon = em.find(Coupon.class, couponId);
            if (coupon != null) {
                em.remove(coupon);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    @Override
    // Get coupon by ID
    public Coupon getCouponById(String couponId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            return em.find(Coupon.class, couponId);
        } finally {
            em.close();
        }
    }
    @Override
    public List<Coupon> getAllCoupons() {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            String jpql = "SELECT c FROM Coupon c";
            TypedQuery<Coupon> query = entityManager.createQuery(jpql, Coupon.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public List<Category> getCategoriesByCoupon(String couponId) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            String jpql = "SELECT c.applicableFurniture FROM Coupon c  WHERE c.couponID = :couponId";
            TypedQuery<Category> query = entityManager.createQuery(jpql, Category.class);
            query.setParameter("couponId", couponId);  // Truyền couponId vào tham số của truy vấn

            return query.getResultList();
        }
        finally {
            entityManager.close();
        }

    }
    @Override
    public boolean existedCoupon(String couponName) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL truy vấn để kiểm tra xem có Coupon nào với couponName này không
            String jpql = "SELECT COUNT(c) FROM Coupon c WHERE c.couponName = :couponName";

            // Tạo TypedQuery để thực thi truy vấn
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("couponName", couponName);  // Đặt tham số couponName trong truy vấn

            // Trả về số lượng kết quả (nếu > 0 thì có tồn tại coupon)
            Long count = query.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Coupon> getListCoupon(List<Furniture> listFur) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            String queryStr = "SELECT c FROM Coupon c WHERE c.startDate <= :currentDate AND c.endDate >= :currentDate" +
                    " AND c.currentUsage < c.useLimit";
            TypedQuery<Coupon> query = em.createQuery(queryStr, Coupon.class);
            query.setParameter("currentDate", new Date());
            List<Coupon> couponList = query.getResultList();

            // Lọc các coupon có thể áp dụng cho sản phẩm trong danh sách listFur
            List<Coupon> applicableCoupons = new ArrayList<>();

            for (Coupon coupon : couponList) {
                if(coupon.getUseCondition() == "product")
                {
                    for (Furniture furniture : listFur) {
                        // Kiểm tra nếu danh mục của sản phẩm nằm trong danh sách danh mục áp dụng coupon
                        if (coupon.getNameApplicableFurniture().contains(furniture.getCategory().getCategoryName())) {
                            applicableCoupons.add(coupon);
                            break; // Nếu tìm thấy ít nhất một sản phẩm áp dụng coupon, thêm coupon vào danh sách và thoát vòng lặp
                        }
                    }
                }
                else
                {
                    applicableCoupons.add(coupon);
                }
            }
            return applicableCoupons;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        } finally {
            em.close(); // Đảm bảo đóng EntityManager sau khi sử dụng
        }
    }
}

