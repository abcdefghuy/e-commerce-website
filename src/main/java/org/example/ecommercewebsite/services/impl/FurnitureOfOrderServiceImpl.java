package org.example.ecommercewebsite.services.impl;

import org.example.ecommercewebsite.DAO.IFurnitureDAO;
import org.example.ecommercewebsite.DAO.Impl.FurnitureDAOImpl;
import org.example.ecommercewebsite.DTO.response.FurnitureOfOrderResponse;
import org.example.ecommercewebsite.DTO.request.FurnitureRequest;
import org.example.ecommercewebsite.Mapper.FurnitureMapper;
import org.example.ecommercewebsite.business.Furniture;
import org.example.ecommercewebsite.services.IFurnitureOfOrderService;

import java.util.*;

public class FurnitureOfOrderServiceImpl implements IFurnitureOfOrderService {
    FurnitureMapper furnitureMapper = new FurnitureMapper();
    IFurnitureDAO furnitureDAO = new FurnitureDAOImpl();
    @Override
    public List<FurnitureOfOrderResponse> getProductOfOrder(Long orderID) {
        List<Furniture> furnitures = furnitureDAO.getFurnituresByOrderId(orderID);
        Map<Long, FurnitureOfOrderResponse> groupedMap = new HashMap<>();
        for (Furniture furniture : furnitures) {
            Long categoryId = furniture.getCategory().getId();
            // Lấy hoặc tạo mới ProductOfOrderResponseDTO cho Category này
            FurnitureOfOrderResponse dto = groupedMap.getOrDefault(categoryId, new FurnitureOfOrderResponse());
            if (dto.getCategoryName() == null) {
                dto = furnitureMapper.convertToDTO(furniture);
            }
            if (dto.getTotalPrice() == null) {
                dto.setTotalPrice(0L);
            }
            if (dto.getQuantity() == null) {
                dto.setQuantity(0L);
            }
            dto.setTotalPrice(dto.getTotalPrice() + furniture.getFurniturePrice());
            dto.setQuantity(dto.getQuantity() + 1);
            groupedMap.put(categoryId, dto);
        }
        return new ArrayList<>(groupedMap.values());
    }

    @Override
    public Long totalPriceOfOrder(Long orderID) {
        List<Furniture> furnitures = furnitureDAO.getFurnituresByOrderId(orderID);
        Long totalPrice = 0L;
        for (Furniture furniture : furnitures) {
            totalPrice += furniture.getFurniturePrice();
        }
        return totalPrice;
    }

    @Override
    public List<FurnitureOfOrderResponse> getFurnituresByCustomerId(FurnitureRequest furnitureRequest) {
        List<Furniture> furnitures = furnitureDAO.getFurnituresByCustomerId(furnitureRequest);
        Map<Long, FurnitureOfOrderResponse> groupedMap = new HashMap<>();
        for (Furniture furniture : furnitures) {
            Long categoryId = furniture.getCategory().getId();
            // Lấy hoặc tạo mới ProductOfOrderResponseDTO cho Category này
            FurnitureOfOrderResponse dto = groupedMap.getOrDefault(categoryId, new FurnitureOfOrderResponse());
            if (dto.getCategoryName() == null) {
                dto = furnitureMapper.convertToDTO(furniture);
            }
            if (dto.getTotalPrice() == null) {
                dto.setTotalPrice(0L);
            }
            if (dto.getQuantity() == null) {
                dto.setQuantity(0L);
            }
            dto.setTotalPrice(dto.getTotalPrice() + furniture.getFurniturePrice());
            dto.setQuantity(dto.getQuantity() + 1);
            groupedMap.put(categoryId, dto);
        }
        List<FurnitureOfOrderResponse> resultList = new ArrayList<>(groupedMap.values());

        Collections.sort(resultList, new Comparator<FurnitureOfOrderResponse>() {
            @Override
            public int compare(FurnitureOfOrderResponse o1, FurnitureOfOrderResponse o2) {
                return o2.getQuantity().compareTo(o1.getQuantity()); // So sánh theo quantity giảm dần
            }
        });

        return resultList;
    }
}
