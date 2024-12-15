package org.example.ecommercewebsite.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.example.ecommercewebsite.DAO.ICustomerDAO;
import org.example.ecommercewebsite.DAO.Impl.CustomerDAOImpl;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.mail.OTPGenerater;
import org.example.ecommercewebsite.mail.EmailSender;
import org.example.ecommercewebsite.utils.MaHoa;

@WebServlet(name = "VerifyServlet", value = "/verifyServlet")
public class VerifyController extends HttpServlet {
    ICustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String url = "/KhachHang/verifyEmail.jsp";
        String messageDK = "";
        HttpSession session = request.getSession();

        if (action.equals("send")){

            session.removeAttribute("otp");

            String email = request.getParameter("email");

            if (!email.equals("")){
                String generatedOTP = OTPGenerater.generateOTP(6);
                session.setAttribute("otp", generatedOTP);

                if (EmailSender.sendOTP(email, generatedOTP)){
                    messageDK = "Vui lòng kiểm tra email để lấy OTP";
                    session.setAttribute("otpCreationTime", System.currentTimeMillis());
                }
                else
                {
                    messageDK = "Không thể gửi email do lỗi hệ thống";
                }

            }
            else{
                messageDK = "Vui lòng nhập email";
            }


        }
        else if (action.equals("verify")){

            String otpEmail = (String) session.getAttribute("otp");
            String otp = request.getParameter("otp");
            Customer cus = (Customer) session.getAttribute("customerInfor");
            String mkMahHoa = MaHoa.toSHA1(cus.getPassword());
            cus.setPassword(mkMahHoa);

            Long otpCreationTime = (Long) session.getAttribute("otpCreationTime");
            long otpExpiryTime = 2 * 60 * 1000;
            long currentTime = System.currentTimeMillis();

            if(currentTime - otpCreationTime > otpExpiryTime){
                messageDK = "Hết hạn OTP";
            }
            else{
                if (otpEmail!= null && otpEmail.equals(otp)){
                    // ngay bước này nếu không có user thì phải trả lại
                    if (cus != null){
                        cus.setStatus("Active");
                        if(customerDAO.insert(cus) > 0){
                            session.removeAttribute("otp");
                            session.removeAttribute("customerInfor");
                            messageDK = "Đăng kí tài khoản thành công";
                            url = "/KhachHang/login.jsp";
                        }
                        else{
                            messageDK = "Lỗi hệ thống";
                        }
                    }
                    else
                    {
                        messageDK = "Vui lòng nhập thông tin ở trang đăng kí trước";
                    }
                }
                else{
                    messageDK = "Mã OTP không hợp lệ vui lòng nhập lại";
                }
            }
        }
        session.setAttribute("messageDK", messageDK);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
