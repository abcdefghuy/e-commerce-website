package org.example.ecommercewebsite.DAO.Impl;
import org.example.ecommercewebsite.DAO.IManagermentCustomerDAO;
import org.example.ecommercewebsite.DTO.request.CustomerRequest;
import org.example.ecommercewebsite.business.Customer;

import javax.persistence.*;
import java.util.List;

public class ManagermentCustomerDAOImpl implements IManagermentCustomerDAO {

    private EntityManagerFactory emf;

    public ManagermentCustomerDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("dataProject"); // Tạo EntityManagerFactory
    }

    @Override
    public List<Customer> getAllCustomer(CustomerRequest reqDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT c FROM Customer c ");
            queryBuilder.append("LEFT JOIN c.orders o ");
            queryBuilder.append("LEFT JOIN o.payment p ");
            queryBuilder.append("WHERE 1=1 ");

            // Thêm điều kiện cho email
            if (reqDTO.getEmail() != null && !reqDTO.getEmail().isEmpty()) {
                queryBuilder.append("AND c.email LIKE :email ");
            }

            // Thêm điều kiện cho phone
            if (reqDTO.getPhone() != null && !reqDTO.getPhone().isEmpty()) {
                queryBuilder.append("AND c.phone LIKE :phone ");
            }

            // Thêm điều kiện cho name
            if (reqDTO.getName() != null && !reqDTO.getName().isEmpty()) {
                String[] searchTerms = reqDTO.getName().split("\\s+");  // Tách chuỗi theo khoảng trắng
//AND (c.name LIKE :name0 OR c.name LIKE :name1 OR c.name LIKE :name2)
                for (int i = 0; i < searchTerms.length; i++) {
                    if (i == 0) {
                        queryBuilder.append("AND (c.name LIKE :name" + i + " ");
                    } else {
                        queryBuilder.append("OR c.name LIKE :name" + i + " ");
                    }
                }
                queryBuilder.append(") ");
            }
            queryBuilder.append("GROUP BY c.personID ");
            queryBuilder.append("ORDER BY SUM(p.money) DESC");

            TypedQuery<Customer> query = em.createQuery(queryBuilder.toString(), Customer.class);

            if (reqDTO.getEmail() != null && !reqDTO.getEmail().isEmpty()) {
                query.setParameter("email", "%" + reqDTO.getEmail() + "%");
            }

            if (reqDTO.getPhone() != null && !reqDTO.getPhone().isEmpty()) {
                query.setParameter("phone", "%" + reqDTO.getPhone() + "%");
            }

            if (reqDTO.getName() != null && !reqDTO.getName().isEmpty()) {
                String[] searchTerms = reqDTO.getName().split("\\s+");  // Tách chuỗi tìm kiếm
                for (int i = 0; i < searchTerms.length; i++) {
                    query.setParameter("name" + i, "%" + searchTerms[i] + "%");  // Thêm dấu % để sử dụng LIKE
                }
            }
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Có lỗi xảy ra khi lấy danh sách khách hàng: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Customer findById(Long customerId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Customer.class, customerId);
        } finally {
            em.close();
        }
    }

    @Override
    public void updateCustomerStatus(List<Long> customerIds, String status) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Truy vấn JPQL(Java Persistence Query Language) để cập nhật trạng thái hàng loạt
            String jpql = "UPDATE Customer c SET c.status = :status WHERE c.personID IN :ids";
            em.createQuery(jpql)
                    .setParameter("status", status)
                    .setParameter("ids", customerIds)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Có lỗi xảy ra khi cập nhật trạng thái hàng loạt: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    //@Override
//public List<Customer> getAllCustomer(CustomerRequestDTO reqDTO) {
//    EntityManager em = emf.createEntityManager();
//    try {
//        StringBuilder queryBuilder = new StringBuilder(
//                "SELECT c.PERSONID, c.AVATAR, c.BIRTHDATE, c.EMAIL, c.GOOGLELOGIN, " +
//                        "c.NAME, c.PASSWORD, c.PHONE, c.STATUS, c.ADDRESSID " +
//                        "FROM CUSTOMER c " +
//                        "LEFT JOIN Orders o ON c.PERSONID = o.CUSTOMERID " +
//                        "LEFT JOIN PAYMENT p ON o.ID = p.ORDERID " +
//                        "WHERE 1 = 1 ");
//
//        // Thêm điều kiện tìm kiếm
//        if (reqDTO.getEmail() != null && !reqDTO.getEmail().isEmpty()) {
//            queryBuilder.append("AND c.EMAIL LIKE '%" + reqDTO.getEmail() + "%' ");
//        }
//        if (reqDTO.getPhone() != null && !reqDTO.getPhone().isEmpty()) {
//            queryBuilder.append("AND c.PHONE LIKE '%" + reqDTO.getPhone() + "%' ");
//        }
//        if (reqDTO.getName() != null && !reqDTO.getName().isEmpty()) {
//            queryBuilder.append("AND c.NAME LIKE '%" + reqDTO.getName() + "%' ");
//        }
//
//        queryBuilder.append("GROUP BY c.PERSONID "); // Group theo PERSONID
//        queryBuilder.append("ORDER BY SUM(p.MONEY) DESC"); // Sắp xếp theo tổng tiền thanh toán
//
//        // Tạo câu truy vấn native
//        Query query = em.createNativeQuery(queryBuilder.toString(), Customer.class);
//
//        // Thực thi truy vấn và trả kết quả
//        return query.getResultList();
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        throw new RuntimeException("Có lỗi xảy ra khi lấy danh sách khách hàng: " + e.getMessage(), e);
//    } finally {
//        em.close();
//    }
//}
}
