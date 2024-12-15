package org.example.ecommercewebsite.DTO.response;


import java.util.ArrayList;
import java.util.List;

public class FeedbackResponse {
    private Long id;
    private String description;
    private int rate;
    private List<String> imageFeedbacks = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public List<String> getImageFeedbacks() {
        return imageFeedbacks;
    }

    public void setImageFeedbacks(List<String> imageFeedbacks) {
        this.imageFeedbacks = imageFeedbacks;
    }
}
