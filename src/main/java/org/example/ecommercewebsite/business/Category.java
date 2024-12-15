package org.example.ecommercewebsite.business;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.example.ecommercewebsite.DAO.IStatisticDAO;
import org.example.ecommercewebsite.DAO.Impl.StatisticDAOImpl;
@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private String categoryDescription;
    private String manufacture;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Furniture> furnitures;

    public Category() {
    }

    public Category(String categoryName, String categoryDescription, String manufacture) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.manufacture = manufacture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public List<Furniture> getFurnitures() {
        return furnitures;
    }

    public void setFurnitures(List<Furniture> furnitures) {
        this.furnitures = furnitures;
    }

    public static List<String> getListImage(List<String> categoryNames) {
        List<String> images = new ArrayList<>();
        IStatisticDAO statisticDAO = new StatisticDAOImpl();
        for (String categoryName : categoryNames) {
            Category category = statisticDAO.findCategoryByName(categoryName);
            if (category != null && !category.getFurnitures().isEmpty()) {
                Furniture firstFurniture = category.getFurnitures().get(0);
                Image representativeImage = firstFurniture.getRepresentativeImage();
                if (representativeImage != null) {
                    images.add(representativeImage.getBase64Data());
                }
            }
        }
        return images;
    }

    public static List<Furniture> getListFirstFurniture(List<String> categoryNames) {
        List<Furniture> Furnitures = new ArrayList<>();
        IStatisticDAO statisticDAO = new StatisticDAOImpl();
        for (String categoryName : categoryNames) {
            Category category = statisticDAO.findCategoryByName(categoryName);
            if (category != null && !category.getFurnitures().isEmpty()) {
                Furniture firstFurniture = category.getFurnitures().get(0);
                Furnitures.add(firstFurniture);
            }
        }
        return Furnitures;
    }
}

