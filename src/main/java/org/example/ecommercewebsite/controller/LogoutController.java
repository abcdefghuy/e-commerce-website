package org.example.ecommercewebsite.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServlet")
    public class LogoutController extends HttpServlet {
        private static final long serialVersionUID = 1L;

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // Lấy session hiện tại
            HttpSession session = request.getSession(); // `false` để không tạo session mới nếu chưa tồn tại
            if(session.getAttribute("customer") != null) {
                session.removeAttribute("customer");
            } else if (session.getAttribute("staff") != null) {
                session.removeAttribute("staff");
            } else if (session.getAttribute("owner") != null) {
                session.removeAttribute("owner");
            }

            response.sendRedirect("indexServlet");
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
        }
    }

