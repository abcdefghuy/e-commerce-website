package org.example.ecommercewebsite.controller;

import org.example.ecommercewebsite.DTO.request.CategoryRequest;
import org.example.ecommercewebsite.DTO.response.CategoryResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.ecommercewebsite.services.ICategoryServices;
import org.example.ecommercewebsite.services.impl.CategoryServiceImpl;

import java.io.IOException;


@WebServlet(name = "categoryController" ,value="/category-controller")
public class ManageCategoryController extends HttpServlet {
    ICategoryServices categoryService = new CategoryServiceImpl();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null || action.equals("null") ) {
            String url="/Admin/addcategory.jsp";
            getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
        }
        if (action.equals("getCategoryList")) {
            getCategoryList(request, response);
        } else if (action.equals("displayCategory")) {
            displayCategory(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action.equals("addCategory")) {
            addCategory(request, response);
        } else if (action.equals("editCategory")) {
            editCategory(request, response);
        }
    }
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String categoryName = request.getParameter("categoryName");
        String manufacturerName = request.getParameter("manufacturerName");
        String description = request.getParameter("description");

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryName(categoryName);
        categoryRequest.setCategoryDescription(description);
        categoryRequest.setManufacture(manufacturerName);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8"); // Đảm bảo mã hóa là UTF-8 để tránh lỗi phông chữ
        try {
            boolean isAdded = categoryService.addCategory(categoryRequest); // Service trả về boolean
            if (isAdded) {
                response.getWriter().write("{\"status\":\"success\", \"message\":\"Danh mục đã được thêm thành công\"}");
            } else {
                response.getWriter().write("{\"status\":\"\", \"message\":\"Danh mục đã tồn tại\"}");
            }
        } catch (Exception e) {
            // Trường hợp lỗi khác
            response.getWriter().write("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }
    private void getCategoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url="/Admin/categorylist.jsp";
        var listDTO = categoryService.getListCategory();
        request.setAttribute("categoryList", listDTO);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    private void displayCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/Admin/editcategory.jsp";
        // Lấy categoryID từ request
        String categoryID = request.getParameter("categoryID");
        if (categoryID != null && !categoryID.isEmpty()) {
            try {
                // Chuyển categoryID thành Long
                Long id = Long.parseLong(categoryID);
                // Gọi service để lấy Category từ DB
                CategoryResponse categoryResponse = categoryService.getCategoryById(id);
                // Kiểm tra nếu categoryDTO tồn tại
                if (categoryResponse != null) {
                    // Lưu vào session nếu cần thiết
                    HttpSession session = request.getSession();
                    session.setAttribute("category", categoryResponse);
                    // Chuyển hướng đến trang edit
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                } else {
                    // Nếu không tìm thấy category, trả về lỗi
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
                }
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu categoryID không phải là số hợp lệ
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID format");
            }
        } else {
            // Nếu không có categoryID, trả về lỗi
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category ID is missing");
        }
    }
    private void editCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String idParam = request.getParameter("id");
        Long id = Long.parseLong(idParam);
        String categoryName = request.getParameter("categoryName");
        String manufacturerName = request.getParameter("manufacturerName");
        String description = request.getParameter("description");
        try {
            if (idParam.isEmpty()) {
                throw new IllegalArgumentException("Category ID is required.");
            }
            CategoryRequest categoryRequest = new CategoryRequest();
            categoryRequest.setCategoryID(id);
            categoryRequest.setCategoryName(categoryName);
            categoryRequest.setCategoryDescription(description);
            categoryRequest.setManufacture(manufacturerName);
            categoryService.updateCategory(categoryRequest);
            // Trả về thông báo thành công dưới dạng JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8"); // Đảm bảo mã hóa là UTF-8 để tránh lỗi phông chữ
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Danh mục đã được cập nhật thành công\"}");
        } catch (Exception e) {
            // Trả về JSON lỗi nếu có ngoại lệ
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }
}




