<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Thực OTP</title>
    <link rel="stylesheet" href="../css/otp.css">
</head>
<body>
<div class="container">
    <h1>Xác Thực OTP</h1>
    <form action="../verifyServlet" method="post">
        <input type="hidden" name="action" value="send">
        <input type="email" name="email" value="${customerInfor.email}" required />
        <button type="submit">Gửi OTP</button>
    </form>
    <br>
    <form action="../verifyServlet" method="post">
        <input type="hidden" name="action" value="verify">
        <input type="text" name="otp" placeholder="Nhập mã OTP" required />
        <button type="submit">Xác Thực</button>
    </form>
    <%
        String messageDK = (String) session.getAttribute("messageDK");
        if (session.getAttribute("messageDK") != null) {
            out.print("<div class='alert alert-danger mt-3'>" + session.getAttribute("messageDK") + "</div>");
            session.removeAttribute("messageDK");
        }
    %>
</div>
<script src="./js/script.js"></script>
</body>
</html>
