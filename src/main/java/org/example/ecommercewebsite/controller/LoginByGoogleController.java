package org.example.ecommercewebsite.controller;


import org.example.ecommercewebsite.DAO.ICustomerDAO;
import org.example.ecommercewebsite.DAO.Impl.CustomerDAOImpl;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.googlelogin.GoogleLogin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "loginGG", value = "/loginGG")
public class LoginByGoogleController extends HttpServlet {
    ICustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String code = request.getParameter("code");
        GoogleLogin googleLogin = new GoogleLogin();
        String accessToken = googleLogin.getToken(code);
        Customer newCustomer = googleLogin.getUserInfo(accessToken);
        String url = "";
        HttpSession session = request.getSession();

        Customer customer = customerDAO.getCustomerByGoogleLogin(newCustomer.getGoogleLogin());
        if (customer != null) {
            session.setAttribute("customer", customer);

            if (!isProfileCompleteCus(customer)) {
                String displayName = customer.getName();
                String displayEmail = customer.getGoogleLogin();
                session.setAttribute("displayName", displayName);
                session.setAttribute("displayEmail", displayEmail);
                url = "../indexServlet";
            } else {
                url = "../indexServlet";
            }
        } else {
            // Thêm khách hàng mới
            newCustomer.setStatus("Active");
           int i = customerDAO.insert(newCustomer);
            session.setAttribute("customer", newCustomer);

            // Kiểm tra hồ sơ của khách hàng mới
            if (!isProfileCompleteCus(newCustomer)) {
                url = "/KhachHang/saveProfile.jsp"; // Chuyển đến trang hoàn thiện hồ sơ
            } else {
                url = "../indexServlet";
            }
        }
        response.sendRedirect(request.getContextPath() + url);
    }
    private boolean isProfileCompleteCus(Customer customer) {
        return customer.getPhone() != null &&
                customer.getAddress() != null ;
    }

}
