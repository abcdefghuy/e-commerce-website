package org.example.ecommercewebsite.DAO.Impl;

import org.example.ecommercewebsite.DAO.ICustomerDAO;


import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.util.DBUtil;

import javax.persistence.*;

public class CustomerDAOImpl implements ICustomerDAO {
    @Override
    public Customer getCustomerById(Long id) { // Chuyển đổi tham số thành Long
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c WHERE c.personID = :id";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("id", id);

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    @Override
    public void updateCustomer(Customer customer) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(customer); // Use merge to update the existing customer entity
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw new RuntimeException("Failed to update customer profile: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    @Override
    public boolean updatePassword(Long personID, String hashedPassword) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            // Lấy khách hàng theo personID
            Customer customer = em.find(Customer.class, personID);
            if (customer != null) {
                customer.setPassword(hashedPassword);
                em.merge(customer);
                trans.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean emailExists(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c.email FROM Customer c WHERE c.email = :email";
        Query query = em.createQuery(qString);
        query.setParameter("email", email);
        try{
            String emailResult = (String) query.getSingleResult();
            return !emailResult.isEmpty();
        }
        catch(NoResultException e){
            return false;
        }
        finally{
            em.close();
        }
    }

    @Override
    public Customer getCustomer(Long customerID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c where c.personID = :customerID";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("customerID", customerID);
        try {
            Customer customer = q.getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Customer getCustomerByEmailPass(String email, String password) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c where c.email = :email and c.password = :password";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("email", email);
        q.setParameter("password", password);
        try {
            Customer customer = q.getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Customer getCustomerByGoogleLogin(String googleLogin) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c where c.googleLogin = :googleLogin";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("googleLogin", googleLogin);
        try {
            Customer customer = q.getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public int insert(Customer customer) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin(); // Bắt đầu giao dịch
            em.persist(customer); // Lưu đối tượng Customer vào database
            trans.commit(); // Commit giao dịch
            return 1; // Trả về 1 nếu thành công
        } catch (Exception e) {
            if (trans != null && trans.isActive()) { // Kiểm tra nếu giao dịch đang hoạt động
                trans.rollback(); // Rollback giao dịch nếu có lỗi
            }
            e.printStackTrace(); // In lỗi ra console để kiểm tra
            return 0; // Trả về 0 nếu gặp lỗi
        } finally {
            if (em != null && em.isOpen()) { // Đảm bảo EntityManager được đóng
                em.close();
            }
        }
    }
}
