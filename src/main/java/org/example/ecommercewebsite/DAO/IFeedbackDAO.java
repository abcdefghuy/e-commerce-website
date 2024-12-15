package org.example.ecommercewebsite.DAO;


import org.example.ecommercewebsite.business.Feedback;

public interface IFeedbackDAO {
    Feedback getFeedback(Long orderID);
    public boolean insertFeedback(Feedback feedback);
    public Feedback getFeedbackByOrderId(Long orderId);
}
