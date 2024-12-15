package org.example.ecommercewebsite.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;

import org.example.ecommercewebsite.DAO.ICustomerDAO;
import org.example.ecommercewebsite.DAO.IOwnerDAO;
import org.example.ecommercewebsite.DAO.IStaffDAO;
import org.example.ecommercewebsite.DAO.Impl.CustomerDAOImpl;
import org.example.ecommercewebsite.DAO.Impl.OwnerDAOImpl;
import org.example.ecommercewebsite.DAO.Impl.StaffDAOImpl;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Staff;
import org.example.ecommercewebsite.business.Owner;

import org.example.ecommercewebsite.utils.MaHoa;

@WebServlet(name = "login", value = "/login")
public class LoginController extends HttpServlet {
    IStaffDAO staffDAO = new StaffDAOImpl();
    ICustomerDAO customerDAO = new CustomerDAOImpl();
    IOwnerDAO ownerDAO = new OwnerDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/KhachHang/login.jsp";
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String role = request.getParameter("role");
        String message = "";
        HttpSession session = request.getSession();

        if (email == null || email.equals("") || pass == null || pass.equals("")) {
            message = "Vui lòng nhập đủ thông tin";
        } else {
            if (role.equals("customer")) {
                String passW = MaHoa.toSHA1(pass);
                Customer customer = customerDAO.getCustomerByEmailPass(email, passW);
                if (customer == null || customer.getStatus().equals("InActive")) {
                    message = "Sai tài khoản hoặc mật khẩu";
                } else {
                    session.setAttribute("customer", customer);

                    // Load name and email for saveProfile.jsp
                    String displayName = customer.getName();
                    String displayEmail = (customer.getEmail() != null && !customer.getEmail().isEmpty()) ? customer.getEmail() : customer.getGoogleLogin();
                    session.setAttribute("displayName", displayName);
                    session.setAttribute("displayEmail", displayEmail);

                    if (!isProfileCompleteCus(customer)) {
                        url = "/KhachHang/saveProfile.jsp";
                    } else {
                        url = "../indexServlet";
                    }
                }
            } else if (role.equals("staff")) {
                Staff staff = staffDAO.getStaffByEmailPass(email, pass);
                if (staff == null || staff.getStatus().equals("InActive")) {
                    message = "Sai tài khoản hoặc mật khẩu";
                } else {
                    session.setAttribute("staff", staff);

                    // Load name and email for saveProfile.jsp
                    String displayName = staff.getName();
                    String displayEmail = staff.getEmail();
                    session.setAttribute("displayName", displayName);
                    session.setAttribute("displayEmail", displayEmail);

                    if (!isProfileCompleteSta(staff)) {
                        url = "/KhachHang/saveProfile.jsp";
                    } else {
                        url = "/Staff/dashboard.jsp";
                    }
                }
            } else if (role.equals("owner")) {
                Owner owner = ownerDAO.getOwnerByEmailPass(email, pass);
                if (owner == null) {
                    message = "Sai tài khoản hoặc mật khẩu";
                } else {
                    session.setAttribute("owner", owner);
                    url = "/listStaff";
                }
            } else {
                message = "Vui lòng chọn vai trò của bạn";
            }
        }

        session.setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + url);
    }

    /**
     */
    private boolean isProfileCompleteCus(Customer customer) {
        return customer.getPhone() != null &&
                customer.getAddress() != null;
    }

    /**
     * Kiểm tra xem hồ sơ của Staff có đầy đủ thông tin chưa.
     */
    private boolean isProfileCompleteSta(Staff staff) {
        return staff.getPhone() != null && !staff.getPhone().isEmpty() &&
                staff.getAddress() != null && staff.getAddress().isComplete();
    }
}
