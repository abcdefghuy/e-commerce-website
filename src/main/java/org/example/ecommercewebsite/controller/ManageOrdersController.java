package org.example.ecommercewebsite.controller;

import org.example.ecommercewebsite.DAO.ICustomerDAO;
import org.example.ecommercewebsite.DAO.IFeedbackDAO;
import org.example.ecommercewebsite.DAO.IOrderDAO;
import org.example.ecommercewebsite.DAO.IPaymentDAO;
import org.example.ecommercewebsite.DAO.Impl.CustomerDAOImpl;
import org.example.ecommercewebsite.DAO.Impl.FeedbackDAOImpl;
import org.example.ecommercewebsite.DAO.Impl.OrderDAOImpl;
import org.example.ecommercewebsite.DAO.Impl.PaymentDAOImpl;
import org.example.ecommercewebsite.ENumeration.EOrderStatus;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Feedback;
import org.example.ecommercewebsite.business.ImageFeedback;
import org.example.ecommercewebsite.business.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ManageOrdersServlet", value = "/manageOrdersServlet")
public class ManageOrdersController extends HttpServlet {
    IPaymentDAO paymentDAO = new PaymentDAOImpl();
    IFeedbackDAO feedbackDAO = new FeedbackDAOImpl();
    IOrderDAO orderDAO = new OrderDAOImpl();
    ICustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7); // Trừ đi 7 ngày
        Date date7DaysAgo = calendar.getTime();
        request.setAttribute("date7DaysAgo", date7DaysAgo);

        String action = request.getParameter("action");
        if ("loadOrders".equals(action))
        {
            Customer customer = (Customer) request.getSession().getAttribute("customer");

            if (customer != null) {
                ArrayList<Order> listOrder = orderDAO.loadOrdersOfCustomer(customer);
                request.setAttribute("listOrder", listOrder);
                request.getRequestDispatcher("/KhachHang/orders.jsp").forward(request, response);
            } else {
                response.sendRedirect("/KhachHang/login.jsp");
            }
        }

        if ("filterOrders".equals(action)) {
            Customer customer = (Customer) request.getSession().getAttribute("customer");
            String status = request.getParameter("status");
            ArrayList<Order> listOrder;

            if (status != null && !status.isEmpty()) {
                EOrderStatus orderStatus = EOrderStatus.valueOf(status);
                listOrder = orderDAO.filterOrders(customer, orderStatus);
            } else {
                listOrder = orderDAO.loadOrdersOfCustomer(customer);
            }

            // Đặt danh sách đơn hàng đã lọc vào request để truyền sang JSP
            request.setAttribute("listOrder", listOrder);
            request.getRequestDispatcher("/KhachHang/orders.jsp").forward(request, response);
        }

        if ("viewFeedback".equals(action)) {
            Long orderId = Long.valueOf(request.getParameter("orderId"));
            Feedback feedback = feedbackDAO.getFeedbackByOrderId(orderId);

            // Set up the response as JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Create a simple JSON object for feedback
            JSONObject jsonResponse = new JSONObject();

            // Assuming Feedback has appropriate methods to get data
            jsonResponse.put("description", feedback.getDescription());
            jsonResponse.put("rate", feedback.getRate());

            // For images, assuming you have base64 encoded images
            JSONArray imagesArray = new JSONArray();
            for (ImageFeedback image : feedback.getImageFeedbacks()) {
                imagesArray.put("data:image/jpeg;base64," + image.getBase64Image());
            }
            jsonResponse.put("images", imagesArray);

            // Write the JSON response
            response.getWriter().write(jsonResponse.toString());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("cancelOrder".equals(action)) {
            Long orderId = Long.valueOf(request.getParameter("orderId"));

            // Gọi phương thức trong lớp DB để thay đổi trạng thái đơn hàng
            boolean success = orderDAO.updateOrderStatus(orderId, EOrderStatus.CANCELED);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        if ("refundOrder".equals(action)) {
            Long orderId = Long.valueOf(request.getParameter("orderId"));

            // Gọi phương thức trong lớp DB để thay đổi trạng thái đơn hàng
            boolean success = orderDAO.updateOrderStatus(orderId, EOrderStatus.REFUNDED);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        if ("acceptOrder".equals(action)) {
            Long orderId = Long.valueOf(request.getParameter("orderId"));

            // Gọi phương thức trong lớp DB để thay đổi trạng thái đơn hàng
            boolean success = orderDAO.updateOrderStatus(orderId, EOrderStatus.ACCEPTED);

            if (success) {
                paymentDAO.updatePaymentDate(orderId);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        // Đọc JSON từ request
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        }

        // Parse JSON thành đối tượng JSONObject
        String json = jsonBuilder.toString();
        JSONObject feedbackData = new JSONObject(json);
        // Lấy dữ liệu từ JSONObject
        action = feedbackData.getString("action");
        if ("feedbackOrder".equals(action)) {
            long customerId = feedbackData.getLong("customerId");
            long orderId = feedbackData.getLong("orderId");
            int rate = feedbackData.getInt("rate");
            String description = feedbackData.getString("description");
            JSONArray imageFeedbacks = feedbackData.getJSONArray("imageFeedbacks");

            // Chuyển đổi JSONArray thành danh sách ImageFeedback
            List<ImageFeedback> imageFeedbackList = convertJSONArrayToImageFeedbackList(imageFeedbacks);

            Feedback feedback = new Feedback(
                    customerDAO.getCustomer(customerId),
                    orderDAO.getOrder(orderId),
                    rate,
                    description,
                    imageFeedbackList
            );

            for (int i = 0; i < imageFeedbacks.length(); i++) {
                String base64Image = imageFeedbacks.getString(i);
                System.out.println("Image " + (i + 1) + ": " + base64Image.substring(0, 50) + "..."); // Print first 50 chars
            }

            boolean success = feedbackDAO.insertFeedback(feedback);
            if (success) {
                orderDAO.updateOrderStatus(orderId, EOrderStatus.FEEDBACKED);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"status\":\"success\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to update order status.\"}");
            }
        }
    }

    private List<ImageFeedback> convertJSONArrayToImageFeedbackList(JSONArray imageFeedbacks) {
        List<ImageFeedback> imageFeedbackList = new ArrayList<>();

        if (imageFeedbacks == null || imageFeedbacks.length() == 0) {
            System.out.println("Image feedback array is empty or null.");
            return imageFeedbackList;
        }

        for (int i = 0; i < imageFeedbacks.length(); i++) {
            try {
                String base64Image = imageFeedbacks.getString(i);

                // Loại bỏ phần tiền tố "data:image/jpeg;base64," nếu có
                if (base64Image.startsWith("data:image/jpeg;base64,")) {
                    base64Image = base64Image.substring("data:image/jpeg;base64,".length());
                }

                // Kiểm tra xem Base64 đã được loại bỏ đúng hay chưa
                System.out.println("Base64 String for Image " + (i + 1) + ": " + base64Image.substring(0, 50) + "...");

                // Giải mã Base64 sang byte[]
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                imageFeedbackList.add(new ImageFeedback(imageBytes));
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid Base64 string at index " + i);
            } catch (Exception e) {
                System.err.println("Unexpected error at index " + i + ": " + e.getMessage());
            }
        }
        return imageFeedbackList;
    }
}
