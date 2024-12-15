package org.example.ecommercewebsite.controller.chat;

import org.example.ecommercewebsite.DAO.IChatDAO;
import org.example.ecommercewebsite. DAO.Impl.ChatDAOImpl;
import org.example.ecommercewebsite.business.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/Staff/insertChat")
public class InsertChatController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String outgoingIDRequest = request.getParameter("outgoing_id");
        String incomingIDRequest = request.getParameter("incoming_id");

        Long outgoingID = Long.parseLong(outgoingIDRequest);
        Long incomingID = Long.parseLong(incomingIDRequest);


        String message = request.getParameter("message");

        if (outgoingID == null || incomingID == null || message == null || message.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Tạo đối tượng Message
        Message newMessage = new Message(incomingID, outgoingID, message, LocalDateTime.now());

        // Lưu vào database
        IChatDAO chatDAOImpl = new ChatDAOImpl();
        boolean isInserted = chatDAOImpl.insertMessage(newMessage);

        if (isInserted) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
