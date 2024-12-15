package org.example.ecommercewebsite.DAO.Impl;

import org.example.ecommercewebsite.DAO.IFeedbackDAO;
import org.example.ecommercewebsite.business.Feedback;
import org.example.ecommercewebsite.util.DBUtil;

import javax.persistence.*;
import java.util.List;

public class FeedbackDAOImpl implements IFeedbackDAO {
    private EntityManagerFactory emf;

    public FeedbackDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("dataProject"); // Tạo EntityManagerFactory
    }
    //    @Override
//    public Feedback getFeedback(Long orderID) {
//        EntityManager em = emf.createEntityManager();
//        StringBuilder query = new StringBuilder("SELECT r FROM Feedback r WHERE r.order.id = :orderID");
//        TypedQuery<Feedback> queryReview = em.createQuery(query.toString(), Feedback.class);
//        queryReview.setParameter("orderID", orderID);
//        return queryReview.getSingleResult();
//    }
    @Override
    public Feedback getFeedback(Long orderID) {
        EntityManager em = emf.createEntityManager();
        StringBuilder query = new StringBuilder("SELECT r FROM Feedback r WHERE r.order.id = :orderID");
        TypedQuery<Feedback> queryReview = em.createQuery(query.toString(), Feedback.class);
        queryReview.setParameter("orderID", orderID);
        queryReview.setMaxResults(1);
        return queryReview.getSingleResult();
    }
    @Override
    public boolean insertFeedback(Feedback feedback) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(feedback);

            // Commit giao dịch nếu cả hai thao tác đều thành công
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback nếu có lỗi xảy ra
            }
            return false;

        } finally {
            em.close(); // Đảm bảo đóng EntityManager
        }
    }
    @Override
    public Feedback getFeedbackByOrderId(Long orderId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            String query = "SELECT f FROM Feedback f WHERE f.order.id = :orderId";
            TypedQuery<Feedback> tq = em.createQuery(query, Feedback.class);
            tq.setParameter("orderId", orderId);
            List<Feedback> feedbackList = tq.getResultList();
            if (feedbackList.isEmpty()) {
                return null;
            }
            return feedbackList.get(0); // Chỉ lấy phần tử đầu tiên nếu có nhiều kết quả
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
