<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.example.ecommercewebsite.utils.LabelConverter" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Bootstrap Admin Template">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Xử lý đơn</title>

    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/img/favicon.jpg">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/select2/css/select2.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-datetimepicker.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dataTables.bootstrap4.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/all.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<div class="main-wrapper">

    <div class="header">

        <div class="header-left active">
            <a href="${pageContext.request.contextPath}/Staff/dashboard.jsp" class="logo">
                <img src="${pageContext.request.contextPath}/assets/img/logo.png" alt="">
            </a>
            <a href="${pageContext.request.contextPath}/Staff/dashboard.jsp" class="logo-small">
                <img src="${pageContext.request.contextPath}/assets/img/logo-small.png" alt="">
            </a>
            <a id="toggle_btn" href="javascript:void(0);">
            </a>
        </div>

        <a id="mobile_btn" class="mobile_btn" href="#sidebar">
    <span class="bar-icon">
    <span></span>
    <span></span>
    <span></span>
    </span>
        </a>

        <ul class="nav user-menu">

            <li class="nav-item">
                <div class="top-nav-search">
                    <a href="javascript:void(0);" class="responsive-search">
                        <i class="fa fa-search"></i>
                    </a>
                    <form action="#">
                        <div class="searchinputs">
                            <input type="text" placeholder="Search Here ...">
                            <div class="search-addon">
                                <span><img src="${pageContext.request.contextPath}/assets/img/icons/closes.svg" alt="img"></span>
                            </div>
                        </div>
                        <a class="btn" id="searchdiv"><img src="${pageContext.request.contextPath}/assets/img/icons/search.svg" alt="img"></a>
                    </form>
                </div>
            </li>


            <li class="nav-item dropdown has-arrow flag-nav">
                <div class="dropdown-menu dropdown-menu-right">
                    <a href="javascript:void(0);" class="dropdown-item">
                        <img src="${pageContext.request.contextPath}/assets/img/flags/us.png" alt="" height="16"> English
                    </a>
                    <a href="javascript:void(0);" class="dropdown-item">
                        <img src="${pageContext.request.contextPath}/assets/img/flags/fr.png" alt="" height="16"> French
                    </a>
                    <a href="javascript:void(0);" class="dropdown-item">
                        <img src="${pageContext.request.contextPath}/assets/img/flags/es.png" alt="" height="16"> Spanish
                    </a>
                    <a href="javascript:void(0);" class="dropdown-item">
                        <img src="${pageContext.request.contextPath}/assets/img/flags/de.png" alt="" height="16"> German
                    </a>
                </div>
            </li>


            <li class="nav-item dropdown">
                <a href="javascript:void(0);" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
                    <img src="${pageContext.request.contextPath}/assets/img/icons/notification-bing.svg" alt="img"> <span
                        class="badge rounded-pill">4</span>
                </a>
                <div class="dropdown-menu notifications">
                    <div class="topnav-dropdown-header">
                        <span class="notification-title">Notifications</span>
                        <a href="javascript:void(0)" class="clear-noti"> Clear All </a>
                    </div>
                    <div class="noti-content">
                        <ul class="notification-list">
                            <li class="notification-message">
                                <a href="">
                                    <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="${pageContext.request.contextPath}/assets/img/profiles/avatar-02.jpg">
</span>
                                        <div class="media-body flex-grow-1">
                                            <p class="noti-details"><span class="noti-title">John Doe</span> added new
                                                task <span class="noti-title">Patient appointment booking</span></p>
                                            <p class="noti-time"><span class="notification-time">4 mins ago</span></p>
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="notification-message">
                                <a href="">
                                    <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="${pageContext.request.contextPath}/assets/img/profiles/avatar-03.jpg">
</span>
                                        <div class="media-body flex-grow-1">
                                            <p class="noti-details"><span class="noti-title">Tarah Shropshire</span>
                                                changed the task name <span class="noti-title">Appointment booking with payment gateway</span>
                                            </p>
                                            <p class="noti-time"><span class="notification-time">6 mins ago</span></p>
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="notification-message">
                                <a href="">
                                    <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="${pageContext.request.contextPath}/assets/img/profiles/avatar-06.jpg">
</span>
                                        <div class="media-body flex-grow-1">
                                            <p class="noti-details"><span class="noti-title">Misty Tison</span> added
                                                <span class="noti-title">Domenic Houston</span> and <span
                                                        class="noti-title">Claire Mapes</span> to project <span
                                                        class="noti-title">Doctor available module</span></p>
                                            <p class="noti-time"><span class="notification-time">8 mins ago</span></p>
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="notification-message">
                                <a href="">
                                    <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="assets/img/profiles/avatar-17.jpg">
</span>
                                        <div class="media-body flex-grow-1">
                                            <p class="noti-details"><span class="noti-title">Rolland Webber</span>
                                                completed task <span class="noti-title">Patient and Doctor video conferencing</span>
                                            </p>
                                            <p class="noti-time"><span class="notification-time">12 mins ago</span></p>
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="notification-message">
                                <a href="activities.html">
                                    <div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="assets/img/profiles/avatar-13.jpg">
</span>
                                        <div class="media-body flex-grow-1">
                                            <p class="noti-details"><span class="noti-title">Bernardo Galaviz</span>
                                                added new task <span class="noti-title">Private chat module</span></p>
                                            <p class="noti-time"><span class="notification-time">2 days ago</span></p>
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="topnav-dropdown-footer">
                        <a href="activities.html">View all Notifications</a>
                    </div>
                </div>
            </li>

            <li class="nav-item dropdown has-arrow main-drop">
                <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
<span class="user-img"><img src="assets/img/profiles/avator1.jpg" alt="">
<span class="status online"></span></span>
                </a>
                <div class="dropdown-menu menu-drop-user">
                    <div class="profilename">
                        <div class="profileset">
<span class="user-img"><img src="assets/img/profiles/avator1.jpg" alt="">
<span class="status online"></span></span>
                            <div class="profilesets">
                                <h6>John Doe</h6>
                                <h5>Admin</h5>
                            </div>
                        </div>
                        <hr class="m-0">
                        <a class="dropdown-item" href="profile.html"> <i class="me-2" data-feather="user"></i> My
                            Profile</a>
                        <a class="dropdown-item" href="generalsettings.html"><i class="me-2"
                                                                                data-feather="settings"></i>Settings</a>
                        <hr class="m-0">
                        <a class="dropdown-item logout pb-0" href="signin.html"><img src="assets/img/icons/log-out.svg"
                                                                                     class="me-2" alt="img">Logout</a>
                    </div>
                </div>
            </li>
        </ul>

        <%--Profile--%>
        <div class="dropdown mobile-user-menu">
            <a href="javascript:void(0);" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
               aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" href="profile.html">My Profile</a>
                <a class="dropdown-item" href="generalsettings.html">Settings</a>
                <a class="dropdown-item" href="signin.html">Logout</a>
            </div>
        </div>

    </div>


    <%----------------------------------Sidebar----------------------------------%>
    <div class="sidebar" id="sidebar">
        <div class="sidebar-inner slimscroll">
            <div id="sidebar-menu" class="sidebar-menu">
                <ul>
                    <li class="active">
                        <a href="dashboard.jsp"><img src="${pageContext.request.contextPath}/assets/img/icons/dashboard.svg" alt="img"><span> Trang chủ</span> </a>
                    </li>
                    <li class="submenu">


                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/assets/img/icons/quotation1.svg" alt="img"><span> Công việc </span> <span class="menu-arrow"></span></a>

                        <ul>

                            <li><a href="${pageContext.request.contextPath}/Staff/quotationList">Xử lý đơn</a></li>
                            <li><a href="${pageContext.request.contextPath}/Staff/loadCustomerList">Tư vấn khách hàng</a></li>
                            <%--<li><a href="${pageContext.request.contextPath}/Staff/loadStaffChatList">Chat (KH)</a></li>--%>

                        </ul>
                    </li>
                    <li class="submenu">

                </ul>
            </div>
        </div>
    </div>
    <%----------------------------------End sidebar----------------------------------%>


    <%-----------------------------------Main content-----------------------------------%>
    <div class="page-wrapper">
        <div class="content">

            <div class="page-header">
                <div class="page-title">
                    <h4>Đơn Hàng</h4>
                    <h6>Quản lý đơn hàng</h6>
                </div>
            </div>

            <div class="card">
                <div class="card-body">

                    <%---------------------------------Table top-----------------------------------%>
                    <div class="table-top">
                        <div class="search-set">

                            <%-------------------------Btn filter-------------------------%>
                            <div class="search-path">
                                <a class="btn btn-filter" id="filter_search">
                                    <img src="${pageContext.request.contextPath}/assets/img/icons/filter.svg" alt="img">
                                    <span><img src="${pageContext.request.contextPath}/assets/img/icons/closes.svg" alt="img"></span>
                                </a>
                            </div>

                            <%-------------------------Btn search-------------------------%>
                            <div class="search-input">
                                <a class="btn btn-searchset"><img src="${pageContext.request.contextPath}/assets/img/icons/search-white.svg" alt="img"></a>
                            </div>
                        </div>

                        <%-------------------------pdf, exel, print-------------------------%>
                        <div class="wordset">
                            <ul>
                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="pdf"><img
                                            src="${pageContext.request.contextPath}/assets/img/icons/pdf.svg" alt="img"></a>
                                </li>
                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="excel"><img
                                            src="${pageContext.request.contextPath}/assets/img/icons/excel.svg" alt="img"></a>
                                </li>
                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="print"><img
                                            src="${pageContext.request.contextPath}/assets/img/icons/printer.svg" alt="img"></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <%---------------------------------End table top-----------------------------------%>



                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <form action="filterQuotations" method="get">
                        <div class="card" id="filter_inputs">
                            <div class="card-body pb-0">
                                <div class="row">

                                    <%--------------------oorder status slelector--------------------%>
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group">
                                            <select name="status" class="select">
                                                <option>Chọn trạng thái</option>
                                                <option value="WAITING_PROCESS">Chờ duyệt</option>
                                                <option value="CANCELED">Đã hủy</option>
                                                <option value="DELIVERING">Đang giao</option>
                                                <option value="DELIVERED">Đã giao</option>
                                                <option value="ACCEPTED">Đã nhận hàng</option>
                                                <option value="REFUNDED">Đã hoàn tiền</option>
                                                <option value="FEEDBACKED">Đã nhận đánh giá</option>
                                            </select>
                                        </div>
                                    </div>
                                    <%--------------------end oorder status slelector--------------------%>

                                    <%------------------------filter btn------------------------%>
                                    <div class="col-lg-1 col-sm-6 col-12 ms-auto">
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-filters ms-auto">
                                                <img src="${pageContext.request.contextPath}/assets/img/icons/search-whites.svg" alt="img">
                                            </button>
                                        </div>
                                    </div>
                                    <%------------------------end filter btn------------------------%>

                                </div>
                            </div>
                        </div>
                    </form>

                    <%--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <table class="table datanew">
                        <thead>
                        <tr>
                            <%--                            <th>--%>
                            <%--                                <label class="checkboxs">--%>
                            <%--                                    <input type="checkbox" id="select-all">--%>
                            <%--                                    <span class="checkmarks"></span>--%>
                            <%--                                </label>--%>
                            <%--                            </th>--%>
                            <th>Mã Order</th>
                            <th>Tên khách hàng</th>
                            <th>Ngày đặt hàng</th>
                            <th>Trạng thái đơn hàng</th>
                            <th>Xử lý</th>
                            <th>Lưu</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${filteredOrders != null}">
                                <c:forEach var="order" items="${filteredOrders}">
                                    <tr>
                                            <%-- Checkbox --%>
                                            <%--                                        <td>--%>
                                            <%--                                            <label class="checkboxs">--%>
                                            <%--                                                <input type="checkbox" name="selectedOrders">--%>
                                            <%--                                                <span class="checkmarks"></span>--%>
                                            <%--                                            </label>--%>
                                            <%--                                        </td>--%>

                                            <%-- Mã Order --%>
                                        <td>${order.id}</td>

                                            <%-- Tên khach hàng --%>
                                        <td>${order.customer.name}</td>

                                            <%-- Ngày đặt hàng --%>
                                        <td>
                                            <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy" />
                                        </td>


                                            <%-- Trạng thái đơn hàng hiện tại --%>
                                        <td>
                                            <span data-current-status="${order.getStatus()}">
                                                    ${LabelConverter.convertStatus(order.getStatus())}
                                            </span>
                                        </td>


                                            <%-- Dropdown cập nhật trạng thái --%>
                                        <td>
                                            <label>
                                                <select name="status-${order.id}" class="badges bg-lightgrey">
                                                    <option value="">Cập nhật:</option>
                                                    <option value="WAITING_PROCESS">Đang chờ xử lý</option>
                                                    <option value="CANCELED">Đã hủy</option>
                                                    <option value="DELIVERING">Đang vận chuyển</option>
                                                    <option value="DELIVERED">Đã vận chuyển</option>
                                                    <option value="ACCEPTED">Đã xác nhận</option>
                                                    <option value="REFUNDED">Đã trả hàng</option>
                                                    <option value="FEEDBACKED">Đã đánh giá</option>
                                                </select>
                                            </label>
                                        </td>

                                            <%-- Nút xử lý --%>
                                        <td>
                                            <button class="btn btn-primary update-status-btn" data-order-id="${order.id}">
                                                <img src="${pageContext.request.contextPath}/assets/img/icons/transfer1.svg" alt="Xử lý">
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>


                            <c:otherwise>
                                <c:forEach var="order" items="${orders}">
                                    <tr>
                                            <%--                                        &lt;%&ndash; Checkbox &ndash;%&gt;--%>
                                            <%--                                        <td>--%>
                                            <%--                                            <label class="checkboxs">--%>
                                            <%--                                                <input type="checkbox" name="selectedOrders">--%>
                                            <%--                                                <span class="checkmarks"></span>--%>
                                            <%--                                            </label>--%>
                                            <%--                                        </td>--%>

                                            <%-- Mã Order --%>
                                        <td>${order.id}</td>

                                            <%-- Tên khách hàng --%>
                                        <td>${order.customer.name}</td>

                                            <%-- Ngày đặt hàng --%>
                                        <td>
                                            <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy" />
                                        </td>


                                            <%-- Trạng thái đơn hàng hiện tại (ẩn trong HTML để sử dụng trong JS) --%>
                                        <td>
                                            <span data-current-status="${order.getStatus()}">
                                                    ${LabelConverter.convertStatus(order.getStatus())}
                                            </span>
                                        </td>


                                            <%-- Dropdown cập nhật trạng thái --%>
                                        <td>
                                            <label>
                                                <select name="status-${order.id}" class="badges bg-lightgrey">
                                                    <option value="">Cập nhật:</option>
                                                    <option value="WAITING_PROCESS">Đang chờ xử lý</option>
                                                    <option value="CANCELED">Đã hủy</option>
                                                    <option value="DELIVERING">Đang vận chuyển</option>
                                                    <option value="DELIVERED">Đã vận chuyển</option>
                                                    <option value="ACCEPTED">Đã xác nhận</option>
                                                    <option value="REFUNDED">Đã trả hàng</option>
                                                    <option value="FEEDBACKED">Đã đánh giá</option>
                                                </select>
                                            </label>
                                        </td>

                                            <%-- Nút xử lý --%>
                                        <td>
                                            <button class="btn btn-primary update-status-btn" data-order-id="${order.id}">
                                                <img src="${pageContext.request.contextPath}/assets/img/icons/transfer1.svg" alt="Xử lý">
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>


                    <%--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--%>

                    <script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>

                    <script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>

                    <script src="${pageContext.request.contextPath}/assets/js/jquery.slimscroll.min.js"></script>

                    <script src="${pageContext.request.contextPath}/assets/js/jquery.dataTables.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/dataTables.bootstrap4.min.js"></script>

                    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>

                    <script src="${pageContext.request.contextPath}/assets/js/moment.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/bootstrap-datetimepicker.min.js"></script>

                    <script src="${pageContext.request.contextPath}/assets/plugins/select2/js/select2.min.js"></script>

                    <script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalerts.min.js"></script>

                    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>


                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <script>
                        // trình tự cập nhật đúng
                        const statusTransitions = {
                            WAITING_PROCESS: ["DELIVERING", "CANCELED"],
                            DELIVERING: ["DELIVERED"],
                            CANCELED: [],
                            DELIVERED: ["ACCEPTED", "REFUNDED"],
                            ACCEPTED: ["FEEDBACKED"],
                            REFUNDED: [],
                            FEEDBACKED: []
                        };

                        $(document).on('click', '.update-status-btn', function () {
                            const orderId = $(this).data('order-id');
                            const currentStatus = $(this).closest('tr').find('span[data-current-status]').data('current-status');
                            const newStatus = $('select[name="status-' + orderId + '"]').val();

                            console.log("Current: " + currentStatus);
                            console.log("New: " + newStatus);

                            // Check ì correct logic
                            if (!statusTransitions[currentStatus].includes(newStatus)) {
                                const alertMessage =
                                    "Bạn có chắc muốn cập nhật trạng thái đơn hàng từ " +   currentStatus + " sang " + newStatus + " không?";
                                const confirmUpdate = confirm(alertMessage);
                                if (!confirmUpdate)
                                    return;
                            }

                            if (!newStatus || newStatus == "") {
                                alert("Vui lòng chọn trạng thái mới!");
                                return;
                            }

                            $.ajax({
                                url: 'updateOrderStatus',
                                type: 'POST',
                                data: { orderId: orderId, newStatus: newStatus },
                                success: function (response) {
                                    // alert(response);
                                    location.reload();
                                },
                                error: function (xhr) {
                                    alert("Có lỗi xảy ra: " + xhr.responseText);
                                }
                            });
                        });
                    </script>

                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%--///////////////////////////////////////////////////////////////////////////////////////////////////--%>
                    <%-----------------------------------End Main content-----------------------------------%>
</body>
</html>
