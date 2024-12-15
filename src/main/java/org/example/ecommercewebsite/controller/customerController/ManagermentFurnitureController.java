package org.example.ecommercewebsite.controller.customerController;

import org.example.ecommercewebsite.DTO.request.FurnitureRequest;
import org.example.ecommercewebsite.DTO.response.CustomerResponse;
import org.example.ecommercewebsite.DTO.response.FurnitureOfOrderResponse;
import org.example.ecommercewebsite.services.*;
import org.example.ecommercewebsite.services.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin-customer-furniture/*"})
public class ManagermentFurnitureController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderService orderService=new OrderServiceImpl();
    private final IFeedbackService feedbackService=new FeedbackServiceImpl();
    private final IFurnitureOfOrderService productOfOrderService=new FurnitureOfOrderServiceImpl();
    private final IManagermentCustomerService managementCustomerService =new ManagermentCustomerServiceImpl();
    private final IPaymentService paymentService=new PaymentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String categoryName=req.getParameter("categoryName");
        String customerIdStr = req.getParameter("customerId");
        Long customerId = Long.parseLong(customerIdStr);
        String priceStartStr = req.getParameter("priceStart");
        String priceEndStr = req.getParameter("priceEnd");
        FurnitureRequest furnitureRequest=new FurnitureRequest();
        furnitureRequest.setCustomerId(customerId);
        furnitureRequest.setCategoryName(categoryName);
        Long priceStart= null;
        Long priceEnd= null;
        if (priceStartStr != null && !priceStartStr.trim().isEmpty()) {
            try {
                priceStart = Long.parseLong(priceStartStr);
                furnitureRequest.setPriceStart(priceStart);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (priceStartStr != null && !priceStartStr.trim().isEmpty()) {
            try {
                priceEnd= Long.parseLong(priceEndStr);
                furnitureRequest.setPriceEnd(priceEnd);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        List<FurnitureOfOrderResponse> furnitureOfOrderResponseList =productOfOrderService.getFurnituresByCustomerId(furnitureRequest);
        CustomerResponse responseDTO = managementCustomerService.getCustomerById(customerId);
        req.setAttribute("furniture", furnitureOfOrderResponseList);
        req.setAttribute("furnitureRequestDTO", furnitureRequest);
        req.setAttribute("customer", responseDTO);
        String url = "/Admin/listFurnitureOfCustomer.jsp";
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
}