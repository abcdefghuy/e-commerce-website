package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Category;
import org.example.ecommercewebsite.business.Coupon;
import org.example.ecommercewebsite.business.Furniture;

import java.util.List;

public interface ICouponDAO {
        void insert(Coupon coupon);

        void update(Coupon coupon);

        void delete(String couponId);

        Coupon getCouponById(String couponId);

        List<Coupon> getAllCoupons();

        List<Category> getCategoriesByCoupon(String couponId);

        boolean existedCoupon(String couponName);
        public List<Coupon> getListCoupon(List<Furniture> listFur);

}
