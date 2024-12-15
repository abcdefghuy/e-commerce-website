package org.example.ecommercewebsite.controller.staffManagementController;


import org.example.ecommercewebsite.DAO.IStaffDAO;
import org.example.ecommercewebsite.DAO.Impl.StaffDAOImpl;
import org.example.ecommercewebsite.config.UtilsEmail;

import org.example.ecommercewebsite.business.Address;
import org.example.ecommercewebsite.business.Staff;
import org.example.ecommercewebsite.util.AccountManagement;
import org.example.ecommercewebsite.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet("/addStaff")
@MultipartConfig
public class AddStaffController extends HttpServlet {
    private IStaffDAO staffDAO = new StaffDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/Admin/addStaff.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("emp-name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        Date birthDate = java.sql.Date.valueOf(request.getParameter("birth-date"));
        Date workDate = java.sql.Date.valueOf(request.getParameter("work-date"));
        Double salary = Double.parseDouble(request.getParameter("salary"));

        String addressCountry = request.getParameter("address-country");
        String addressCity = request.getParameter("address-city");
        String addressStreet = request.getParameter("address-street");
        String addressProvince = request.getParameter("address-province");

        Address address = new Address(addressStreet, addressCity, addressProvince, addressCountry);
        String password = AccountManagement.generatePassword(10);
        byte[] avatarBytes = ImageUtil.renderImage(request.getPart("avatar"));

        String message = "";
        boolean isSuccess = false;
        if (staffDAO.isExisted(name, phone)){
            message = "Nhân viên này đã tồn tại!";
        }else{
            try{
                Staff staff = new Staff(name, birthDate, address, email, password, phone, avatarBytes, salary, workDate);
                staffDAO.insert(staff);
                HttpSession session = request.getSession();
                List<Staff> listStaff = (List<Staff>) session.getAttribute("listStaff");
                listStaff.add(staff);
                session.setAttribute("listStaff", listStaff);
                message = "Thêm nhân viên thành công!";
                isSuccess = true;
                // gửi mail chứa password
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.submit(() -> {
                    String subject = "Tạo tài khoản thành công!";
                    String content = "Xin chào " + name + ",<br>"
                            + "Tài khoản nhân viên của bạn đã được tạo thành công.<br>"
                            + "Mật khẩu đăng nhập: " + password + "<br>"
                            + "Không chia sẻ mật khẩu này với bất kỳ ai!";
                    UtilsEmail.sendEmail(email, subject, content);
                });
                executorService.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
                message = "Thêm nhân viên thất bại!";
            }
        }
        request.setAttribute("message", message);
        request.setAttribute("isSuccess", isSuccess);
        request.getRequestDispatcher("/Admin/addStaff.jsp").forward(request, response);
    }
}
