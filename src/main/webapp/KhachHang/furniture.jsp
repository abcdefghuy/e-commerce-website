<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 11/17/2024
  Time: 10:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Untree.co">
    <link rel="shortcut icon" href="favicon.png">

    <meta name="description" content="" />
    <meta name="keywords" content="bootstrap, bootstrap4" />

    <!-- Bootstrap CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link href="css/tiny-slider.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">

    <title>Trang chi tiết sản phẩm </title>
</head>
<body>
<c:import url="../includes/header.jsp" />

<c:choose>
    <c:when test="${not empty furniture}">
        <!-- Start We Help Section -->
        <div class="we-help-section">
            <div class="container">
                <div class="row justify-content-between">
                    <div class="col-lg-7 mb-5 mb-lg-0">
                        <div class="imgs-grid">
                            <c:choose>
                                <c:when test="${not empty furniture.furnitureImages}">
                                    <c:forEach var="i" begin="0" end="${fn:length(furniture.furnitureImages) > 3 ? 2 : fn:length(furniture.furnitureImages) - 1}">
                                        <div class="grid grid-${i + 1}">
                                            <img src="data:image/png;base64,${furniture.furnitureImages[i].base64Data}"
                                                 alt="${furniture.furnitureImages[i].fileName}"
                                                 class="img-fluid product-thumbnail">
                                        </div>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-lg-5 ps-lg-5">
                        <h2 class="section-title mb-4">${furniture.category.categoryName}</h2>
                        <p class="fur_des">Mô tả: ${furniture.furnitureDescription}</p>

                        <ul class="list-unstyled custom-list my-4">
                            <li class="priceValue"> ${furniture.furniturePrice} </li>
                            <li>Màu: ${furniture.furnitureColor}</li>
                            <li>NSX: ${furniture.category.manufacture}</li>
                            <li>Danh mục: ${furniture.category.categoryName}</li>
                        </ul>
                        <form action="../PurchaseServlet" method="POST" class="btn-form" style="display:inline;">
                            <input type="hidden" name="furnitureID" value="${furniture.id}">
                            <input type="hidden" name="action" value="addtocart">
                            <button type="submit" class="btn-submit">
											<span class="icon-cross">
												<img src="images/cross.svg" class="img-fluid">
											</span>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- End We Help Section -->
    </c:when>

    <c:otherwise>
        <div class="container text-center mt-5">
            <h3>Hiện tại chưa có sản phẩm nào.</h3>
        </div>
    </c:otherwise>
</c:choose>


<!-- Start Product Section -->
<div class="product-section">
    <div class="container">
        <div class="row">
            <!-- Cột Mô Tả -->
            <div class="col-md-12 col-lg-3 mb-5 mb-lg-0">
                <h2 class="mb-4 section-title">Các sản phẩm mới nhất của shop</h2>
                <p class="mb-4">Đây là các sản phẩm mới ra mắt gần đây</p>
                <p><a href="shopServlet" class="btn">Khám phá</a></p>
            </div>

            <!-- Cột Swiper -->
            <div class="col-md-12 col-lg-9">
                <div class="swiper-container-wrapper">
                    <div class="swiper-container">
                        <div class="swiper-wrapper">
                            <c:forEach var="Furniture" items="${listFurniture}">
                                <div class="swiper-slide">
                                    <form action="../furnitureServlet" method="POST" style="display:inline;">
                                        <input type="hidden" name="furnitureId" value="${Furniture.id}">
                                        <input type="hidden" name="furnitureCategoryID" value="${Furniture.category.id}">
                                        <a href="javascript:void(0);" class="product-item" onclick="this.closest('form').submit();">
                                            <img src="data:image/png;base64,${Furniture.representativeImage.base64Data}"
                                                 alt="${Furniture.representativeImage.fileName}" class="img-fluid product-thumbnail">
                                            <h3 class="product-title">${Furniture.category.categoryName}</h3>
                                            <h3 class="product-title">Màu: ${Furniture.furnitureColor}</h3>
                                            <strong class="product-price priceValue">${Furniture.furniturePrice}</strong>
                                        </a>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                        <!-- Nút điều hướng -->
                        <%--                            <div class="swiper-button-next"></div>--%>
                        <%--                            <div class="swiper-button-prev"></div>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- End Product Section -->
<c:import url="../includes/footer.jsp" />

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const swiper = new Swiper('.swiper-container', {
            slidesPerView: 3, // Hiển thị 3 sản phẩm cùng lúc
            spaceBetween: 20, // Khoảng cách giữa các slide
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
            loop: true, // Lặp lại vòng trượt
            autoplay: {
                delay: 5000, // Trượt tự động sau mỗi 3 giây
                disableOnInteraction: false, // Không tắt autoplay khi người dùng tương tác
            },
        });
    });
</script>


<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/tiny-slider.js"></script>
<script src="js/custom.js"></script>
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<script src="js/furniture.js"></script>
</body>
</html>
