<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="POS - Bootstrap Admin Template">
    <meta name="keywords" content="admin, estimates, bootstrap, Entity, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <style>
        /* Tùy chỉnh font chữ và khung cho các ô nhập */
        .form-control {
            font-family: 'Arial', sans-serif;
            font-size: 14px;
            border: 1.5px solid #ccc;
            border-radius: 5px;
            padding: 10px;
        }
        input[type="date"],
        input[type="email"] {
            font-family: 'Arial', sans-serif;
            font-size: 14px;
            border: 1.5px solid #007bff;
            border-radius: 5px;
            padding: 10px;
        }
        input[type="date"]:focus,
        input[type="email"]:focus {
            border-color: #0056b3;
            box-shadow: 0 0 5px rgba(0, 86, 179, 0.5);
        }
    </style>
</head>
<body>
<div id="global-loader">
    <div class="whirly-loader"></div>
</div>

<div class="main-wrapper">
    <div class="header">
        <!-- Header content remains unchanged -->
    </div>

    <!-- Sidebar and Main Content -->
    <div class="sidebar" id="sidebar">
        <div class="sidebar-inner slimscroll">
            <div id="sidebar-menu" class="sidebar-menu">
                <ul>
                    <li>
                        <a href="index.jsp"><img src="assets/img/icons/dashboard.svg" alt="img"><span> Dashboard</span> </a>
                    </li>
                </ul>
                <ul>
                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/dashboard.svg" alt="img"><span> Thống Kê</span> <span class="menu-arrow"></span> </a>
                        <ul>
                            <li><a href="index1.jsp">Doanh Thu</a></li>
                            <li><a href="index2.jsp">Sản Phẩm Bán Chạy</a></li>
                            <li><a href="index3.jsp">Trạng Thái Đơn Hàng</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="coupon.jsp"><img src="assets/img/icons/coupon.svg" alt="img"><span> Tạo Mã Khuyến Mãi</span> </a>
                    </li>
                </ul>
                <ul>
                    <li >
                        <a href="listStaff.jsp"><img src="assets/img/icons/dashboard.svg" alt="img" class="active"><span> Quản lý nhân viên</span> </a>
                    </li>
                </ul>
                <ul>
                    <li >
                        <a href="listCustomer.jsp"><img src="assets/img/icons/dashboard.svg" alt="img"><span> Quản lý Khách Hàng</span> </a>
                    </li>
                </ul>
                <ul>
                    <li>
                        <a href="setShift.jsp"><img src="assets/img/icons/dashboard.svg" alt="img" class=""><span>Phân ca làm việc</span> </a>
                    </li>
                </ul>
                <ul>
                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/product.svg" alt="img"><span> Sản phẩm</span> <span class="menu-arrow"></span></a>
                        <ul>
                            <li><a href="productlist.jsp">Danh sách sản phẩm</a></li>
                            <li><a href="addproduct.jsp">Thêm sản phẩm</a></li>
                            <li><a href="categorylist.jsp">Danh sách Danh mục sản phẩm</a></li>
                            <li><a href="addcategory.jsp">Thêm danh mục sản phẩm</a></li>
                        </ul>
                    </li>
                </ul>
                <ul>
                    <li>
                        <a href="profile.jsp" class="active"><img src="assets/img/icons/dashboard.svg" alt="img" class=""><span>Thông tin cá nhân</span> </a>
                    </li>
                </ul>
                <ul>
                    <li>
                        <a href="updatepassword.jsp"><img src="assets/img/icons/dashboard.svg" alt="img" class=""><span>Cập nhập mật khẩu</span> </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>


    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Profile</h4>
                    <h6>User Profile</h6>
                </div>
            </div>
            <!-- Profile Form -->
            <div class="card">
                <div class="card-body">
                    <div class="profile-set">
                        <div class="profile-top">
                            <div class="profile-content">
                                <div class="profile-contentimg">
                                    <img src="assets/img/customer/customer5.jpg" alt="img" id="blah">
                                    <div class="profileupload">
                                        <input type="file" id="imgInp">
                                        <a href="javascript:void(0);"><img src="assets/img/icons/edit-set.svg" alt="img"></a>
                                    </div>
                                </div>
                                <div class="profile-contentname">
                                    <h2>William Castillo</h2>
                                    <h4>Update Your Photo and Personal Details.</h4>
                                </div>
                            </div>
                            <div class="ms-auto">
                                <a href="javascript:void(0);" class="btn btn-submit me-2">Save</a>
                                <a href="javascript:void(0);" class="btn btn-cancel">Cancel</a>
                            </div>
                        </div>
                    </div>
                    <!-- Form Fields -->
                    <div class="row">
                        <div class="col-lg-6 col-sm-12">
                            <div class="form-group">
                                <label>Full Name</label>
                                <input type="text" class="form-control" placeholder="Enter your full name">
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="form-group">
                                <label>Phone Number</label>
                                <input type="text" class="form-control" placeholder="Enter your phone number">
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="form-group">
                                <label>Date of Birth</label>
                                <input type="date" class="form-control" placeholder="Select your date of birth">
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="form-group">
                                <label>Address</label>
                                <input type="text" class="form-control" placeholder="Enter your address">
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="form-group">
                                <label>Gender</label>
                                <select class="form-control">
                                    <option value="">Select Gender</option>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Other">Other</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="form-group">
                                <label>Email</label>
                                <input type="email" class="form-control" placeholder="Enter your email">
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="form-group">
                                <label>Username</label>
                                <input type="text" class="form-control" placeholder="Enter your username">
                            </div>
                        </div>
                        <!-- Button "Change Password" Added -->
                        <div class="ms-auto">
                            <a href="javascript:void(0);" class="btn btn-submit me-2">Change Password</a> <!-- New Button Added -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Scripts -->
<script src="assets/js/jquery-3.6.0.min.js"></script>
<script src="assets/js/feather.min.js"></script>
<script src="assets/js/jquery.slimscroll.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/plugins/select2/js/select2.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/js/script.js"></script>
</body>
</html>
