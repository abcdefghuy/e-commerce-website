<%--
Document   : index
Created on : Oct 16, 2024, 11:25:38 PM
Author     : HUY
--%>
<%@ page import="org.example.ecommercewebsite.constant.constant" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="stylesheet" href="../scss/login.css">
</head>
<body>
<div class="container" id="container">
    <div class="form-container sign-up-container">
        <form action="../registerServlet" method="post">
            <h1>Tạo tài khoản</h1>
            <div class="social-container">
                <a href="#" class="social"><i class="fab fa-facebook"></i></a>
                <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
                <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
            </div>
            <span>Hoặc dùng email của bạn để đăng nhập</span>
            <input type="text" name="name" placeholder="Name" required/>
            <input type="email" name="email" placeholder="Email" required/>
            <input type="password" name="password" placeholder="Password" required/>
            <%
                String messageDK = (String) session.getAttribute("messageDK");
                if (session.getAttribute("messageDK") != null) {
                    out.print("<div class='alert alert-danger mt-3'>" + session.getAttribute("messageDK") + "</div>");
                    session.removeAttribute("messageDK");
                }
            %>

            <button>Đăng ký</button>
        </form>
    </div>
    <div class="form-container sign-in-container">
        <form action="../login" method="post">
            <h1>Đăng nhập</h1>
            <div class="social-container">
                <a href="#" class="social"><i class="fab fa-facebook"></i></a>
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=${constant.GOOGLE_REDIRECT_URI}&response_type=code&client_id=121656116147-na8us5c1uluts0pmsm70bg99ggjgj3dc.apps.googleusercontent.com&approval_prompt=force" class="social"><i class="fab fa-google-plus-g"></i></a>
                <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
            </div>
            <span>Hoặc dùng tài khoản của bạn</span>
            <input type="email" placeholder="Email" name = "email" required/>
            <input type="password" placeholder="Password" name = "password" required/>
            <%
                String message = (String) session.getAttribute("message");
                if (session.getAttribute("message") != null) {
                    out.print("<div class='alert alert-danger mt-3'>" + session.getAttribute("message") + "</div>");
                    session.removeAttribute("message");
                }
            %>
            <div class="role-container">
                <label><input type="radio" name="role" value="customer" required> Customer</label>
                <label><input type="radio" name="role" value="staff" required> Staff</label>
                <label><input type="radio" name="role" value="owner" required> Owner</label>
            </div>
            <a href="forgotpass.jsp">Quên mật khẩu</a>
            <button>Đăng Nhập</button>
        </form>
    </div>
    <div class="overlay-container">
        <div class="overlay">
            <div class="overlay-panel overlay-left">
                <h1>Chào mừng trở lại</h1>
                <p>
                    Để giữ kết nói với chúng tôi vui lòng đăng nhập với thông tin cá nhân của bạn
                </p>
                <button class="ghost" id="signIn">Đăng nhập</button>
            </div>
            <div class="overlay-panel overlay-right">
                <h1>Xin chào !!!</h1>
                <p>Nhập thông tin cá nhân của bạn và bắt đầu trải nghiệm với chúng tôi</p>
                <button class="ghost" id="signUp">Đăng kí</button>
            </div>
        </div>
    </div>
</div>
<script src="../js/login.js"></script>
</body>
</html>
