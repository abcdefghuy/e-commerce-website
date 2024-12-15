

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quên mật khẩu</title>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <style>
        body {
            background-color: #eee;
            color: #505050;
            font-family: "Rubik", Helvetica, Arial, sans-serif;
            font-size: 14px;
            line-height: 1.5;
        }

        .forgot {
            background-color: #fff;
            padding: 12px;
            border: 1px solid #dfdfdf;
        }

        .padding-bottom-3x {
            padding-bottom: 72px !important;
        }

        .btn {
            font-size: 13px;
        }

        .form-control:focus {
            border-color: #76b7e9;
            outline: 0;
            box-shadow: 0 0 0 0px #28a745;
        }
    </style>
</head>
<body class="snippet-body">
<div class="container padding-bottom-3x mb-2 mt-5">
    <div class="col-lg-8 col-md-10 mx-auto">
        <form class="card mt-4" action="../forgotPassword" method="POST">
            <div class="card-body">
                <input type="hidden" name="action" value="DoiMatKhau">
                <label for="email-for-pass">Nhập địa chỉ email của bạn</label>
                <input class="form-control" type="text" name="email" id="email-for-pass" required>
                <small class="form-text text-muted">
                    Nhập địa chỉ email đã đăng ký. Sau đó chúng tôi sẽ gửi email OTP đến địa chỉ này.
                </small>

                <%
                    if (request.getAttribute("message") != null) {
                        out.print("<div class='alert alert-danger mt-3'>" + request.getAttribute("message") + "</div>");
                    }
                %>
            </div>

            <div class="card-footer">
                <button class="btn btn-success" type="submit">Gửi mã OTP</button>
            </div>
        </form>
    </div>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
