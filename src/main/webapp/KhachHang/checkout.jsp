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
								<h1>Checkout</h1>
							</div>
						</div>
						<div class="col-lg-7">

						</div>
					</div>
				</div>
			</div>
		<!-- End Hero Section -->

		<div class="untree_co-section">
		    <div class="container">
		      <div class="row">
				  <div class="col-md-5 mb-5 mb-md-0">
		          <h2 class="h3 mb-3 text-black">Thông tin cá nhân</h2>
		          <div class="p-3 p-lg-5 border bg-white">
		            <div class="form-group row">
						<div class="col-md-12">
							<label for="c_fname" class="text-black">Họ và Tên <span class="text-danger">*</span></label>
							<input type="text" class="form-control" id="c_fname" name="c_fname" value="${customer.getName()}" readonly>
						</div>
		            </div>
		            <div class="form-group row">
		              <div class="col-md-12">
		                <label for="c_address" class="text-black">Địa chỉ <span class="text-danger">*</span></label>
		                <input type="text" class="form-control" id="c_address" name="c_address" value="${customer.getAddress().getStreet()}" readonly >
		              </div>
		            </div>
					  <div class="form-group row">
						  <div class="col-md-12">
							  <label for="c_district" class="text-black">Quận/ Huyện <span class="text-danger">*</span></label>
							  <input type="text" class="form-control" id="c_district" name="c_district" value="${customer.getAddress().getCity()}" readonly>
						  </div>
					  </div>
					  <div class="form-group row mb-5">
					  	<div class="col-md-6">

							<label for="c_state_country" class="text-black">Tỉnh / Thành phố <span class="text-danger">*</span></label>
		                	<input type="text" class="form-control" id="c_state_country" name="c_state_country" value="${customer.getAddress().getProvince()}" readonly>
		              	</div>
						  <div class="col-md-6">
							  <label for="c_country" class="text-black">Quốc gia<span class="text-danger">*</span></label>
							  <input type="text" class="form-control" id="c_country" name="c_country" value="${customer.getAddress().getCountry()}" readonly>
						  </div>
					  </div>

		            <div class="form-group row mb-5">
		              <div class="col-md-6">
		                <label for="c_email_address" class="text-black">Địa chỉ Email <span class="text-danger">*</span></label>
		                <input type="text" class="form-control" id="c_email_address" name="c_email_address" value="${customer.getEmail()}" readonly>
		              </div>
		              <div class="col-md-6">
		                <label for="c_phone" class="text-black">Số điện thoại <span class="text-danger">*</span></label>
		                <input type="text" class="form-control" id="c_phone" name="c_phone" value="${customer.getPhone()}" readonly>
		              </div>
		            </div>
		          </div>
		        </div>
				  <div class="col-md-7">
					<div class="row mb-5">
						<div class="col-md-12">
						<h2 class="h3 mb-3 text-black">Đơn hàng</h2>
						<div class="p-3 p-lg-5 border bg-white">
							<table class="table site-block-order-table mb-5">
								<thead>
								<tr>
									<th style="white-space: nowrap;">Hình ảnh</th>
									<th>Sản phẩm</th>
									<th style="width: 80px; text-align: center;">Số lượng</th> <!-- Thêm style cho cột số lượng -->
									<th>Tổng tiền</th>
								</tr>
								</thead>
								<tbody>
									<c:forEach var="furniture" items="${listFurniture}">
										<tr>
											<!-- Hình ảnh sản phẩm -->
											<td>
												<img src="data:image/png;base64,${furniture.representativeImage.base64Data}"
													 alt="${furniture.representativeImage.fileName}"
													 class="img-order">
											</td>

											<!-- Tên sản phẩm -->
											<td>${furniture.category.categoryName}</td>

											<!-- Số lượng và input chỉnh sửa -->
											<td data-id="${furniture.category.id}">
												<input type="number" class="quantity" value="1" min="1"
													   data-price="${furniture.furniturePrice}"
													   data-total="total-${furniture.category.categoryName}" onchange="updateTotal()"></td>

											<!-- Tổng tiền cho sản phẩm -->
											<td><span id="total-${furniture.category.categoryName}">${furniture.furniturePrice}</span></td>
										</tr>
									</c:forEach>

								</tbody>
							</table>
							<!-- Phần mã giảm giá-->
							<div id="coupon-section">
								<h2 class="h3 mb-3 text-black" id="coupon-title">Mã giảm giá</h2>
								<select class="form-control w-100" id="c_code" aria-label="Coupon Code">
									<option value="" disabled selected>Select Coupon Code</option>
									<c:forEach var="coupon" items="${listCoupon}">
										<option
												value="${coupon.couponID}"
												data-coupon-type="${coupon.couponType}"
												data-coupon-value="${coupon.couponValue}"
												data-end-date="${coupon.getFormattedEndDate()}"
												data-conditions="${coupon.useCondition}"
												data-min-order-value="${coupon.minOrderValue}"
												data-use-limit="${coupon.useLimit}"
												data-current-usage="${coupon.currentUsage}">
												${coupon.couponName}
										</option>
									</c:forEach>
								</select>
								<div id="coupon-details" class="mt-3" style="display:none;">
									<p><strong>Ngày kết thúc:</strong> <span id="data-end-date"></span></p>
									<p><strong>Điều kiện sử dụng:</strong> <span id="data-conditions"></span></p>
									<p><strong>Giá trị:</strong> <span id="data-coupon-value"></span></p>
									<p><strong>Số lượt sử dụng còn lại:</strong> <span id="data-current-usage"></span></p>


								</div>
							</div>

						  <!-- Thêm dòng tổng đơn hàng vào bảng -->
						  <div class="mt-3">
							<table class="table site-block-order-table mb-5">
							  <tbody>
								  <tr>
									  <td class="text-black font-weight-bold"><strong>Tổng tiền</strong></td>
									  <td class="text-black"><span id="grand-total">${total}</span></td>
								  </tr>
								  <tr>
									<td class="text-black font-weight-bold"><strong>Giảm giá</strong></td>
									<td class="text-black" id="discount">0</td>
								  </tr>
								  <tr>
									<td class="text-black font-weight-bold"><strong>Tổng thanh toán</strong></td>
									<td class="text-black" id="amount">${total}</td>
								  </tr>
							  </tbody>
							</table>
						  </div>
						  <!-- Kết thúc mã giảm giá -->

							<!-- Phương thức thanh toán -->
							<div class="border p-3 mb-3">
								<h3 class="h6 mb-0 payment">Phương thức thanh toán</h3>
								<div class="form-check">
									<input class="form-check-input" type="radio" name="paymentMethod" id="paymentBank" value="bank" checked>


									<input type="hidden" name="description" id="description"
										   value="CUSTID${customer.getPersonID()}<c:forEach var='item' items='${listFurniture}'>${item.category.id}</c:forEach>"/>
									<label class="form-check-label payment" for="paymentBank">
										Thanh toán ngân hàng
									</label>
									<div class="collapse show" id="collapsebank">
										<div class="py-2">
											<p class="mb-0 payment content-payment">Chắn chắn rằng bạn sẽ chuyển đúng với số tiền của đơn hàng. Trong trường hợp số tiền của bạn lớn hoặc nhỏ hơn chúng tôi sẽ không chịu trách nhiệm.</p>
										</div>
									</div>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="radio" name="paymentMethod" id="paymentCash" value="cash">
									<label class="form-check-label payment" for="paymentCash" >
										Thanh toán khi nhận hàng
									</label>
									<div class="collapse" id="collapsecash">
										<div class="py-2">
											<p class="mb-0 payment content-payment">Vui lòng thanh toán ngay khi bạn đã nhận được hàng. Nếu không chúng tôi sẽ cấm bạn khỏi hệ thống.</p>
										</div>
									</div>
								</div>
							</div>

							<!-- Lớp phủ toàn trang -->
							<div id="overlay" style="display:none">
								<div id="loadingMessage">
									Vui lòng đợi, đang tạo mã QR...
								</div>
							</div>

							<!-- Modal thông báo -->
							<div id="paymentModal" class="modal">
								<div class="modal-content">
									<span class="close" onclick="closeModal()">&times;</span>
									<h2>Quét mã QR để thanh toán</h2>

									<!-- Bộ đếm thời gian -->
									<p id="timer" class="countdown">10:00</p>

									<!-- Mã QR của bạn sẽ được hiển thị ở đây -->
									<div id="qr-code-container">
										<img id="qr-code-img" src="" alt="QR Code" />
									</div>
								</div>
							</div>
							<!-- Modal xác nhận đóng -->
							<div id="confirmCloseModal" class="modal">
								<div class="modal-content">
									<p>Bạn có chắc chắn muốn đóng không?</p>
									<div class="button-group">
										<button id="confirmCloseYes">Đồng ý</button>
										<button id="confirmCloseNo">Hủy</button>
									</div>
								</div>
							</div>
							<div class="form-group" id="payment-section">
							<button class="btn btn-black btn-lg py-3 btn-block" id="btn-payment" onclick="checkMethodPayment()">Thanh toán</button>
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
		<script src="../js/PaymentMethod.js"></script>
	</body>

</html>
