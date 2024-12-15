<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul>
                <li id ="list-staff">
                    <a href="${pageContext.request.contextPath}/listStaff"><img src="${pageContext.request.contextPath}/assets/img/icons/users1.svg" alt="img"><span> Quản lý nhân viên</span> </a>
                </li>
            </ul>
            <ul>
                <li id="set-shift">
                    <a href="setShiftStaff"><img src="${pageContext.request.contextPath}/assets/img/icons/transcation.svg" alt="img"><span>Phân ca làm việc</span> </a>
                </li>
            </ul>
            <ul>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/assets/img/icons/dashboard.svg" alt="img"><span> Thống Kê</span> <span class="menu-arrow"></span> </a>
                    <ul>
                        <li><a href="statistics?page=index1" id="revenue"> Doanh Thu</a></li>
                        <li><a href="statistics?page=index2" id="top-product">Sản Phẩm Bán Chạy</a></li>
                        <li><a href="statistics?page=index3" id="order-status">Trạng Thái Đơn Hàng</a></li>
                    </ul>
                </li>
            </ul>
            <ul>
                <li id="create-coupon">
                    <a href="CouponController"><img src="${pageContext.request.contextPath}/assets/img/icons/coupon.svg" alt="img"><span> Tạo Mã Khuyến Mãi</span> </a>
                </li>
            </ul>
            <ul>
                <li id="list-customer">
                    <a href="${pageContext.request.contextPath}/admin-customer-list"><img src="${pageContext.request.contextPath}/assets/img/icons/dashboard.svg" alt="img"><span> Quản lý Khách Hàng</span> </a>
                </li>
            </ul>
            <ul>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/assets/img/icons/product.svg" alt="img"><span> Sản phẩm</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li ><a href="product-controller?action=getFurnitureList" id="list-product">Danh sách sản phẩm</a></li>
                        <li ><a href="product-controller?action=displayCategory" id="add-product">Thêm sản phẩm</a></li>
                        <li >
                            <a href="category-controller?action=getCategoryList" id="list-category">Danh sách danh mục sản phẩm</a>
                        </li>
                        <li ><a href="category-controller?action=null" id="add-category">Thêm danh mục sản phẩm</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>