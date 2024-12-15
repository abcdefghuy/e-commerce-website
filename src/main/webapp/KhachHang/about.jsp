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
	<title>Trang Thông Tin </title>
</head>
<body>
<c:import url="../includes/header.jsp" />

<!-- Start Hero Section -->
<div class="hero">
	<div class="container">
		<div class="row justify-content-between">
			<div class="col-lg-5">
				<div class="intro-excerpt">
					<h1>About Us</h1>
					<p class="mb-4">Chào mừng bạn đến với Furni. Chúng tôi cam kết mang đến sản phẩm và dịch vụ chất lượng, tạo nên giá trị bền vững và trải nghiệm tuyệt vời cho khách hàng.</p>
					<p><a href="../shopServlet" class="btn btn-secondary me-2">Shop Now</a></p>
				</div>
			</div>
			<div class="col-lg-7">
				<div class="hero-img-wrap">
					<img src="../images/couch.png" class="img-fluid">
				</div>
			</div>
		</div>
	</div>
</div>
<!-- End Hero Section -->



<!-- Start Why Choose Us Section -->
<div class="why-choose-section">
	<div class="container">
		<div class="row justify-content-between align-items-center">
			<div class="col-lg-6">
				<h2 class="section-title">Tại sao nên chọn chúng tôi</h2>
				<p>Shop chúng tôi cam kết mang đến sản phẩm chất lượng cao, giá cả cạnh tranh cùng dịch vụ chăm sóc khách hàng tận tâm, đảm bảo sự hài lòng tuyệt đối trong từng trải nghiệm mua sắm</p>

				<div class="row my-5">
					<div class="col-6 col-md-6">
						<div class="feature">
							<div class="icon">
								<img src="../images/truck.svg" alt="Image" class="imf-fluid">
							</div>
							<h3>Fast &amp; Free Shipping</h3>
							<p>Chúng tôi cam kết giao hàng nhanh chóng và hoàn toàn miễn phí, giúp bạn nhận sản phẩm yêu thích một cách tiện lợi và dễ dàng nhất</p>
						</div>
					</div>

					<div class="col-6 col-md-6">
						<div class="feature">
							<div class="icon">
								<img src="../images/bag.svg" alt="Image" class="imf-fluid">
							</div>
							<h3>Easy to Shop</h3>
							<p>Trải nghiệm mua sắm dễ dàng với giao diện thân thiện và quy trình đơn giản, giúp bạn tìm thấy sản phẩm yêu thích chỉ trong vài bước</p>
						</div>
					</div>

					<div class="col-6 col-md-6">
						<div class="feature">
							<div class="icon">
								<img src="../images/support.svg" alt="Image" class="imf-fluid">
							</div>
							<h3>24/7 Support</h3>
							<p>Chúng tôi luôn sẵn sàng hỗ trợ bạn 24/7, đảm bảo mọi thắc mắc và vấn đề của bạn được giải quyết một cách nhanh chóng và hiệu quả</p>
						</div>
					</div>

					<div class="col-6 col-md-6">
						<div class="feature">
							<div class="icon">
								<img src="../images/return.svg" alt="Image" class="imf-fluid">
							</div>
							<h3>Hassle Free Returns</h3>
							<p>Chúng tôi cung cấp chính sách đổi trả dễ dàng và nhanh chóng, giúp bạn an tâm mua sắm mà không phải lo lắng về những phiền toái không đáng có</p>
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

<!-- Start Team Section -->
<div class="untree_co-section">
	<div class="container">

		<div class="row mb-5">
			<div class="col-lg-5 mx-auto text-center">
				<h2 class="section-title">Our Team</h2>
			</div>
		</div>

		<div class="row">

			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/hien.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Bá</span> Hiền</h3>
				<span class="d-block position mb-4">Nhóm trưởng</span>
				<p>MSSV 22110320 - UTER</p>
			</div>
			<!-- End Column 1 -->


			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/sHuy.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Sang</span> Huy</h3>
				<span class="d-block position mb-4">Thành viên</span>
				<p>MSSV 22110333 - UTER</p>
			</div>
			<!-- End Column 1 -->


			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/huy.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Hoàng</span> Huy</h3>
				<span class="d-block position mb-4">Thành Viên</span>
				<p>MSSV 22110335 - UTER</p>
			</div>
			<!-- End Column 1 -->


			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/nha.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Thanh</span> Nhã</h3>
				<span class="d-block position mb-4">Thành viên</span>
				<p>MSSV 22110386 - UTER</p>
			</div>
			<!-- End Column 1 -->

			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/phat.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Đức</span> Phát</h3>
				<span class="d-block position mb-4">Thành viên</span>
				<p>MSSV 22110393 - UTER</p>
			</div>
			<!-- End Column 1 -->

			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/nhat.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Quang</span> Nhật</h3>
				<span class="d-block position mb-4">Thành viên</span>
				<p>MSSV 22110390 - UTER</p>
			</div>
			<!-- End Column 1 -->
			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/hung.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Đình</span> Hưng</h3>
				<span class="d-block position mb-4">Thành viên</span>
				<p>MSSV 22110342 - UTER</p>
			</div>
			<!-- End Column 1 -->
			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/hoai.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Văn</span> Hoài</h3>
				<span class="d-block position mb-4">Thành viên</span>
				<p>MSSV 22110327 - UTER</p>
			</div>
			<!-- End Column 1 -->
			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/tu.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Mạnh</span> Tú</h3>
				<span class="d-block position mb-4">Thành viên</span>
				<p>MSSV 22110455 - UTER</p>
			</div>
			<!-- End Column 1 -->
			<!-- Start Column 1 -->
			<div class="col-12 col-md-6 col-lg-3 mb-5 mb-md-0">
				<img src="../images/thu.jpg" class="img-fluid mb-5 avt">
				<h3 clas><span class="">Anh</span> Thư</h3>
				<span class="d-block position mb-4">Thành viên</span>
				<p>MSSV 22110431 - UTER</p>
			</div>
			<!-- End Column 1 -->
		</div>
	</div>
</div>
<!-- End Team Section -->



<c:import url="..//includes/footer.jsp" />
<script src="/js/bootstrap.bundle.min.js"></script>
<script src="/js/tiny-slider.js"></script>
<script src="/js/custom.js"></script>
</body>

</html>
