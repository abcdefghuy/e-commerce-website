package org.example.ecommercewebsite.business;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "CUSTOMERID")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "ORDERID")
    private Order order;

    private int rate;

    private String description;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageFeedback> imageFeedbacks = new ArrayList<>();

    public Feedback()
    {

    }

    public Feedback(Customer customer, Order order, int rate, String description) {
        this.customer = customer;
        this.order = order;
        this.rate = rate;
        this.description = description;
    }

    public Feedback(Customer customer, Order order, int rate, String description, List<ImageFeedback> imageFeedbacks) {
        this.customer = customer;
        this.order = order;
        this.rate = rate;
        this.description = description;

        if (imageFeedbacks != null) {
            for (ImageFeedback imageFeedback : imageFeedbacks) {
                addImageFeedback(imageFeedback);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<ImageFeedback> getImageFeedbacks() {
        return imageFeedbacks;
    }

    public void setImageFeedbacks(List<ImageFeedback> imageFeedbacks) {
        this.imageFeedbacks = imageFeedbacks;
    }

    public void addImageFeedback(ImageFeedback imageFeedback) {
        imageFeedbacks.add(imageFeedback);
        imageFeedback.setFeedback(this);
    }

    public void removeImageFeedback(ImageFeedback imageFeedback) {
        imageFeedbacks.remove(imageFeedback);
        imageFeedback.setFeedback(null);
    }
}