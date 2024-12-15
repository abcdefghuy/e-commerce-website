package org.example.ecommercewebsite.controller.customerController;
import org.example.ecommercewebsite.DTO.request.OrderRequest;
import org.example.ecommercewebsite.DTO.response.*;
import com.google.gson.Gson;
import org.example.ecommercewebsite.services.*;
import org.example.ecommercewebsite.services.impl.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/admin-customer-order/*"})
public class ManagermentOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderService orderService=new OrderServiceImpl();
    private final IFeedbackService feedbackService=new FeedbackServiceImpl();
    private final IFurnitureOfOrderService productOfOrderService=new FurnitureOfOrderServiceImpl();
    private final IManagermentCustomerService managementCustomerService =new ManagermentCustomerServiceImpl();
    private final IPaymentService paymentService=new PaymentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String customerIdStr = req.getParameter("customerId");
        Long customerId = Long.parseLong(customerIdStr);
        String orderStatusStr = req.getParameter("status");
        String orderIdStr = req.getParameter("id");
        String orderDateParam = req.getParameter("orderDate");

        OrderRequest searchOrder = new OrderRequest();
        searchOrder.setCustomerId(customerId);
        searchOrder.setStatus(orderStatusStr);

        Long orderId = null;

        if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
            try {
                orderId = Long.parseLong(orderIdStr);
                searchOrder.setId(orderId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (orderDateParam != null && !orderDateParam.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                searchOrder.setOrderDate(dateFormat.parse(orderDateParam));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<OrderResponse> orders = orderService.getOrder(searchOrder);
        CustomerResponse responseDTO = managementCustomerService.getCustomerById(customerId);
        req.setAttribute("searchOrder", searchOrder);
        req.setAttribute("orders", orders);
        req.setAttribute("customer", responseDTO);
        String url = "/Admin/listOrder.jsp";
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        FeedbackResponse responseDTO = new FeedbackResponse();
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                Long orderId = Long.parseLong(pathInfo.substring(1));
                responseDTO = feedbackService.getFeedback(orderId);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(new Gson().toJson(responseDTO));
            } catch (NumberFormatException e) {
                // Nếu không phải số, gửi lỗi về phía client
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid ID format");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing or invalid path info");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        DetailOrderAndPayment responseDTO = new DetailOrderAndPayment();
        List<FurnitureOfOrderResponse> furnitureOfOrderResponses =new ArrayList<>();
        PaymentResponse paymentResponse = new PaymentResponse();

        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                Long orderId = Long.parseLong(pathInfo.substring(1));
                furnitureOfOrderResponses = productOfOrderService.getProductOfOrder(orderId);
                paymentResponse = paymentService.getPayment(orderId);
                responseDTO.setFurnitureOfOrderResponseDTO(furnitureOfOrderResponses);
                responseDTO.setPaymentResponseDTO(paymentResponse);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(new Gson().toJson(responseDTO));
            } catch (NumberFormatException e) {
                // Nếu không phải số, gửi lỗi về phía client
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid ID format");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing or invalid path info");
        }
    }
}