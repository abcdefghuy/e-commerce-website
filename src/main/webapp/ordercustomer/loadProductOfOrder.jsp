<%--
  Created by IntelliJ IDEA.
  User: ANH THU
  Date: 11/22/2024
  Time: 12:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function viewListProduct(orderID) {
        // $('#feedback').modal();
        loadListProduct(orderID);
    }
    function loadListProduct(orderID) {
        $.ajax({
            url: '/admin-customer-order/' + orderID,
            type: 'PUT',
            dataType: 'json',
            success: function(response) {
                // Làm sạch dữ liệu cũ
                $('#productOrderTableBody').empty();
                $('#paymentTotalPrice').text('');
                $('#paymentCoupon').text('');
                $('#paymentMethod').text('');
                $('#paymentMoney').text('');

                // Hiển thị thông tin thanh toán
                if (response.paymentResponse) {
                    const payment = response.paymentResponse;
                    $('#paymentMethod').text(payment.method || 'Không xác định');
                    $('#paymentTotalPrice').text(payment.totalPrice ? payment.totalPrice.toLocaleString() + ' VNĐ' : '0 VNĐ');
                    $('#paymentMoney').text(payment.money ? payment.money.toLocaleString() + ' VNĐ' : '0 VNĐ');
                    $('#paymentCoupon').text(payment.couponName || 'Không có mã giảm giá');
                }

                // Hiển thị danh sách sản phẩm
                if (response.furnitureOfOrderResponse && response.furnitureOfOrderResponse.length > 0) {
                    let productRows = '';
                    response.furnitureOfOrderResponse.forEach(function(product, index) {
                        // Xác định trạng thái
                        let statusClass = '';
                        let statusText = '';

                        if (product.furnitureStatus === 'ON_SALE') {
                            statusClass = 'bg-lightgreen'; // Xanh lá cây
                            statusText = 'Đang giảm giá';
                        } else if (product.furnitureStatus === 'OUT_OF_STOCK') {
                            statusClass = 'bg-lightgrey'; // Màu xám
                            statusText = 'Hết Hàng';
                        } else {
                            statusClass = 'bg-lightred'; // Đỏ
                            statusText = 'Hết giảm giá';
                        }

                        // Tạo chuỗi HTML cho từng sản phẩm
                        productRows +=
                            '<tr>' +
                            '<td>' + (index + 1) + '</td>' +
                            '<td>' + product.categoryName + '</td>' +
                            '<td style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 150px;" title="' + product.categoryDescription + '">' + product.categoryDescription + '</td>' +                            '<td>' + product.furniturePrice.toLocaleString() + ' VNĐ</td>' +
                            '<td><span class="badges ' + statusClass + '">' + statusText + '</span></td>' +
                            '<td>' + product.quantity + '</td>' +
                            '<td>' + product.totalPrice.toLocaleString() + ' VNĐ</td>' +
                            '</tr>';
                    });

                    // Thêm tất cả các dòng vào bảng
                    $('#productOrderTableBody').append(productRows);
                } else {
                    // Không có sản phẩm
                    $('#productOrderTableBody').append(
                        '<tr>' +
                        '<td colspan="7" class="text-center">Không có sản phẩm nào</td>' +
                        '</tr>'
                    );
                }

                // Hiển thị modal
                $('#productOfOrderList').modal('show');
            },
            error: function() {
                console.log("error");
                $('#orderNull').modal('show');
            }
        });
    }
</script>