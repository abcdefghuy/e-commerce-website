package org.example.ecommercewebsite.controller.order;

import org.example.ecommercewebsite.DAO.IChatDAO;
import org.example.ecommercewebsite.DAO.IOrderDAO;
import org.example.ecommercewebsite.DAO.Impl.OrderDAOImpl;
import org.example.ecommercewebsite.business.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Staff/quotationList")
public class QuotationListController extends HttpServlet {

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
            List<Order> orders = orderDAOImpl.getAllOrders();
            request.setAttribute("orders", orders);

            System.out.println(orders);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi xử lý yêu cầu.");
        }
        request.getRequestDispatcher("quotationList.jsp").forward(request, response);
    }

}
