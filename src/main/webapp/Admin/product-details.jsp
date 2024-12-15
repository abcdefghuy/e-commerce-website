<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Thông tin chi tiết sản phẩm";
        const listStaffElement = document.getElementById("list-product");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>
<%------------------------------------------------%>
<div class="page-wrapper">
    <div class="content">
        <div class="page-header">
            <div class="page-title">
                <h4>Chi tiết sản phẩm</h4>
                <h6>Toàn bộ thông tin chi tiết của một sản phẩm</h6>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-8 col-sm-12">
                <div class="card">
                    <div class="card-body">
                        <div class="bar-code-view">
                            <img src="assets/img/barcode1.png" alt="barcode">
                            <a class="printimg">
                                <img src="assets/img/icons/printer.svg" alt="print">
                            </a>
                        </div>
                        <div class="productdetails">
                            <ul class="product-bar">
                                <li>
                                    <h4>Trạng thái</h4>
                                    <h6><c:choose>
                                        <c:when test="${quantity == 0}">
                                            <!-- Nếu số lượng bằng 0, đặt trạng thái là 'Hết hàng' -->
                                            <span class="badges bg-lightyellow">Hết hàng</span>
                                        </c:when>
                                        <c:when test="${furniture.furnitureStatus == 'STOP_SELLING'}">
                                            <!-- Nếu trạng thái là 'STOP_SELLING', sử dụng màu đỏ -->
                                            <span class="badges bg-lightred">Ngừng kinh doanh</span>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Nếu trạng thái không phải là 'STOP_SELLING', sử dụng màu xanh -->
                                            <span class="badges bg-lightgreen">Đang bán</span>
                                        </c:otherwise>
                                    </c:choose></h6>
                                </li>
                                <li>
                                    <h4>Danh mục</h4>
                                    <h6>${furniture.categoryName}</h6>
                                </li>
                                <li>
                                    <h4>Nhà sản xuất</h4>
                                    <h6>${furniture.manufacture}</h6>
                                </li>
                                <li>
                                    <h4>Giá</h4>
                                    <h6>${furniture.furniturePrice}</h6>
                                </li>
                                <li>
                                    <h4>Màu sắc</h4>
                                    <h6>${furniture.furnitureColor}</h6>
                                </li>
                                <li>
                                    <h4>Mô tả sản phẩm</h4>
                                    <h6>${furniture.furnitureDescription}</h6>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-sm-12">
                <div class="card">
                    <div class="card-body">
                        <div class="slider-product-details">
                            <div class="owl-carousel owl-theme product-slide">
                                <c:forEach var="image" items="${furniture.base64ImageData}">
                                    <div class="slider-product">
                                        <img src="data:image/jpeg;base64,${image}" alt="img">
                                        <!-- Tên file ảnh, giả sử bạn có thêm thông tin về tên file trong DTO -->
                                            <%--                                        <h4>Image</h4>--%>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
</div>


<c:import url="footer.jsp"/>