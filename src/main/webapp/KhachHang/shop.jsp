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
	<title>Shop Page </title>
</head>

<body>

<c:import url="../includes/header.jsp" />

<!-- Start Hero Section -->

<div class="hero">
	<div class="container">
		<div class="row justify-content-between position-relative">
			<div class="col-lg-5">
				<div class="intro-excerpt">
					<h1>Shop</h1>
					<p class="mb-4">Đây là những sản phẩm của shop chúng tôi.</p>
					<p><a href="" class="btn btn-secondary me-2">Shop Now</a></p>
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




<div class="untree_co-section product-section before-footer-section">
	<div class="container">
		<form id="formSearch" action="../shopServlet" method="POST" class="d-flex mb-3">
			<input
					type="text"
					name="keyword"
					value="${keyword}"
					class="form-control me-2"
					placeholder="Nhập từ khóa tìm kiếm..."
					style="width: 85%;"
			>
			<input
					type="hidden"
					name="price"
					value="${not empty price ? price : "70000000"}"
			>
			<input
					type="hidden"
					name="page"
			>
			<input
					type="hidden"
					name="Color"
					value="${not empty color ? color : ""}"
			>
			<input
					type="hidden"
					name="NSX"
					value="${not empty nsx ? nsx : ""}"
			>
			<button
					type="submit"
					class="btn btn-primary"
					id="buttonSubmitSearch"
					style="width: 10%;"
			>
				<i class="fas fa-search"></i>
			</button>
		</form>


		<div class="row">
			<div class="col-md-3">
				<div class="filter-container">
					<h5>Bộ lọc tìm kiếm </h5>

					<!-- Mức giá -->
					<div class="filter-section">
						<h3>Mức giá</h3>
						<div class="price-range">
							<input type="range" id="priceSlider" name="price" min="0" max="100000000" value="${not empty price ? price : "70000000"}" step="1000000">
							<span class="priceValue" id="priceValue">${not empty price ? price : "70000000"}</span>
						</div>
					</div>

					<!-- Nhà sản xuất -->
					<c:if test="${not empty listNSX}">
						<div class="filter-section">
							<h3>Nhà sản xuất</h3>
							<c:set var="selectedNSX" value="${nsx}" />
							<div class="filter-options">
								<c:forEach var="nsx" items="${listNSX}">
									<label><input type="radio" name="nsx" value="${nsx}" <c:if test="${selectedNSX == nsx}">checked</c:if>> ${nsx}</label>
								</c:forEach>
							</div>
						</div>
					</c:if>

					<!-- Màu sản phẩm -->
					<c:if test="${not empty listColor}">
						<div class="filter-section">
							<h3>Màu</h3>
							<!-- Lấy giá trị color từ request -->
							<c:set var="selectedColor" value="${color}" />
							<div class="filter-options">
								<select name="color" id="colorSelect">
									<option value="">Tất cả</option>
									<c:forEach var="color" items="${listColor}">
										<option value="${color}" <c:if test="${selectedColor == color}">selected</c:if>>${color}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</c:if>
					<!-- Nút Reset -->
					<div class="filter-section text-center mt-3">
						<button type="submit" class="btn btn-secondary buttonSubmitSearch">Reset bộ lọc</button>
					</div>
				</div>
			</div>
			<div class="col-md-9">
				<div class="row">
					<c:forEach var="furniture" items="${listFurnitures}">
						<div class="col-12 col-md-4 col-lg-3 mb-5">
							<form action="../furnitureServlet" method="GET" style="display:inline;">
								<input type="hidden" name="furnitureId" value="${furniture.id}">
								<input type="hidden" name="furnitureCategoryID" value="${furniture.category.id}">
								<input type="hidden" name="action" value="fromShop">
								<a href="javascript:void(0);" class="product-item" onclick="this.closest('form').submit();">
									<img src="data:image/png;base64,${furniture.representativeImage.base64Data}"
										 alt="${furniture.representativeImage.fileName}"
										 class="img-fluid product-thumbnail">
									<h3 class="product-title">${furniture.category.categoryName}</h3>
									<h3 class="product-color">Màu: ${furniture.furnitureColor}</h3>
									<h3 class="product-color">NSX: ${furniture.category.manufacture}</h3>
									<strong class="product-price priceValue">${furniture.furniturePrice}</strong>
								</a>
							</form>
							<!-- Form chứa nút submit -->
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
					</c:forEach>
				</div>
			</div>
			<c:if test="${pagination.totalPages > 0}">
				<ul class="pagination">
					<!-- Hiển thị trang 1 và trang 2 mà không có lớp 'active' -->
					<li class="page-item">
						<c:if test="${pagination.currentPage > 1}">
							<button class="page-link" data-pagination="${pagination.currentPage-1}"> Trang trước</button>
						</c:if>
					</li>

					<c:forEach var="i" begin="1" end="${pagination.totalPages}">
						<li class="page-item ${pagination.currentPage == i ? 'active' : ''}">
							<button class="page-link" data-pagination="${i}">${i}</button>
						</li>
					</c:forEach>

					<li class="page-item">
						<c:if test="${pagination.currentPage < pagination.totalPages}">
							<button class="page-link" data-pagination="${pagination.currentPage+1}"> Trang sau</button>
						</c:if>
					</li>
				</ul>
			</c:if>
		</div>
	</div>
</div>
<%-- Kiểm tra xem có thông báo nào không --%>
<% String message = (String) request.getAttribute("message"); %>
<% if (message != null) { %>
<script type="text/javascript">
	alert("<%= message %>");
</script>
<% } %>
<c:import url="../includes/footer.jsp" />

<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/tiny-slider.js"></script>
<script src="js/custom.js"></script>
<script src="js/shop.js"></script>
</body>

</html>
