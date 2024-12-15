package org.example.ecommercewebsite.controller.customerController;
import org.example.ecommercewebsite.DTO.request.CustomerRequest;
import org.example.ecommercewebsite.DTO.response.CustomerResponse;
import org.example.ecommercewebsite.services.IManagermentCustomerService;
import org.example.ecommercewebsite.services.impl.ManagermentCustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/admin-customer-list/*"})
public class ManagermentCustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // Thêm AddressDAO
    private final IManagermentCustomerService customerService=new ManagermentCustomerServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CustomerRequest reqDTO = new CustomerRequest();
        reqDTO.setName(req.getParameter("name"));
        System.out.println(req.getParameter("name"));
        reqDTO.setPhone(req.getParameter("phone"));
        reqDTO.setEmail(req.getParameter("email"));
        List<CustomerResponse> customerList = customerService.getAllCustomers(reqDTO);
        req.setAttribute("customerList", customerList);
        req.setAttribute("customerSearch", reqDTO);
        String url = "/Admin/listCustomer.jsp";
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String action = req.getParameter("action");
        String reason = req.getParameter("reason");

        if (pathInfo != null && pathInfo.length() > 1) {
            String[] idArray = pathInfo.substring(1).split(",");
            // Chuyển đổi từ String[] sang List<Long>
            List<Long> ids = new ArrayList<>();
            for (String id : idArray) {
                try {
                    ids.add(Long.parseLong(id)); // Chuyển đổi từng phần tử String sang Long
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("ID không hợp lệ: " + id);
                    return;
                }
            }

            if (action == null || action.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Hành động không hợp lệ.");
                return;
            }

            if ("lock".equals(action)) {
                if (reason == null || reason.isEmpty()) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Lý do khóa tài khoản không được để trống.");
                    return;
                }
                customerService.lockCustomerStatus(ids, reason);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Khách hàng đã được khóa tài khoản thành công.");
            } else if ("unlock".equals(action)) {
                customerService.unlockCustomerStatus(ids);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Khách hàng đã được mở khóa tài khoản thành công.");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Hành động không được hỗ trợ.");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Không tìm thấy ID khách hàng.");
        }
    }
}
