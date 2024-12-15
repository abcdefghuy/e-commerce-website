package org.example.ecommercewebsite.services;

import org.example.ecommercewebsite.DTO.response.FeedbackResponse;

public interface IFeedbackService {
    FeedbackResponse getFeedback(Long orderID);
}
