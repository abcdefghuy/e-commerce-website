/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.business;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ADMIN
 */
@Entity 
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentID;

    @Temporal(TemporalType.DATE)    
    private Date paymentDate;
    @OneToOne
    @JoinColumn(name = "COUPONID")
    private Coupon coupon;
    private String method;
    private Double money;
    
    @OneToOne
    @JoinColumn(name = "ORDERID", unique = true)
    private Order order;

    public Payment() {
    }

    public Payment(Coupon coupon, Date paymentDate, Double money, String method, Order order) {
        this.coupon = coupon;
        this.paymentDate = paymentDate;
        this.money = money;
        this.method = method;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getLongMoney() {
        return (money != null) ? money.longValue() : 0L;
    }
}
