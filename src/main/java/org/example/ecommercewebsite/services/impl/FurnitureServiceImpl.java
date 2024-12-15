package org.example.ecommercewebsite.services.impl;

import org.example.ecommercewebsite.DAO.Impl.CategoryDAOImpl;
import org.example.ecommercewebsite.DAO.Impl.FurnitureDAOImpl;
import org.example.ecommercewebsite.DTO.request.FurnitureRequest;
import org.example.ecommercewebsite.DTO.response.FurnitureResponse;
import org.example.ecommercewebsite.ENumeration.EFurnitureStatus;
import org.example.ecommercewebsite.Mapper.FurnitureMapper;
import org.example.ecommercewebsite.business.Category;
import org.example.ecommercewebsite.business.Furniture;
import org.example.ecommercewebsite.business.Image;
import org.example.ecommercewebsite.services.IFurnitureServices;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FurnitureServiceImpl implements IFurnitureServices {
    private final FurnitureDAOImpl furnitureDAOImpl;
    private final FurnitureMapper furnitureMapper;
    private final CategoryDAOImpl categoryDAOImpl;
    public FurnitureServiceImpl() {
        this.furnitureDAOImpl = new FurnitureDAOImpl();
        this.furnitureMapper = new FurnitureMapper();
        this.categoryDAOImpl = new CategoryDAOImpl();
    }
    @Override
    public void addFurniture(FurnitureRequest furnitureRequest) {
        if (furnitureRequest == null) {
            throw new IllegalArgumentException("FurnitureRequest cannot be null.");
        }

        // Lấy Category từ database
        Category category = categoryDAOImpl.getCategoryByID(furnitureRequest.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("Category with ID " + furnitureRequest.getCategoryId() + " does not exist.");
        }

        // Kiểm tra sản phẩm đã tồn tại hay chưa
        Furniture existingFurniture = furnitureDAOImpl.getFurnitureIfExists(
                category.getCategoryName(),
                furnitureRequest.getFurnitureColor(),
                furnitureRequest.getFurniturePrice()
        );

        Furniture furniture = furnitureMapper.toEntity(furnitureRequest, category);
        if (existingFurniture != null) {
            // Nếu tồn tại, giữ lại ảnh cũ và thêm ảnh mới
            List<Image> updatedImages = new ArrayList<>(existingFurniture.getFurnitureImages());

            // Sử dụng ExecutorService cho CompletableFuture
            ExecutorService executor = Executors.newFixedThreadPool(10);
            List<CompletableFuture<Void>> futures = new ArrayList<>();

// Thêm ảnh mới vào danh sách ảnh cũ, xử lý bất đồng bộ
            if (furnitureRequest.getBase64Images() != null) {
                for (String base64Image : furnitureRequest.getBase64Images()) {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
                        Image newImage = new Image();
                        newImage.setData(decodedBytes);
                        synchronized (updatedImages) {
                            updatedImages.add(newImage);
                        }
                    }, executor);
                    futures.add(future);
                }
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();


            furniture.setFurnitureImages(updatedImages);
        }

        // Lưu sản phẩm vào cơ sở dữ liệu
        List<Furniture> furnitures = new ArrayList<>();
        furnitures.add(furniture);

        // Thêm các bản sao nếu cần
        for (int i = 1; i < furnitureRequest.getQuantity(); i++) {
            Furniture clonedFurniture = furnitureMapper.cloneFurniture(furniture);
            furnitures.add(clonedFurniture);
        }

        // Thêm tất cả các bản sao vào batch (nếu có)
        if (!furnitures.isEmpty()) {
            furnitureDAOImpl.addFurniture(furnitures);
        }
    }
    @Override
    public FurnitureResponse updateFurniture(FurnitureRequest furnitureRequest) {
        // Tạo ExecutorService với 10 threads
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            // Lấy Category từ database
            Category category = categoryDAOImpl.getCategoryByID(furnitureRequest.getCategoryId());
            // Chuyển đổi FurnitureRequest thành Furniture bằng phương thức toEntity
            Furniture furniture = furnitureMapper.toEntity(furnitureRequest, category);

            // Lưu ảnh đã xóa (nếu có)
            List<Image> existingImages = furnitureDAOImpl.getImagesByFurnitureId(furniture.getId());
            // Loại bỏ ảnh bị xóa
            List<Long> removedImageIds = furnitureRequest.getRemovedImageIds();
            if (removedImageIds != null && !removedImageIds.isEmpty()) {
                existingImages.removeIf(image -> removedImageIds.contains(image.getId()));
            }

            // Cập nhật danh sách ảnh cũ + mới
            existingImages.addAll(furniture.getFurnitureImages());
            furniture.setFurnitureImages(existingImages);

            // Cập nhật sản phẩm trong cơ sở dữ liệu bất đồng bộ
            CompletableFuture<Void> updateFurnitureFuture = CompletableFuture.runAsync(() -> {
                int updatedCount = furnitureDAOImpl.updateFurnitureByCategory(furniture);
                if (updatedCount == 0) {
                    throw new IllegalStateException("No furniture updated for category ID: " + furnitureRequest.getCategoryId());
                }
            }, executor);

            // Xóa ảnh theo category ID bất đồng bộ
            CompletableFuture<Void> deleteImagesFuture = CompletableFuture.runAsync(() -> {
                furnitureDAOImpl.deleteImagesByCategory(furnitureRequest.getCategoryId());
            }, executor);

            // Lấy danh sách sản phẩm theo Category ID và xử lý ảnh mới bất đồng bộ
            CompletableFuture<List<Image>> newImagesFuture = CompletableFuture.supplyAsync(() -> {
                List<Furniture> furnitureList = furnitureDAOImpl.getFurnitureByCategoryID(furnitureRequest.getCategoryId());
                List<Image> newImages = new ArrayList<>();
                for (Furniture f : furnitureList) {
                    for (Image img : furniture.getFurnitureImages()) {
                        Image newImg = new Image();
                        newImg.setData(img.getData());
                        newImg.setFurniture(f); // Gắn ảnh mới vào Furniture tương ứng
                        newImages.add(newImg);
                    }
                }
                return newImages;
            }, executor);

            // Đảm bảo tất cả các tác vụ bất đồng bộ hoàn thành trước khi lưu ảnh mới vào database
            CompletableFuture<Void> saveNewImagesFuture = newImagesFuture.thenAcceptAsync(newImages -> {
                if (!newImages.isEmpty()) {
                    furnitureDAOImpl.saveImagesInBatch(newImages);
                }
            }, executor);

            // Chờ tất cả các tác vụ bất đồng bộ hoàn thành
            CompletableFuture.allOf(updateFurnitureFuture, deleteImagesFuture, saveNewImagesFuture).join();

            // Chuyển đổi kết quả Furniture thành DTO để trả về
            return furnitureMapper.toDTO(furniture, 1L); // Chuyển Entity thành Response DTO
        } finally {
            // Đảm bảo executor được tắt sau khi hoàn thành tất cả các tác vụ
            executor.shutdown();
        }
    }
    @Override
    public List<FurnitureResponse> getFurnitureList() {
        Map<Furniture, Long> furnitureMap = furnitureDAOImpl.getFurnitureList();
        List<FurnitureResponse> furnitureResponseList = new ArrayList<>();
        for (Map.Entry<Furniture, Long> entry : furnitureMap.entrySet()) {
            Furniture furniture = entry.getKey();
            Long quantity = entry.getValue();
            FurnitureResponse dto = furnitureMapper.toDTO(furniture, quantity);
            furnitureResponseList.add(dto);
        }
        furnitureResponseList.sort(Comparator.comparing(FurnitureResponse::getCategoryID));//
        return furnitureResponseList;
    }
    @Override
    public FurnitureResponse getFurnitureByID(Long id){
        Furniture furniture = furnitureDAOImpl.getFurnitureByID(id);
        if (furniture != null) {
            return furnitureMapper.toDTO(furniture, 1L);
        }
        return null;
    }
    @Override
    public void stopSellingFurniture(List<FurnitureResponse> furnitureResponseList) {
        List<Long> furnitureIds = furnitureResponseList.stream()
                .map(FurnitureResponse::getId)
                .collect(Collectors.toList());

        List<Furniture> furnitureList = furnitureDAOImpl.getFurnitureByIDs(furnitureIds);
        for (Furniture furniture : furnitureList) {
            if (furniture.getFurnitureStatus() == EFurnitureStatus.ON_SALE) {
                furniture.setFurnitureStatus(EFurnitureStatus.STOP_SELLING);
            }
        }
        furnitureDAOImpl.updateFurnitureList(furnitureList); // Batch update
    }
    @Override
    public void stopSellingFurnitureByCategory(Long categoryId){
        List<FurnitureResponse> furnitureResponseList = getFurnitureByCategoryID(categoryId);
        // Kiểm tra và dừng bán sản phẩm
        if (furnitureResponseList != null && !furnitureResponseList.isEmpty()) {
            stopSellingFurniture(furnitureResponseList);
        }
    }
    @Override
    public void restoreFurnitureByCategory(Long categoryId){
        List<FurnitureResponse> furnitureResponseList = getFurnitureByCategoryID(categoryId);
        // Kiểm tra và dừng bán sản phẩm
        if (furnitureResponseList != null && !furnitureResponseList.isEmpty()) {
            restoreFurniture(furnitureResponseList);
        }
    }
    @Override
    public void restoreFurniture(List<FurnitureResponse> furnitureResponseList) {
        List<Long> furnitureIds = furnitureResponseList.stream()
                .map(FurnitureResponse::getId)
                .collect(Collectors.toList());
        List<Furniture> furnitureList = furnitureDAOImpl.getFurnitureByIDs(furnitureIds);
        for (Furniture furniture : furnitureList) {
            if (furniture.getFurnitureStatus() == EFurnitureStatus.STOP_SELLING) {
                furniture.setFurnitureStatus(EFurnitureStatus.ON_SALE);
            }
        }
        furnitureDAOImpl.updateFurnitureList(furnitureList);
    }
    @Override
    public List<FurnitureResponse> getFurnitureByCategoryID(Long categoryID){
        List<Furniture> furnitureList = furnitureDAOImpl.getFurnitureByCategoryID(categoryID);
        return furnitureList.stream()
                .map(furniture -> furnitureMapper.toDTO(furniture, 1L))  // Pass both parameters
                .collect(Collectors.toList());
    }
    @Override
    public List<FurnitureResponse> getFurnitureByFilters(Long categoryId, String priceRange) {
        // Bước 1: Lấy danh sách Furniture từ DAO dựa trên tiêu chí lọc
        List<Furniture> furnitureList = furnitureDAOImpl.getFurnitureByFilters(categoryId, priceRange);
        // Bước 2: Sử dụng hàm mapper để chuyển đổi danh sách Entity thành danh sách DTO
        return furnitureList.stream()
                .map(furniture -> furnitureMapper.toDTO(
                        furniture,
                        furnitureDAOImpl.countFurnitureByCategoryId(furniture.getCategory().getId())
                ))
                .collect(Collectors.toList());
    }
}
