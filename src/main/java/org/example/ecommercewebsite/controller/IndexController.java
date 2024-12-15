package org.example.ecommercewebsite.controller;


import org.example.ecommercewebsite.DAO.IFurnitureDAO;
import org.example.ecommercewebsite.DAO.Impl.FurnitureDAOImpl;
import org.example.ecommercewebsite.business.Furniture;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", value = "/indexServlet")
public class IndexController extends HttpServlet {
    IFurnitureDAO furnitureDAO = new FurnitureDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        List<Furniture> listFurniture = furnitureDAO.getFurnituresHot(7);
        List<Furniture> listFurnitureNew = furnitureDAO.getFurnitureNew();
        request.setAttribute("listFurniture", listFurniture);
        request.setAttribute("listFurnitureNew", listFurnitureNew);
        context.getRequestDispatcher("/KhachHang/index.jsp").forward(request, response);
    }
}