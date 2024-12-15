package org.example.ecommercewebsite.services.impl;


import org.example.ecommercewebsite.DAO.IFeedbackDAO;
import org.example.ecommercewebsite.DAO.Impl.FeedbackDAOImpl;
import org.example.ecommercewebsite.DTO.response.FeedbackResponse;
import org.example.ecommercewebsite.business.Feedback;
import org.example.ecommercewebsite.Mapper.FeedbackMapper;
import org.example.ecommercewebsite.services.IFeedbackService;

public class FeedbackServiceImpl implements IFeedbackService {
    private IFeedbackDAO feedbackDAO = new FeedbackDAOImpl();
    private FeedbackMapper feedbackMapper = new FeedbackMapper();
    @Override
    public FeedbackResponse getFeedback(Long orderID) {
        Feedback review=feedbackDAO.getFeedback(orderID);
        FeedbackResponse feedbackResponse =new FeedbackResponse();
        feedbackResponse = feedbackMapper.convertToDTO(review);
        return feedbackResponse;
    }
}
