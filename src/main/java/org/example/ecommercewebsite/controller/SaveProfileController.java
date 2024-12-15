package org.example.ecommercewebsite.controller;

import org.example.ecommercewebsite.DAO.ICustomerDAO;
import org.example.ecommercewebsite.DAO.IStaffDAO;
import org.example.ecommercewebsite.DAO.Impl.CustomerDAOImpl;
import org.example.ecommercewebsite.DAO.Impl.StaffDAOImpl;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Staff;
import org.example.ecommercewebsite.business.Address;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "SaveProfileControl", value = "/saveProfile")
@MultipartConfig
public class SaveProfileController extends HttpServlet {

    private final ICustomerDAO customerDAO = new CustomerDAOImpl();
    private final IStaffDAO staffDAO = new StaffDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy thông tin người dùng từ session
        Customer customer = (Customer) session.getAttribute("customer");
        Staff staff = (Staff) session.getAttribute("staff");
        String url = "";
        if (customer == null && staff == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not logged in.");
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String birthDateStr = request.getParameter("birthDate");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String province = request.getParameter("province");
            String country = request.getParameter("country");
            Address address = new Address(street, city, province, country);

            Part part = request.getPart("profileImage");
            byte[] profileImage = null;
            if (part != null && part.getSize() > 0) {
                profileImage = toByteArray(part.getInputStream());
            }

            if (customer != null) {
                updateCustomer(customer, name, phone, birthDateStr, address, profileImage);
                session.setAttribute("person", customer);
                url = "../indexServlet";
            } else if (staff != null) {
                updateStaff(staff, name, phone, birthDateStr, address, profileImage);
                session.setAttribute("person", staff);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Profile updated successfully.");
            response.sendRedirect(request.getContextPath() + url + "?status=success");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error while saving profile: " + e.getMessage());
        }
    }

    private void updateCustomer(Customer customer, String name, String phone, String birthDateStr, Address address, byte[] profileImage) {
        if (name != null && !name.isEmpty()) customer.setName(name);
        if (phone != null && !phone.isEmpty()) customer.setPhone(phone);
        customer.setAddress(address);
        if (profileImage != null) customer.setAvatar(profileImage);
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            try {
                customer.setBirthDate(java.sql.Date.valueOf(birthDateStr));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid birth date format: " + e.getMessage());
            }
        }
        customerDAO.updateCustomer(customer);
    }

    private void updateStaff(Staff staff, String name, String phone, String birthDateStr, Address address, byte[] profileImage) {
        if (name != null && !name.isEmpty()) staff.setName(name);
        if (phone != null && !phone.isEmpty()) staff.setPhone(phone);
        staff.setAddress(address);
        if (profileImage != null) staff.setAvatar(profileImage);
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            try {
                staff.setBirthDate(java.sql.Date.valueOf(birthDateStr));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid birth date format: " + e.getMessage());
            }
        }
        staffDAO.update(staff);
    }

    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

}
