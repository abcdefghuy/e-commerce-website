package org.example.ecommercewebsite.Mapper;

import org.example.ecommercewebsite.DTO.response.FeedbackResponse;
import org.example.ecommercewebsite.business.Feedback;
import org.modelmapper.ModelMapper;

import java.util.Base64;
import java.util.stream.Collectors;

public class FeedbackMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    public static FeedbackResponse convertToDTO(Feedback review) {
        // Chuyển đổi các thuộc tính cơ bản từ Review sang FeedbackResponseDTO
        FeedbackResponse feedbackResponse = modelMapper.map(review, FeedbackResponse.class);

        // Chuyển đổi danh sách ImageFeedback
        if (review.getImageFeedbacks() != null) {
            feedbackResponse.setImageFeedbacks(
                    review.getImageFeedbacks().stream()
                            .map(imageFeedback -> Base64.getEncoder().encodeToString(imageFeedback.getFeedbackImage()))
                            .collect(Collectors.toList())
            );
        }

        return feedbackResponse;
    }
}
