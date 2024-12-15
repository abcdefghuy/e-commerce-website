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
	<link href="../css/cart.css" rel="stylesheet">
	<link href="../css/checkout.css" rel="stylesheet">

	<title>Furni Free Bootstrap 5 Template for Furniture and Interior Design Websites by Untree.co </title>
</head>

<body>

<c:import url="../includes/header.jsp" />

<!-- Start Hero Section -->
<div class="hero">
	<div class="container">
		<div class="row justify-content-between">
			<div class="col-lg-5">
				<div class="intro-excerpt">
					<h1>Cart</h1>
				</div>
			</div>
			<div class="col-lg-7">

			</div>
		</div>
	</div>
</div>
<!-- End Hero Section -->



<div class="untree_co-section before-footer-section">
	<div class="container">
		<div class="row mb-5">
			<form class="col-md-12" method="post" action="../PurchaseServlet">
				<div class="site-blocks-table">
					<table class="table">
						<thead>
						<tr>
							<th class="product-thumbnail">Hình ảnh</th>
							<th class="product-name">Sản phẩm</th>
							<th class="product-name">Màu</th>
							<th class="product-price">Giá</th>
							<th class="product-name">Nhà sản xuất</th>
							<th class="product-remove">Xóa sản phẩm</th>
							<th class="product-remove">Chọn mua sản phẩm</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach var="furniture" items="${listFurniture}">
							<tr>
								<td class="product-thumbnail">
									<img src="data:image/png;base64,${furniture.representativeImage.base64Data}"
										 alt="${furniture.representativeImage.fileName}"
										 class="img-fluid product-thumbnail">
								</td>
								<td class="product-name">
									<h2 class="h5 text-black">${furniture.category.categoryName}</h2>
								</td>
								<td class="product-name">${furniture.furnitureColor}</td>
								<td class="product-price">${furniture.furniturePrice}</td>
								<td class="product-name">${furniture.category.manufacture}</td>
								<td>
									<button type="button" class="btn btn-black btn-sm" onclick="showConfirmModal('${furniture.id}')">X</button>

									<!-- Modal xác nhận -->
									<div id="confirmCloseModal" class="modal" style="display:none;">
										<div class="modal-content">
											<p>Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không?</p>
											<div class="button-group">
												<button type="button" id="confirmCloseYes" onclick="submitForm()">Đồng ý</button>
												<button type="button" id="confirmCloseNo" onclick="closeModal()">Hủy</button>
											</div>
										</div>
									</div>
								</td>
								<td>
									<input type="checkbox" class="custom-checkbox" name="selectedProducts" value="${furniture.id}">
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</form>
		</div>

		<div class="row">
			<div class="col-md-6">
				<div class="row mb-5">
					<div class="col-md-6">
						<form action="../shopServlet" method="POST" style="display:inline;">
							<button class="btn btn-outline-black btn-sm btn-block">Tiếp tục mua sắm</button>
						</form>
					</div>
				</div>
			</div>
			<div class="col-md-6 pl-5">
				<div class="row justify-content-end">
					<div class="col-md-7">
						<div class="row">
							<div class="col-md-12">
								<form action="../PurchaseServlet" method="POST" style="display:inline;" id="purchaseForm">
									<input type="hidden" name="action" value="purchase">
									<button id="checkoutBtn" class="btn btn-outline-black btn-sm btn-block">Mua hàng</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<c:import url="../includes/footer.jsp" />

<script src="../js/bootstrap.bundle.min.js"></script>
<script src="../js/tiny-slider.js"></script>
<script src="../js/custom.js"></script>
<script src="../js/cart.js"></script>

</body>

</html>
