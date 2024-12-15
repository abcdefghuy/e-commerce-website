package org.example.ecommercewebsite.DTO.request;
import java.util.Date;

public class OrderRequest {
    private Long id;
    private Date orderDate;
    private Long CustomerId;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return CustomerId;
    }
    public void setCustomerId(Long customerId) {
        CustomerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

}
