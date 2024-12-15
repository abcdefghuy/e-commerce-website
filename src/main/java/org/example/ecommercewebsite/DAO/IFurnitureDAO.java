package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.DTO.request.FurnitureRequest;
import org.example.ecommercewebsite.business.Feedback;
import org.example.ecommercewebsite.business.Furniture;

import java.util.List;
import java.util.Map;

public interface IFurnitureDAO {
    List<Furniture> getFurnituresByOrderId(Long orderId);

    List<Furniture> getFurnituresByCustomerId(FurnitureRequest furnitureRequest);
    public void addFurniture(List<Furniture> furnitures);
    public int updateFurnitureByCategory(Furniture furniture);
    public List<Furniture> getFurnitureByCategoryID(Long categoryID);
    public Furniture getFurnitureByID(Long id);public Map<Furniture, Long> getFurnitureList();
    public void updateFurnitureList(List<Furniture> furnitureList);
    public List<Furniture> getFurnitureByFilters(Long categoryId, String priceRange);
    public List<Furniture> getAllFurnitures(int limit, int skip, String keyword, int price, String color, String nsx);
    public List<Furniture> getFurnitureNew ();
    public long countFurniture(String keyword, int price, String color, String nsx);
    public Furniture getFurniture(int id);
    public List<Furniture> getFurnitureQuantity(String categoryID, int quantity);
    public Furniture getFurnitureDiscount(String categoryID);
    public List<String> getListColor ();
    public List<String> getListNSX ();
    public List<Feedback> getFeedBacks ();
    public List<Furniture> getFurnituresHot (int topLimit);
}