package org.example.ecommercewebsite.controller.staffManagementController;

import org.example.ecommercewebsite.DAO.IStaffDAO;
import org.example.ecommercewebsite.DAO.Impl.StaffDAOImpl;
import org.example.ecommercewebsite.business.Staff;
import org.example.ecommercewebsite.config.UtilsEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet("/deleteStaff")
public class DeleteStaffController extends HttpServlet {
    IStaffDAO staffDAO = new StaffDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long staffId = Long.parseLong(request.getParameter("emp-id"));
        Staff staff = staffDAO.getStaffById(staffId);

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        List<Staff> listStaff = (List<Staff>) session.getAttribute("listStaff");
        if (action.equals("delete")) {
            staff.setStatus("InActive");

            for (Staff s : listStaff) {
                if (Objects.equals(s.getPersonID(), staff.getPersonID())) {
                    s.setStatus("InActive");
                    break;
                }
            }
        }
        else if (action.equals("restore")) {
            staff.setStatus("Active");

            for (Staff s : listStaff) {
                if (Objects.equals(s.getPersonID(), staff.getPersonID())) {
                    s.setStatus("Active");
                    break;
                }
            }
        }
        //StaffDAO.delete(staff);
        staffDAO.update(staff);

        //gửi mail thông báo xóa tài khoản
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            String subject = "Tạo tài khoản thành công!";
            String content = "Xin chào " + staff.getName() + ",<br>"
                    + "Tài khoản nhân viên của bạn đã bị xóa khỏi hệ thống.<br>"
                    + "Cảm ơn bạn đã làm việc tại hệ thống trong thời gian qua." + "<br>"
                    + "Mọi thắc mắc vui lòng liên hệ Đặng Bá Hiền (0xxx-xxx-xxx)!";
            UtilsEmail.sendEmail(staff.getEmail(), subject, content);
        });
        executorService.shutdown();

        session.setAttribute("listStaff", listStaff);
        response.sendRedirect("listStaff");
    }
}
