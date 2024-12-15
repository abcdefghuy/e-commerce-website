package org.example.ecommercewebsite.controller.chat;

import org.example.ecommercewebsite.DAO.IChatDAO;
import org.example.ecommercewebsite.DAO.Impl.ChatDAOImpl;
import org.example.ecommercewebsite.business.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Staff/getChat")
public class GetChatController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String outgoingIDRequest = request.getParameter("outgoing_id");
        String incomingIDRequest = request.getParameter("incoming_id");

        Long outgoingID = Long.parseLong(outgoingIDRequest);
        Long incomingID = Long.parseLong(incomingIDRequest);

        if (outgoingID == null || incomingID == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        IChatDAO chatDAOImpl = new ChatDAOImpl();
        List<Message> chatHistory = chatDAOImpl.getChatHistory(outgoingID, incomingID);


        StringBuilder chatHTML = new StringBuilder();
        for (Message message : chatHistory) {
            if (message.getOutgoingMsgID().equals(outgoingID)) {
                chatHTML.append("<div class='chat outgoing'><div class='details'><p>")
                        .append(message.getMsg())
                        .append("</p></div></div>");
            } else {
                chatHTML.append("<div class='chat incoming'><div class='details'><p>")
                        .append(message.getMsg())
                        .append("</p></div></div>");
            }
        }

        response.setContentType("text/html");
        response.getWriter().write(chatHTML.toString());
    }
}
