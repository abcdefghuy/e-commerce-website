package org.example.ecommercewebsite.business;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Base64;

@Entity
public class ImageFeedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] feedbackImage;

    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @Transient // Không lưu thuộc tính này vào cơ sở dữ liệu
    private String base64Image;

    public ImageFeedback(byte[] feedbackImage) {
        this.feedbackImage = feedbackImage;
    }

    public ImageFeedback() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFeedbackImage() {
        return feedbackImage;
    }

    public void setFeedbackImage(byte[] feedbackImage) {
        this.feedbackImage = feedbackImage;
    }

    public String getBase64Image() {
        if (feedbackImage != null) {
            return Base64.getEncoder().encodeToString(feedbackImage);
        }
        return null;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
        if (base64Image != null) {
            this.feedbackImage = Base64.getDecoder().decode(base64Image);
        }
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
