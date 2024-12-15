package org.example.ecommercewebsite.controller.chat;

import org.example.ecommercewebsite.DAO.IChatDAO;
import org.example.ecommercewebsite.DAO.Impl.UserInfoDAOImpl;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.DAO.Impl.ChatDAOImpl;
import org.example.ecommercewebsite.business.Message;
import org.example.ecommercewebsite.business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/Staff/loadStaffChatList")
public class GetStaffChatListController extends HttpServlet {

    private IChatDAO chatDAOImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        chatDAOImpl = new ChatDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Session
        HttpSession session = request.getSession();
        Customer customerSession = (Customer) session.getAttribute("customer");
        Long customerID = customerSession.getPersonID();

        // Utf-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");


        try {
            UserInfoDAOImpl userInfoDAOImpl = new UserInfoDAOImpl();
            Customer customer = userInfoDAOImpl.getCustomerInfoById(customerID);

            List<Staff> staffs = chatDAOImpl.getStaffChatList(customerID);

            System.out.println(staffs);

            Map<Long, String> latestMessages = new HashMap<>();

            for (Staff staff : staffs) {
                Message latestMessageObj = chatDAOImpl.getLatestMessage(staff.getPersonID(), customerID);
                if (latestMessageObj != null) {
                    latestMessages.put(staff.getPersonID(), latestMessageObj.getMsg());
                } else {
                    latestMessages.put(staff.getPersonID(), "Chưa có tin nhắn");
                }
            }

            request.setAttribute("customer", customer);
            request.setAttribute("customerID", customerID);
            request.setAttribute("staffs", staffs);
            request.setAttribute("latestMessages", latestMessages);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi xử lý yêu cầu.");
        }

        request.getRequestDispatcher("staffChatList.jsp").forward(request, response);
    }
}

