package org.example.ecommercewebsite.business;

import org.example.ecommercewebsite.ENumeration.EFurnitureStatus;

import javax.persistence.*;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

@Entity
public class Furniture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String furnitureColor;
    private Long furniturePrice;
    private String furnitureDescription;

    @ManyToOne
    @JoinColumn(name = "order_id") // Cột `order_id` trong bảng FURNITURE
    private Order order;
    @ManyToMany(mappedBy = "listFurniture")
    private List<Cart> cart;

    @Enumerated(EnumType.STRING)
    private EFurnitureStatus furnitureStatus;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "furniture", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Image> furnitureImages;

    public Furniture(String furnitureColor, Long furniturePrice, String furnitureDescription, EFurnitureStatus furnitureStatus, Category category, List<Image> furnitureImages) {
        this.furnitureColor = furnitureColor;
        this.furniturePrice = furniturePrice;
        this.furnitureDescription = furnitureDescription;
        this.furnitureStatus = furnitureStatus;
        this.category = category;
        this.furnitureImages = furnitureImages;
    }


    public Furniture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFurnitureColor() {
        return furnitureColor;
    }

    public void setFurnitureColor(String furnitureColor) {
        this.furnitureColor = furnitureColor;
    }

    public Long getFurniturePrice() {
        return furniturePrice;
    }

    public void setFurniturePrice(Long furniturePrice) {
        this.furniturePrice = furniturePrice;
    }

    public String getFurnitureDescription() {
        return furnitureDescription;
    }

    public void setFurnitureDescription(String furnitureDescription) {
        this.furnitureDescription = furnitureDescription;
    }

    public List<Image> getFurnitureImages() {
        return furnitureImages;
    }

    public void setFurnitureImages(List<Image> furnitureImages) {
        this.furnitureImages = furnitureImages;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public EFurnitureStatus getFurnitureStatus() {
        return furnitureStatus;
    }

    public void setFurnitureStatus(EFurnitureStatus EFurnitureStatus) {
        this.furnitureStatus = EFurnitureStatus;
    }
    public Image getRepresentativeImage() {
        if (furnitureImages != null && !furnitureImages.isEmpty()) {
            return furnitureImages.get(0); // Lấy ảnh đầu tiên làm đại diện
        }
        return null; // Trả về null nếu không có ảnh nào
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}