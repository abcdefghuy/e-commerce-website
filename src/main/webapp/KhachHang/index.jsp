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
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
	<link href="css/tiny-slider.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
	<title>Home Page</title>
</head>

<body>
<c:import url="../includes/header.jsp" />

<!-- Start Hero Section -->
<div class="hero">
	<div class="container">
		<div class="row justify-content-between">
			<div class="col-lg-5">
				<div class="intro-excerpt">
					<h1>Modern Interior <span clsas="d-block">Design Studio</span></h1>
					<p class="mb-4">Khám phá không gian sống lý tưởng với các thiết kế hiện đại và tinh tế, biến mọi ý tưởng thành hiện thực!</p>
					<p><a href="${pageContext.request.contextPath}/shopServlet" class="btn btn-secondary me-2">Shop Now</a></p>
				</div>
			</div>
			<div class="col-lg-7">
				<div class="hero-img-wrap">
					<img src="images/couch.png" class="img-fluid">
				</div>
			</div>
		</div>
	</div>
</div>
<!-- End Hero Section -->

<!-- Start Product Section -->
<div class="product-section">
	<div class="container">
		<div class="row">
			<!-- Cột Mô Tả -->
			<div class="col-md-12 col-lg-3 mb-5 mb-lg-0">
				<h2 class="mb-4 section-title">Các sản phẩm bán chạy nhất của shop</h2>
				<p class="mb-4">Đây là các sản phẩm được mua nhiều nhất tại shop chúng tôi</p>
				<p><a href="${pageContext.request.contextPath}/shopServlet" class="btn">Khám phá Shop</a></p>
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

<!-- Start Product Section -->
<div class="product-section">
	<div class="container">
		<div class="row">
			<!-- Cột Mô Tả -->
			<div class="col-md-12 col-lg-3 mb-5 mb-lg-0">
				<h2 class="mb-4 section-title">Các sản phẩm mới ra mắt của shop</h2>
				<p class="mb-4">Đây là các sản phẩm vừa được ra mắt tại shop chúng tôi tại shop chúng tôi</p>
				<p><a href="${pageContext.request.contextPath}/shopServlet" class="btn">Khám phá Shop</a></p>
			</div>

			<!-- Cột Swiper -->
			<div class="col-md-12 col-lg-9">
				<div class="swiper-container-wrapper">
					<div class="swiper-container">
						<div class="swiper-wrapper">
							<c:forEach var="Furniture" items="${listFurnitureNew}">
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

<!-- Start Why Choose Us Section -->
<div class="why-choose-section">
	<div class="container">
		<div class="row justify-content-between">
			<div class="col-lg-6">
				<h2 class="section-title">Hãy chon chúng tôi</h2>
				<p>Hãy lựa chọn chúng tôi để biến không gian mơ ước của bạn thành hiện thực, với những thiết kế độc đáo, tinh tế và đội ngũ giàu kinh nghiệm sẵn sàng phục vụ bạn!</p>

				<div class="row my-5">
					<div class="col-6 col-md-6">
						<div class="feature">
							<div class="icon">
								<img src="../images/truck.svg" alt="Image" class="imf-fluid">
							</div>
							<h3>Fast &amp; Free Shipping</h3>
							<p>Chúng tôi cam kết giao hàng nhanh chóng và hoàn toàn miễn phí, đảm bảo sản phẩm đến tay bạn an toàn và đúng hẹn, dù bạn ở bất cứ đâu!</p>
						</div>
					</div>

					<div class="col-6 col-md-6">
						<div class="feature">
							<div class="icon">
								<img src="../images/bag.svg" alt="Image" class="imf-fluid">
							</div>
							<h3>Easy to Shop</h3>
							<p>Mua sắm chưa bao giờ dễ dàng đến thế! Với giao diện thân thiện và quy trình đơn giản, bạn có thể tìm kiếm và đặt hàng chỉ trong vài bước.</p>
						</div>
					</div>

					<div class="col-6 col-md-6">
						<div class="feature">
							<div class="icon">
								<img src="../images/support.svg" alt="Image" class="imf-fluid">
							</div>
							<h3>24/7 Support</h3>
							<p>Chúng tôi luôn sẵn sàng hỗ trợ bạn mọi lúc, mọi nơi, 24/7. Đội ngũ tư vấn chuyên nghiệp sẽ giúp giải đáp mọi thắc mắc và đảm bảo sự hài lòng tuyệt đối.</p>
						</div>
					</div>

					<div class="col-6 col-md-6">
						<div class="feature">
							<div class="icon">
								<img src="../images/return.svg" alt="Image" class="imf-fluid">
							</div>
							<h3>Hassle Free Returns</h3>
							<p>Đổi trả dễ dàng và nhanh chóng! Chúng tôi cam kết mang đến trải nghiệm mua sắm không rủi ro với chính sách hoàn trả linh hoạt, giúp bạn yên tâm tuyệt đối.</p>
						</div>
					</div>

				</div>
			</div>

			<div class="col-lg-5">
				<div class="img-wrap">
					<img src="../images/why-choose-us-img.jpg" alt="Image" class="img-fluid">
				</div>
			</div>

		</div>
	</div>
</div>
<!-- End Why Choose Us Section -->

<!-- Start We Help Section -->
<div class="we-help-section">
	<div class="container">
		<div class="row justify-content-between">
			<div class="col-lg-7 mb-5 mb-lg-0">
				<div class="imgs-grid">
					<div class="grid grid-1"><img src="../images/img-grid-1.jpg" alt="Untree.co"></div>
					<div class="grid grid-2"><img src="../images/img-grid-2.jpg" alt="Untree.co"></div>
					<div class="grid grid-3"><img src="../images/img-grid-3.jpg" alt="Untree.co"></div>
				</div>
			</div>
			<div class="col-lg-5 ps-lg-5">
				<h2 class="section-title mb-4">Chúng Tôi Giúp Bạn Tạo Nên Không Gian Nội Thất Hiện Đại</h2>
				<p>Khám phá giải pháp thiết kế nội thất hiện đại, kết hợp hoàn hảo giữa sự tinh tế và tiện nghi. Chúng tôi mang đến những ý tưởng sáng tạo, phù hợp với mọi phong cách và nhu cầu của bạn.</p>

				<ul class="list-unstyled custom-list my-4">
					<li>Thiết kế không gian sống độc đáo và tối ưu</li>
					<li>Đội ngũ chuyên gia giàu kinh nghiệm</li>
					<li>Đảm bảo chất lượng cao với mức chi phí hợp lý</li>
					<li>Hỗ trợ tư vấn và thi công trọn gói</li>
				</ul>
				<p><a href="${pageContext.request.contextPath}/shopServlet" class="btn">Khám phá Shop</a></p>
			</div>
		</div>
	</div>
</div>
<!-- End We Help Section -->



<c:import url="../includes/footer.jsp" />

<script>
	function confirmLogout() {
		if (confirm("Bạn có chắc chắn muốn đăng xuất?")) {
			window.location.href = "LogoutServlet"; // Chuyển hướng đến servlet nếu người dùng chọn "OK"
		}
	}
</script>

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

<script src="../js/bootstrap.bundle.min.js"></script>
<script src="../js/tiny-slider.js"></script>
<script src="../js/custom.js"></script>
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<script src="../js/furniture.js"></script>
</body>

</html>
