package org.example.ecommercewebsite.controller;


import org.example.ecommercewebsite.DAO.ICustomerDAO;
import org.example.ecommercewebsite.DAO.Impl.CustomerDAOImpl;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.utils.ValidationUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/registerServlet")
public class RegisterController extends HttpServlet {
    ICustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/KhachHang/verifyEmail.jsp";
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String messageDK = "";
        if (name == null || name.equals("") || email == null || email.equals("") || password == null || password.equals("")) {
            url = "/KhachHang/login.jsp";
            messageDK = "Vui lòng nhập đủ thông tin";
        }
        else if  (!ValidationUtils.isValidEmail(email)){
            url = "/KhachHang/login.jsp";
            messageDK = "Vui lòng nhập đúng định dạng email";
        }
        else if (customerDAO.emailExists(email)){
            url = "/KhachHang/login.jsp";
            messageDK = "Email này đã được đăng kí trong hệ thống";
        }

        // Xử lý khi một trong các biến là null hoặc rỗng

        Customer cus = new Customer (name,email,password);
        session.setAttribute("customerInfor", cus);
        session.setAttribute("messageDK", messageDK);
        response.sendRedirect(request.getContextPath()+url);


    }

}