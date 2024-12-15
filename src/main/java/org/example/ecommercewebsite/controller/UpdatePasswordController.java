package org.example.ecommercewebsite.controller;

import org.example.ecommercewebsite.DAO.ICustomerDAO;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.DAO.Impl.CustomerDAOImpl;
import org.example.ecommercewebsite.utils.MaHoa;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "UpdatePasswordServlet", value = "/updatePassword")
public class UpdatePasswordController extends HttpServlet {

    private final ICustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy thông tin người dùng từ session
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy thông tin từ form
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (currentPassword == null || newPassword == null || confirmPassword == null ||
                currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "Các trường không được để trống.");
            request.getRequestDispatcher("/updatepassword.jsp").forward(request, response);
            return;
        }

        String hashedCurrentPassword = MaHoa.toSHA1(currentPassword);
        if (!hashedCurrentPassword.equals(customer.getPassword())) {
            request.setAttribute("error", "Mật khẩu hiện tại không đúng.");
            request.getRequestDispatcher("/updatepassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            request.getRequestDispatcher("/updatepassword.jsp").forward(request, response);
            return;
        }

        if (!Customer.isValidPassword(newPassword)) {
            request.setAttribute("error", "Mật khẩu mới không hợp lệ. Vui lòng đảm bảo:\n" +
                    "- Độ dài tối thiểu: 8 ký tự\n" +
                    "- Có ít nhất một chữ cái viết thường\n" +
                    "- Có ít nhất một chữ cái viết hoa\n" +
                    "- Có ít nhất một chữ số\n" +
                    "- Có ít nhất một ký tự đặc biệt.");
            request.getRequestDispatcher("/updatepassword.jsp").forward(request, response);
            return;
        }

        String hashedNewPassword = MaHoa.toSHA1(newPassword);

        boolean updateSuccess = customerDAO.updatePassword(customer.getPersonID(), hashedNewPassword);

        if (updateSuccess) {
            customer.setPassword(hashedNewPassword);
            session.setAttribute("customer", customer);
            request.setAttribute("success", "Cập nhật mật khẩu thành công.");
            request.getRequestDispatcher("/updatepassword.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Không thể cập nhật mật khẩu. Vui lòng thử lại.");
            request.getRequestDispatcher("/updatepassword.jsp").forward(request, response);
        }
    }
}
