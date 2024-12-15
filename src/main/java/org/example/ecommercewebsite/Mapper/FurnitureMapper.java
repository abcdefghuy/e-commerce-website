package org.example.ecommercewebsite.Mapper;

import org.example.ecommercewebsite.DTO.request.FurnitureRequest;
import org.example.ecommercewebsite.DTO.response.FurnitureOfOrderResponse;
import org.example.ecommercewebsite.DTO.response.FurnitureResponse;
import org.example.ecommercewebsite.ENumeration.EFurnitureStatus;
import org.example.ecommercewebsite.business.Category;
import org.example.ecommercewebsite.business.Furniture;
import org.example.ecommercewebsite.business.Image;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FurnitureMapper {
    private static ModelMapper modelMapper = new ModelMapper();
    public FurnitureResponse toDTO(Furniture furniture, Long quantity) {
        List<String> thumbnails = new ArrayList<>();
        List<Long> imageIDs = new ArrayList<>();
        for (Image image : furniture.getFurnitureImages()) {
            thumbnails.add(Base64.getEncoder().encodeToString(image.getData()));
            imageIDs.add(image.getId());
        }
        FurnitureResponse dto = modelMapper.map(furniture, FurnitureResponse.class);
        dto.setQuantity(quantity);
        dto.setImageID(imageIDs);
        dto.setBase64ImageData(thumbnails);
        if (furniture.getFurnitureStatus() != null) {
            EFurnitureStatus status = furniture.getFurnitureStatus();
            String furnitureStatus = status.toString();
            dto.setFurnitureStatus(furnitureStatus);
        }
        if(furniture.getCategory().getCategoryName() != null && furniture.getCategory().getId() != 0 && furniture.getCategory().getManufacture() != null) {
            dto.setManufacture(furniture.getCategory().getManufacture());
            dto.setCategoryID(furniture.getCategory().getId());
            dto.setCategoryName(furniture.getCategory().getCategoryName());
        }
        return dto;
    }
    public Furniture toEntity(FurnitureRequest request, Category category) {
        Furniture furniture = modelMapper.map(request, Furniture.class);
        // Ánh xạ Category
        if (request.getFurnitureStatus() != null) {
            EFurnitureStatus status = EFurnitureStatus.valueOf(request.getFurnitureStatus());
            furniture.setFurnitureStatus(status);
        }
        furniture.setCategory(category);
        // Xử lý ảnh
        List<Image> images = new ArrayList<>();
        if (request.getBase64Images() != null) {
            for (String base64Image : request.getBase64Images()) {
                Image image = new Image();
                image.setData(Base64.getDecoder().decode(base64Image));
                image.setFurniture(furniture); // Gán Furniture cho Image
                images.add(image);
            }
        }
        furniture.setFurnitureImages(images);
        return furniture;
    }
    public Furniture cloneFurniture(Furniture originalFurniture) {
        Furniture clonedFurniture = new Furniture();
        clonedFurniture.setFurnitureColor(originalFurniture.getFurnitureColor());
        clonedFurniture.setFurniturePrice(originalFurniture.getFurniturePrice());
        clonedFurniture.setFurnitureDescription(originalFurniture.getFurnitureDescription());
        clonedFurniture.setFurnitureStatus(originalFurniture.getFurnitureStatus());
        clonedFurniture.setCategory(originalFurniture.getCategory());

        List<Image> clonedImages = new ArrayList<>();
        for (Image image : originalFurniture.getFurnitureImages()) {
            Image clonedImage = new Image();
            clonedImage.setData(image.getData());
            clonedImage.setFileName(image.getFileName());
            clonedImage.setFurniture(clonedFurniture); // Gắn ảnh vào sản phẩm được clone
            clonedImages.add(clonedImage);
        }
        clonedFurniture.setFurnitureImages(clonedImages);

        return clonedFurniture;
    }
    public FurnitureOfOrderResponse convertToDTO(Furniture furniture) {
        FurnitureOfOrderResponse furnitureDTO = modelMapper.map(furniture, FurnitureOfOrderResponse.class);
        if (furniture.getFurnitureImages() != null && !furniture.getFurnitureImages().isEmpty()) {
            furnitureDTO.setAvatar(furniture.getFurnitureImages().get(0).getBase64Data());
        }
        return furnitureDTO;
    }
}
