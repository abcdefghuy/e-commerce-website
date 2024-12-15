<%--
  Created by IntelliJ IDEA.
  User: ANH THU
  Date: 11/21/2024
  Time: 9:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function unlockCustomer(customerId, status) {
        if (status !== 'InActive') {
            $('#unlockCustomerStatus').modal('show');
        } else {
            unlockCustomers(customerId);
        }
    }

    var unlockInactiveCustomers = [];
    var unlockselectedCustomers = [];
    $('#btnUnlockCustomers').click(function (e) {
        e.preventDefault();
        // Đặt lại mảng khi nhấn nút
        unlockSelectedCustomers = [];
        unlockInactiveCustomers = [];
        // Duyệt qua các checkbox được chọn trong bảng
        $('#customerList').find('tbody input[type=checkbox]:checked').each(function () {
            var customerId = $(this).val();
            var row = $(this).closest('tr');
            var customerName = row.find('td:nth-child(2) a').text().trim();
            var customerPhone = row.find('td:nth-child(3)').text().trim();
            var customerStatus = row.find('td:nth-child(6) .badges').text().trim();

            if (customerStatus.toLowerCase() === 'active') {
                // Thêm thông tin khách hàng vào danh sách
                unlockInactiveCustomers.push({
                    id: customerId,
                    name: customerName || 'Không rõ',
                    phone: customerPhone || 'Không rõ'
                });
            } else {
                // Thêm ID khách hàng vào danh sách để xóa
                unlockSelectedCustomers.push(customerId);
            }
        });
        // Hiển thị danh sách khách hàng đã bị xóa nếu có
        if (unlockInactiveCustomers.length > 0) {
            // Khởi tạo chuỗi HTML
            var htmlContent = '<h6>Những khách hàng sau đang hoạt động:</h6>';
            htmlContent += '<table class="table table-bordered">';
            htmlContent += '<thead><tr><th>STT</th><th>Tên Khách Hàng</th><th>Số Điện Thoại</th></tr></thead>';
            htmlContent += '<tbody>';

            // Duyệt qua danh sách khách hàng và tạo các hàng cho bảng
            for (var i = 0; i < unlockInactiveCustomers.length; i++) {
                var customer = unlockInactiveCustomers[i];
                htmlContent += '<tr>';
                htmlContent += '<td>' + (i + 1) + '</td>';
                htmlContent += '<td>' + (customer.name || 'Không rõ') + '</td>';
                htmlContent += '<td>' + (customer.phone || 'Không rõ') + '</td>';
                htmlContent += '</tr>';
            }
            htmlContent += '</tbody></table>';
            // Chèn chuỗi HTML vào modal
            $('#unlockCustomerStatusList .modal-body').html(htmlContent);
            $('#unlockCustomerStatusList').modal('show');
        } else if (unlockSelectedCustomers.length > 0) {
            unlockCustomers(unlockSelectedCustomers);
        } else {
            $('#deleteCustomer').modal('show');
        }
    });

    // Xử lý sự kiện khi nhấn nút "Bỏ chọn"
    $('#btnUnlockUncheckCustomers').click(function () {
        // Bỏ chọn tất cả các checkbox tương ứng với khách hàng trong danh sách đã xóa
        $('#customerList').find('tbody input[type=checkbox]:checked').each(function () {
            var customerId = $(this).val();
            // Kiểm tra nếu ID của khách hàng nằm trong danh sách "inactiveCustomers"
            var isInactive = unlockInactiveCustomers.some(function (customer) {
                return customer.id === customerId;
            });

            // Nếu có trong danh sách, bỏ chọn checkbox
            if (isInactive) {
                $(this).prop('checked', false);
            }
        });

        $('#customerStatusList').modal('hide');
    });


    function unlockCustomers(customerId) {
        $('#confirmUnlockModal').modal('show');

        $('#confirmUnlockButton').off('click').on('click', function () {
            $('#confirmUnlockModal').modal('hide');

            $.ajax({
                url: "${pageContext.request.contextPath}/admin-customer-list/" + customerId,
                type: "POST",
                data: {
                    action: "unlock"
                },
                success: function (result) {
                    $('#unlockCustomerSuccess').modal('show');
                    $('#unlockCustomerSuccess').on('hidden.bs.modal', function () {
                        location.reload();
                    });
                },
                error: function (result) {
                    console.log("error");
                    alert(result.message || "Có lỗi xảy ra, vui lòng thử lại.");
                }
            });
        });
    }
</script>