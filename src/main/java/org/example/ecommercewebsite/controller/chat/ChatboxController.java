package org.example.ecommercewebsite.controller.chat;

import org.example.ecommercewebsite.DAO.IUserInfoDAO;
import org.example.ecommercewebsite.DAO.Impl.UserInfoDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Staff/chatbox")
public class ChatboxController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String incomingIdRequest = request.getParameter("incoming_id");
        String outgoingIdRequest = request.getParameter("outgoing_id");
        String currentRole = request.getParameter("currentRole");

        Long incomingId = Long.parseLong(incomingIdRequest);
        Long outgoingId = Long.parseLong(outgoingIdRequest);

        IUserInfoDAO userInfoDAOImpl = new UserInfoDAOImpl();

        Object outgoingUser;

        if ("staff".equals(currentRole)) {
            outgoingUser = userInfoDAOImpl.getCustomerInfoById(outgoingId);
        } else {
            outgoingUser = userInfoDAOImpl.getStaffInfoById(outgoingId);
        }

        request.setAttribute("outgoingUser", outgoingUser);
        request.setAttribute("incoming_id", incomingId);
        request.setAttribute("outgoing_id", outgoingId);
        request.setAttribute("current_role", currentRole);
        request.getRequestDispatcher("chatbox.jsp").forward(request, response);
    }
}
