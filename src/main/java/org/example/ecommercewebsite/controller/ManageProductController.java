package org.example.ecommercewebsite.controller;

import org.example.ecommercewebsite.DTO.request.FurnitureRequest;
import org.example.ecommercewebsite.DTO.response.FurnitureResponse;
import org.example.ecommercewebsite.ENumeration.EFurnitureStatus;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.example.ecommercewebsite.services.ICategoryServices;
import org.example.ecommercewebsite.services.IFurnitureServices;
import org.example.ecommercewebsite.services.impl.CategoryServiceImpl;
import org.example.ecommercewebsite.services.impl.FurnitureServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@MultipartConfig
@WebServlet(name = "productController", value = "/product-controller")
public class ManageProductController extends HttpServlet {
    IFurnitureServices furnitureServices = new FurnitureServiceImpl();
    ICategoryServices categoryService = new CategoryServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        System.out.println(action);
        String id = request.getParameter("id");
        String url = "/error.jsp"; // Trang lỗi mặc định
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "addProduct":
                addProduct(request, response);
                break;
            case "editFurniture":
                editFurniture(request, response);
                break;
            case "deleteFurniture":
                deleteFurniture(request, response,id);
                break;
            case "restoreFurniture":
                restoreFurniture(request, response);
                break;
            default:
                // Hiển thị lỗi nếu action không hợp lệ
                request.setAttribute("errorMessage", "Invalid action for POST request");
                getServletContext().getRequestDispatcher(url).forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String url = "/error.jsp"; // Trang lỗi mặc định

        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "displayCategory":
                displayCategory(request, response);
                break;
            case "getFurnitureList":
                getFurnitureList(request, response);
                break;
            case "displayFurniture":
                displayFurniture(request, response);
                break;
            case "displayDetailFurniture":
                displayDetailFurniture(request, response);
                break;
            case "filterFurniture":
                filterFurniture(request, response);
                break;
            default:
                // Hiển thị lỗi nếu action không hợp lệ
                request.setAttribute("errorMessage", "Invalid action for GET request");
                getServletContext().getRequestDispatcher(url).forward(request, response);
        }
    }
    private void displayCategory(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        String url= "/Admin/addproduct.jsp";
        var categories = categoryService.getListCategory();
        request.setAttribute("categories", categories);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Lấy dữ liệu từ request
        FurnitureRequest furnitureRequest = new FurnitureRequest();
        furnitureRequest.setFurnitureColor(request.getParameter("color"));
        furnitureRequest.setFurniturePrice(Long.parseLong(request.getParameter("price")));
        furnitureRequest.setFurnitureDescription(request.getParameter("description"));
        furnitureRequest.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        furnitureRequest.setCategoryId(Long.parseLong(request.getParameter("category")));
        furnitureRequest.setFurnitureStatus((EFurnitureStatus.ON_SALE).toString());
        // Lấy ảnh từ request
        List<String> base64Images = new ArrayList<>();
        for (Part part : request.getParts()) {
            if ("images".equals(part.getName()) && part.getSize() > 0) {
                byte[] imageBytes = part.getInputStream().readAllBytes();
                base64Images.add(Base64.getEncoder().encodeToString(imageBytes));
            }
        }
        furnitureRequest.setBase64Images(base64Images);
        // Gọi Service để xử lý
        try {
            furnitureServices.addFurniture(furnitureRequest);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Sản phẩm đã được thêm thành công\"}");
        } catch (Exception e) {
            // Trả về JSON lỗi
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }
    private void getFurnitureList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String url = "/Admin/productlist.jsp";
        List<FurnitureResponse> furnitureResponseList = furnitureServices.getFurnitureList();
        // Truyền dữ liệu vào request
        request.setAttribute("furnitureList", furnitureResponseList);
        request.setAttribute("categories", categoryService.getListCategory()); // Nếu cần, truyền thêm thông tin về các categories
        // Chuyển tiếp tới trang JSP
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    private void displayFurniture(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String url= "/Admin/editproduct.jsp";
        HttpSession session = request.getSession();
        String idPram =request.getParameter("id");
        Long id = Long.parseLong(idPram);
        FurnitureResponse furnitureResponse = furnitureServices.getFurnitureByID(id);

//        request.setAttribute("selectedCategoryID", furnitureDTO.getCategoryID());
        session.setAttribute("furniture", furnitureResponse);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    private void editFurniture(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            List<String> base64Images = new ArrayList<>();
            for (Part part : request.getParts()) {
                if ("images".equals(part.getName()) && part.getSize() > 0) {
                    byte[] imageBytes = part.getInputStream().readAllBytes();
                    base64Images.add(Base64.getEncoder().encodeToString(imageBytes));
                }
            }
            HttpSession session = request.getSession();
            var f= (FurnitureResponse) session.getAttribute("furniture");
            // Lấy danh sách ảnh bị xóa
            List<Long> removedImageIds = new ArrayList<>();
            String removedImages = request.getParameter("removedImageIds");
            if (removedImages != null && !removedImages.isEmpty()) {
                String[] imageIds = removedImages.split(",");
                for (String idStr : imageIds) {
                    removedImageIds.add(Long.parseLong(idStr));
                }
            }
            // Tạo DTO từ thông tin request
            FurnitureRequest furnitureRequest = new FurnitureRequest();
            furnitureRequest.setId(Long.parseLong(request.getParameter("id")));
            furnitureRequest.setFurnitureColor(request.getParameter("furnitureColor"));
            furnitureRequest.setFurniturePrice(Long.parseLong(request.getParameter("furniturePrice")));
            furnitureRequest.setFurnitureDescription(request.getParameter("furnitureDescription"));
            furnitureRequest.setFurnitureStatus(f.getFurnitureStatus());
            furnitureRequest.setCategoryId(f.getCategoryID());
            furnitureRequest.setBase64Images(base64Images);
            furnitureRequest.setRemovedImageIds(removedImageIds);
            FurnitureResponse furnitureResponse = furnitureServices.updateFurniture(furnitureRequest);
            // Đặt thông tin vào request để chuyển đến view
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Sản phẩm đã được thêm thành công\"}");
        } catch (Exception e) {
            // Trả về JSON lỗi
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }

    private void displayDetailFurniture(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException{
        String url="/Admin/product-details.jsp";
        String idPram= request.getParameter("id");
        String quantity=request.getParameter("quantity");
        Long id = Long.parseLong(idPram);
        FurnitureResponse furnitureResponse = furnitureServices.getFurnitureByID(id);
        request.setAttribute("furniture", furnitureResponse);
        request.setAttribute("quantity",quantity);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    private void deleteFurniture(HttpServletRequest request, HttpServletResponse response, String idPrams) throws IOException, ServletException {
        if (idPrams != null) {
            Long id = Long.parseLong(idPrams);
            furnitureServices.stopSellingFurnitureByCategory(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Furniture ID is missing.");
        }
    }
    private void restoreFurniture(HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {
        try {
            // Lấy categoryId từ request
            String categoryId = request.getParameter("id");
            if (categoryId == null || categoryId.isEmpty()) {
                throw new IllegalArgumentException("Category ID is required.");
            }
            Long id = Long.parseLong(categoryId);

            // Gọi đến Service để khôi phục sản phẩm
            furnitureServices.restoreFurnitureByCategory(id);

            // Redirect về danh sách sản phẩm
            response.sendRedirect("product-controller?action=getFurnitureList");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid category ID format.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while restoring furniture: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    private void filterFurniture(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy các tham số từ request
        String url = "/Admin/productlist.jsp";
        String categoryId = request.getParameter("categoryId");
        Long id = null;

        // Nếu có categoryId, parse nó thành Long
        if (categoryId != null && !categoryId.isEmpty()) {
            id = Long.parseLong(categoryId);
        }

        String priceRange = request.getParameter("priceRange");

        // Lấy danh sách các danh mục (categories)
        var categories = categoryService.getListCategory();

        // Lấy danh sách sản phẩm theo filters
        List<FurnitureResponse> furnitureList = null;

        // Nếu có categoryId, tìm theo categoryId và priceRange
        if (id != null) {
            furnitureList = furnitureServices.getFurnitureByFilters(id, priceRange);
        } else {
            // Nếu không có categoryId, chỉ tìm theo priceRange
            furnitureList = furnitureServices.getFurnitureByFilters(null, priceRange);
        }

        // Đặt dữ liệu vào request attribute để chuyển tới JSP
        request.setAttribute("categories", categories);
        request.setAttribute("furnitureList", furnitureList);

        // Chuyển tiếp đến trang JSP để hiển thị kết quả
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}

