package org.example.ecommercewebsite.controller;


import org.example.ecommercewebsite.DAO.IFurnitureDAO;
import org.example.ecommercewebsite.DAO.Impl.FurnitureDAOImpl;
import org.example.ecommercewebsite.business.Furniture;
import org.example.ecommercewebsite.util.PaginationHelper;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShopServlet", value = "/shopServlet")
public class ShopController extends HttpServlet {
    private final IFurnitureDAO furnitureDAO = new FurnitureDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        String keyword = request.getParameter("keyword");
        String priceParam = request.getParameter("price");
        String color = request.getParameter("Color");
        String nsx = request.getParameter("NSX");

        if (priceParam == null){
            priceParam = "70000000";
        }
        int price = Integer.parseInt(priceParam);
        // xu li pagination
        int page = 1;
        int limitPage = 8;
        long totalItem = furnitureDAO.countFurniture(keyword, price, color, nsx);

        String pageParam = request.getParameter("page");
        if (pageParam != null &&  !pageParam.equals("")) {
            try {
                page = Integer.parseInt(pageParam);  // Chuyển đổi thành int
            } catch (NumberFormatException e) {
                // Xử lý khi không thể chuyển đổi pageParam sang int
                page = 1;  // Bạn có thể gán giá trị mặc định ở đây nếu cần
            }
        }

        PaginationHelper pagination = new PaginationHelper(page, limitPage, totalItem);
        List <String> listColor = furnitureDAO.getListColor();
        List <String> listNSX = furnitureDAO.getListNSX();



        List<Furniture> listFurnitures = furnitureDAO.getAllFurnitures(pagination.getLimitItem(),pagination.getSkip(), keyword, price,color,nsx);
        request.setAttribute("listFurnitures", listFurnitures);
        request.setAttribute("pagination", pagination);
        request.setAttribute("keyword", keyword);
        request.setAttribute("price", priceParam);
        request.setAttribute("color", color);
        request.setAttribute("listColor", listColor);
        request.setAttribute("nsx", nsx);
        request.setAttribute("listNSX", listNSX);
        sc.getRequestDispatcher("/KhachHang/shop.jsp").forward(request, response);    }
}