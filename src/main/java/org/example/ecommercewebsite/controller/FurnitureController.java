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

@WebServlet(name = "FurnitureServlet", value = "/furnitureServlet")
public class FurnitureController extends HttpServlet {
    IFurnitureDAO furnitureDAO = new FurnitureDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String idParam = request.getParameter("furnitureCategoryID");

        if (idParam == null || idParam.equals("") || !idParam.matches("\\d+")) {
            response.sendRedirect(request.getHeader("Referer"));
        }
        else{
            int id = Integer.parseInt(idParam);
            Furniture furniture = furnitureDAO.getFurniture(id);
            List<Furniture> listFurniture = furnitureDAO.getFurnitureNew();
            request.setAttribute("listFurniture", listFurniture);
            request.setAttribute("furniture", furniture);
            context.getRequestDispatcher("/KhachHang/furniture.jsp").forward(request, response);
        }
    }
}