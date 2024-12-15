$('#btnSearchCustomer').click(function (e) {
    e.preventDefault();

    let phone = $("input[name='phone']").val();
    let name = $("input[name='name']").val();
    let email = $("input[name='email']").val();

    if (phone) {
        let phoneRegex = /^[0-9]+$/; // Đảm bảo chỉ chứa chữ số
        if (!phoneRegex.test(phone)) {
            $('#errorMessage').text("Số điện thoại chỉ được chứa chữ số.");
            $('#errorModal').modal('show');
            return;
        }
    }

    if (name) {
        let nameRegex = /^[a-zA-ZÀ-ỹ\s]+$/;  // Bao gồm cả tiếng Việt và dấu cách
        if (!nameRegex.test(name)) {
            $('#errorMessage').text("Tên không được chứa chữ số hoặc ký tự đặc biệt.");
            $('#errorModal').modal('show');
            return;
        }
    }

    // Kiểm tra email chỉ khi trường không rỗng
    if (email) {
        let emailRegex = /^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9.-]+\.)+[a-zA-Z]{2,}$/;
        if (!emailRegex.test(email)) {
            $('#errorMessage').text("Email không hợp lệ. Đảm bảo có đuôi đúng như .com, .net, .org, v.v.");
            $('#errorModal').modal('show');
            return;
        }
    }

    $('#listForm').submit();
});