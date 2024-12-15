package org.example.ecommercewebsite.services;

import org.example.ecommercewebsite.DTO.request.FurnitureRequest;
import org.example.ecommercewebsite.DTO.response.FurnitureResponse;

import java.util.List;

public interface IFurnitureServices {
   void addFurniture(FurnitureRequest furnitureRequest);
   FurnitureResponse updateFurniture(FurnitureRequest furnitureRequest);
   List<FurnitureResponse> getFurnitureByCategoryID(Long categoryID);
   FurnitureResponse getFurnitureByID(Long id);
   List<FurnitureResponse> getFurnitureList();
   void stopSellingFurniture(List<FurnitureResponse> furnitureResponseList);
   void restoreFurniture(List<FurnitureResponse> furnitureResponseList);
   void stopSellingFurnitureByCategory(Long categoryId);
   void restoreFurnitureByCategory(Long categoryId);
   List<FurnitureResponse> getFurnitureByFilters(Long categoryId, String priceRange);
}
