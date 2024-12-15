document.addEventListener('DOMContentLoaded', function () {
    const rowsPerPage = 5;
    let currentPage = 1;

    // Lấy tất cả các hàng trong bảng
    const rows = Array.from(document.querySelectorAll('#orderList tbody tr'));

    // Kiểm tra nếu không có dữ liệu
    if (rows.length === 0) {
        document.getElementById('page-info').textContent = 'No data available';
        document.getElementById('prev-page').disabled = true;
        document.getElementById('next-page').disabled = true;
        return;
    }

    // Tính tổng số trang
    const totalPages = Math.ceil(rows.length / rowsPerPage);

    // Hàm hiển thị hàng theo trang
    function displayRows(page) {
        const start = (page - 1) * rowsPerPage; // Vị trí bắt đầu
        const end = start + rowsPerPage; // Vị trí kết thúc

        rows.forEach(function (row, index) {
            if (index >= start && index < end) {
                row.style.display = ''; // Hiển thị
            } else {
                row.style.display = 'none'; // Ẩn
            }
        });

        // Cập nhật thông tin trang
        document.getElementById('page-info').textContent = 'Page ' + page + ' of ' + totalPages;

        // Vô hiệu hóa nút Prev nếu ở trang đầu tiên
        document.getElementById('prev-page').disabled = page === 1;

        // Vô hiệu hóa nút Next nếu ở trang cuối cùng
        document.getElementById('next-page').disabled = page === totalPages;
    }

    // Sự kiện nút "Previous Page"
    document.getElementById('prev-page').addEventListener('click', function () {
        if (currentPage > 1) {
            currentPage--;
            displayRows(currentPage);
        }
    });

    // Sự kiện nút "Next Page"
    document.getElementById('next-page').addEventListener('click', function () {
        if (currentPage < totalPages) {
            currentPage++;
            displayRows(currentPage);
        }
    });

    // Hiển thị hàng cho trang đầu tiên khi tải trang
    displayRows(currentPage);
});
