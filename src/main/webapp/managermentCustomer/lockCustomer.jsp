<%--
  Created by IntelliJ IDEA.
  User: ANH THU
  Date: 11/21/2024
  Time: 9:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function deleteCustomer(customerId, status) {
        if (status !== 'Active') {
            $('#customerStatus').modal('show');
        } else {
            btnDeleteCustomer(customerId);
        }
    }

    var inactiveCustomers = [];
    var selectedCustomers = [];
    $('#btnDeleteCustomers').click(function (e) {
        e.preventDefault();
        // Đặt lại mảng khi nhấn nút
        selectedCustomers = [];
        inactiveCustomers = [];
        // Duyệt qua các checkbox được chọn trong bảng
        $('#customerList').find('tbody input[type=checkbox]:checked').each(function () {
            var customerId = $(this).val();
            var row = $(this).closest('tr');
            var customerName = row.find('td:nth-child(2) a').text().trim();
            var customerPhone = row.find('td:nth-child(3)').text().trim();
            var customerStatus = row.find('td:nth-child(6) .badges').text().trim();

            if (customerStatus.toLowerCase() !== 'active') {
                // Thêm thông tin khách hàng vào danh sách đã xóa
                inactiveCustomers.push({
                    id: customerId,
                    name: customerName || 'Không rõ',
                    phone: customerPhone || 'Không rõ'
                });
            } else {
                // Thêm ID khách hàng vào danh sách để xóa
                selectedCustomers.push(customerId);
            }
        });
        // Hiển thị danh sách khách hàng đã bị xóa nếu có
        if (inactiveCustomers.length > 0) {
            // Khởi tạo chuỗi HTML
            var htmlContent = '<h6>Những khách hàng sau đã bị xóa trước đó:</h6>';
            htmlContent += '<table class="table table-bordered">';
            htmlContent += '<thead><tr><th>STT</th><th>Tên Khách Hàng</th><th>Số Điện Thoại</th></tr></thead>';
            htmlContent += '<tbody>';

            // Duyệt qua danh sách khách hàng và tạo các hàng cho bảng
            for (var i = 0; i < inactiveCustomers.length; i++) {
                var customer = inactiveCustomers[i];
                htmlContent += '<tr>';
                htmlContent += '<td>' + (i + 1) + '</td>';
                htmlContent += '<td>' + (customer.name || 'Không rõ') + '</td>';
                htmlContent += '<td>' + (customer.phone || 'Không rõ') + '</td>';
                htmlContent += '</tr>';
            }
            htmlContent += '</tbody></table>';
            // Chèn chuỗi HTML vào modal
            $('#customerStatusList .modal-body').html(htmlContent);
            $('#customerStatusList').modal('show');
        } else if (selectedCustomers.length > 0) {
            btnDeleteCustomer(selectedCustomers);
        } else {
            $('#deleteCustomer').modal('show');
        }
    });

    // Xử lý sự kiện khi nhấn nút "Bỏ chọn"
    $('#btnUncheckCustomers').click(function () {
        // Bỏ chọn tất cả các checkbox tương ứng với khách hàng trong danh sách đã xóa
        $('#customerList').find('tbody input[type=checkbox]:checked').each(function () {
            var customerId = $(this).val();
            // Kiểm tra nếu ID của khách hàng nằm trong danh sách "inactiveCustomers"
            var isInactive = inactiveCustomers.some(function (customer) {
                return customer.id === customerId;
            });

            // Nếu có trong danh sách, bỏ chọn checkbox
            if (isInactive) {
                $(this).prop('checked', false);
            }
        });

        // Ẩn modal sau khi bỏ chọn
        $('#customerStatusList').modal('hide');
    });

    function btnDeleteCustomer(data) {
        $('#confirmDeleteModal').modal('show');

        // nút khác
        $('#deleteReason').off('change').on('change', function () {
            if ($(this).val() === 'Khác') {
                $('#deleteReasonText').show();
            } else {
                $('#deleteReasonText').hide();
                $('#deleteReasonText').val('');
            }
        });


        $('#confirmDeleteButton').off('click').on('click', function () {
            const reason = $('#deleteReason').val();
            const additionalReason = $('#deleteReasonText').val();

            if (!reason) {
                alert("Vui lòng chọn lý do xóa.");
                return;
            }
            let deleteMessage = reason;
            if (reason === 'Khác') {
                deleteMessage = additionalReason || "Không có lý do cụ thể.";
            }

            $('#confirmDeleteModal').modal('hide');
            $.ajax({
                url: "${pageContext.request.contextPath}/admin-customer-list/" + data,
                type: "POST",
                data: {
                    reason: deleteMessage,
                    action: "lock"
                },
                success: function (result) {
                    $('#deleteCustomerSuccess').modal('show');
                    $('#deleteCustomerSuccess').on('hidden.bs.modal', function () {
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