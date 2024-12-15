function confirmLogout() {
    if (confirm("Bạn có chắc chắn muốn đăng xuất?")) {
        window.location.href = "../LogoutServlet"; // Chuyển hướng đến servlet nếu người dùng chọn "OK"
    }
}