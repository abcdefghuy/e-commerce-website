<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="author" content="Untree.co">
        <link rel="shortcut icon" href="favicon.png">

        <meta name="description" content="" />
        <meta name="keywords" content="bootstrap, bootstrap4" />

        <!-- Bootstrap CSS -->
        <link href="../css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <link href="../css/tiny-slider.css" rel="stylesheet">
        <link href="../css/style.css" rel="stylesheet">
        <link href="../css/feedback.css" rel="stylesheet">

        <script src="../js/feedback.js"></script>

        <title>Furni Free Bootstrap 5 Template for Furniture and Interior Design Websites by Untree.co </title>
    </head>

    <body>

        <c:import url="../includes/header.jsp" />
        <%Long orderId = Long.parseLong(request.getParameter("orderId"));%>
        <%Long customerId = Long.parseLong(request.getParameter("customerId"));%>

        <!-- Start Hero Section -->
        <div class="hero">
            <div class="container">
                <div class="row justify-content-between">
                    <div class="col-lg-5">
                        <div class="intro-excerpt">
                            <h1>Đánh giá đơn hàng</h1>
                        </div>
                    </div>
                    <div class="col-lg-7">

                    </div>
                </div>
            </div>
        </div>
        <!-- End Hero Section -->

        <div class="feedback-container">
            <h2>Phản hồi của bạn</h2>
            <div class="emojis-rate">
                <img src="../images/rate1.png" alt="Very Dissatisfied" class="emoji-rate" onclick="selectIcon(this, 1)">
                <img src="../images/rate2.png" alt="Dissatisfied" class="emoji-rate" onclick="selectIcon(this, 2)">
                <img src="../images/rate3.png" alt="Neutral" class="emoji-rate" onclick="selectIcon(this, 3)">
                <img src="../images/rate4.png" alt="Satisfied" class="emoji-rate" onclick="selectIcon(this, 4)">
                <img src="../images/rate5.png" alt="Very Satisfied" class="emoji-rate" onclick="selectIcon(this, 5)">
            </div>
            <textarea id="textFeedback" class="text-feedback" placeholder="Nhập phản hồi của bạn ở đây..."></textarea>
            <div class="image-upload-container">
                <h2>Chọn tối đa 5 ảnh</h2>
                <input type="file" id="imageInput" accept="image/*" multiple>
                <div id="previewContainer"></div>
            </div>
            <button type="button" class="btn-feedback" onclick="feedbackOrder(<%= customerId %>, <%= orderId %>)">Gửi</button>
        </div>

        <c:import url="../includes/footer.jsp" />

        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/tiny-slider.js"></script>
        <script src="../js/custom.js"></script>
    </body>

</html>
