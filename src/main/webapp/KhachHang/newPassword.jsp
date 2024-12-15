<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đổi mật khẩu</title>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <style>
        .placeicon {
            font-family: fontawesome;
        }

        .custom-control-label::before {
            background-color: #dee2e6;
            border: #dee2e6;
        }

        .form-container {
            border: 2px solid #17a2b8; /* Màu border */
            border-radius: 10px;       /* Bo góc */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Đổ bóng */
            padding: 20px;            /* Khoảng cách bên trong */
            background-color: #B0C4DE; /* Nền trắng */
        }
    </style>
</head>
<body oncontextmenu="return false" class="snippet-body bg-white">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-social/5.1.1/bootstrap-social.css">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-12 col-md-9 col-lg-7 col-xl-6 mt-5">
            <!-- Form Container -->
            <div class="form-container">
                <!-- Main Heading -->
                <div class="row justify-content-center align-items-center pt-3">
                    <h1><strong>Đổi mật khẩu</strong></h1>
                </div>

                <div class="pt-3 pb-3">
                    <form class="form-horizontal" action="newPassword" method="POST">
                        <input type="hidden" name="action" value="LuuMatKhau">

                        <!-- New Password Input -->
                        <div class="form-group row justify-content-center px-3">
                            <div class="col-9 px-0">
                                <input type="password" name="password" placeholder="&#xf084; &nbsp; Mật khẩu mới" class="form-control border-info placeicon">
                            </div>
                        </div>

                        <!-- Confirm New Password Input -->
                        <div class="form-group row justify-content-center px-3">
                            <div class="col-9 px-0">
                                <input type="password" name="confPassword" placeholder="&#xf084; &nbsp; Xác nhận mật khẩu mới" class="form-control border-info placeicon">
                            </div>
                        </div>

                        <!-- Display Message -->
                        <%
                            if (request.getAttribute("message") != null) {
                                out.print("<div class='alert alert-danger mt-3'>" + request.getAttribute("message") + "</div>");
                            }
                        %>

                        <%
                            if (request.getAttribute("message_success") != null) {
                                out.print("<div class='alert alert-success mt-3'>" + request.getAttribute("message_success") + "</div>");
                            }
                        %>

                        <!-- Submit Button -->
                        <div class="form-group row justify-content-center">
                            <div class="col-3 px-3 mt-3">
                                <input type="submit" value="Xác nhận" class="btn btn-info btn-block">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
