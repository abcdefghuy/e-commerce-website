package org.example.ecommercewebsite.controller;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.example.ecommercewebsite.DAO.IPersonDAO;
import org.example.ecommercewebsite.DAO.Impl.PersonDAOImpl;
import org.example.ecommercewebsite.business.Person;
import org.example.ecommercewebsite.mail.EmailSender;
import org.example.ecommercewebsite.mail.OTPGenerater;
import org.example.ecommercewebsite.utils.MaHoa;

@WebServlet(urlPatterns = {"/forgotPassword", "/ValidateOtp", "/newPassword"})
public class ForgotPasswordController extends HttpServlet {
    IPersonDAO personDAO = new PersonDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        RequestDispatcher dispatcher = null;

        if(action.equals("DoiMatKhau")){
            String url = "index.jsp";
            String email = request.getParameter("email");
            HttpSession session = request.getSession();

            if(!personDAO.emailExists(email)){
                url = "/KhachHang/forgotpass.jsp";
                request.setAttribute("message", "Email này không tồn tại trong hệ thống");
            }
            else{
                session.setAttribute("email", email);
                url = "/KhachHang/enterOtp.jsp";
                session.removeAttribute("otp");
                String generatedOTP = OTPGenerater.generateOTP(6);
                session.setAttribute("otp", generatedOTP);
                try{
                    EmailSender.sendOTP(email, generatedOTP);
                    session.setAttribute("otpCreationTime", System.currentTimeMillis());
                } catch(Exception e){
                    url = "/KhachHang/forgotpass.jsp";
                    request.setAttribute("message", "Có lỗi xảy ra khi gửi OTP, vui lòng thử lại.");
                }
            }

            dispatcher=request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }

        if(action.equals("XacThucOTP")){
            String url = "";
            String otp = request.getParameter("otp");
            HttpSession session = request.getSession();
            String OTPss = (String) session.getAttribute("otp");
            Long otpCreationTime = (Long) session.getAttribute("otpCreationTime");
            long otpExpiryTime = 2 * 60 * 1000;
            long currentTime = System.currentTimeMillis();

            if(OTPss != null && otpCreationTime != null && OTPss.equals(otp)){
                if(currentTime - otpCreationTime > otpExpiryTime){
                    url = "/KhachHang/enterOtp.jsp";
                    request.setAttribute("message","Mã OTP đã hết hạn");
                } else {
                    url = "/KhachHang/newPassword.jsp";
                    session.removeAttribute("otp");
                }
            } else {
                url = "/KhachHang/enterOtp.jsp";
                request.setAttribute("message", "OTP đã nhập không đúng");
            }
            dispatcher=request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }

        if(action.equals("LuuMatKhau")){
            String pass = request.getParameter("password");
            String confPass = request.getParameter("confPassword");
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            String url = "/KhachHang/newPassword.jsp";
            if(!pass.equals(confPass)){
                request.setAttribute("message", "Xác nhận mật khẩu không khớp");
            } else if (!Person.isValidPassword(pass)){
                request.setAttribute("message", "Độ dài tối thiểu: 8 ký tự.\n" +
                        "Có ít nhất một chữ cái viết thường.\n" +
                        "Có ít nhất một chữ cái viết hoa.\n" +
                        "Có ít nhất một chữ số.\n" +
                        "Có ít nhất một ký tự đặc biệt.");
            }
            else if( pass == null || confPass == null || pass.equals("") || confPass.equals("")){
                request.setAttribute("message", "Vui lòng nhập đầy đủ");
            } else {
                String passW = MaHoa.toSHA1(pass);
                if(personDAO.updatePassword(email, passW)){
                    request.setAttribute("message_success", "Đổi mật khẩu thành công");
                    session.removeAttribute("email");
                    url = "/KhachHang/login.jsp";
                } else {
                    request.setAttribute("message", "Đổi mật khẩu thất bại");
                }
            }
            dispatcher=request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }
}
