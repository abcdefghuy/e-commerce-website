document.addEventListener("DOMContentLoaded", function () {
    // Lấy tất cả các checkbox
    const checkboxes = document.querySelectorAll("input[type='checkbox']");
    // Lấy thẻ <p> để hiển thị số lượng
    const statusParagraph = document.getElementById("status");

    // Hàm đếm số checkbox đã chọn
    function updateCheckedCount() {
        const checkedCount = Array.from(checkboxes).filter(cb => cb.checked).length;

        statusParagraph.textContent = `Số lượng ca làm đã chọn: ${checkedCount}`;
    }

    // Gắn sự kiện 'change' cho mỗi checkbox
    checkboxes.forEach(cb => {
        cb.addEventListener("change", updateCheckedCount);
    });

    // Khởi tạo trạng thái ban đầu
    updateCheckedCount();
});