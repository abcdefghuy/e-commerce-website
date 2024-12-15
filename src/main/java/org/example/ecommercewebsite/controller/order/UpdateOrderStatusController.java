package org.example.ecommercewebsite.controller.order;

import org.example.ecommercewebsite.DAO.IOrderDAO;
import org.example.ecommercewebsite.DAO.Impl.OrderDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Staff/updateOrderStatus")
public class UpdateOrderStatusController extends HttpServlet {

    private IOrderDAO orderDAOImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        orderDAOImpl = new OrderDAOImpl();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long orderId = Long.parseLong(request.getParameter("orderId"));
            String newStatus = request.getParameter("newStatus");

            orderDAOImpl.updateOrderStatus(orderId, newStatus);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Order status updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update order status.");
        }
    }
}
