document.querySelectorAll('input[name="paymentMethod"]').forEach((elem) => {
    elem.addEventListener("change", function() {
        document.getElementById("collapsebank").classList.remove("show");
        document.getElementById("collapsecash").classList.remove("show");

        if (this.id === "paymentBank") {
            document.getElementById("collapsebank").classList.add("show");
        } else if (this.id === "paymentCash") {
            document.getElementById("collapsecash").classList.add("show");
        }
    });
});

function updateTotal() {
    let grandTotal = 0;

    // Lặp qua tất cả các input số lượng và tính lại tổng tiền
    document.querySelectorAll('.quantity').forEach(function(input) {
        let quantity = parseInt(input.value);

        // Nếu số lượng nhập vào nhỏ hơn 1, tự động đổi về 1
        if (isNaN(quantity) || quantity < 1) {
            quantity = 1;
            input.value = quantity; // Gán giá trị mặc định là 1
        }
        const price = parseInt(input.getAttribute('data-price'));
        const totalCell = document.getElementById(input.getAttribute('data-total'));

        // Tính lại tổng tiền cho sản phẩm này
        const totalPrice = price * quantity;
        totalCell.textContent = `${Math.round(totalPrice).toFixed(0)}`; // Làm tròn và chuyển thành chuỗi số nguyên

        // Cộng vào tổng tiền toàn bộ
        grandTotal += totalPrice;
    });
    // Cập nhật tổng tiền toàn bộ
    document.getElementById('grand-total').textContent = `${Math.round(grandTotal).toFixed(0)}`; // Làm tròn tổng tiền
    updateCouponDetails()
}

// Hàm cập nhật tổng thanh toán
function updateAmount() {
    // Lấy giá trị tổng tiền (order) và giảm giá từ các phần tử trong giao diện
    let order = document.getElementById('grand-total').textContent;

    // Lấy mã giảm giá (coupon code) đã chọn
    let couponCode = document.getElementById('c_code').value;

    const formData = new URLSearchParams();
    formData.append('couponCode', couponCode);
    formData.append('action', 'coupon');
    formData.append('total', order);
    const furnitureQuantities = getFurnitureQuantities();

    // Thêm dữ liệu số lượng các sản phẩm vào formData
    furnitureQuantities.forEach(furniture => {
        formData.append('listCategoryID', furniture.furnitureID);  // Dùng [] để gửi một mảng
        formData.append('quantity', furniture.quantity);
    });
    // Gửi yêu cầu POST đến servlet
    fetch('PurchaseServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // Đảm bảo rằng servlet có thể xử lý request
        },
        body: formData.toString() // Gửi couponCode trong body
    })
        .then(response => response.text())  // Chuyển đổi phản hồi thành văn bản
        .then(data => {
            let discount = 0;
            if (data === 'FullUse') {
                alert('Mã giảm giá đã hết lượt sử dụng');
            } else if (data !== 'NoCoupon') {
                discount = parseFloat(data);
            }
            const totalAmount = order - discount;
            document.getElementById('discount').textContent = `${Math.round(discount).toFixed(0)}`;
            document.getElementById('amount').textContent = `${Math.round(totalAmount).toFixed(0)}`;
        })
        .catch(error => {
            console.error('Lỗi khi gọi servlet:', error); // Xử lý lỗi nếu có
        });

}

document.getElementById('c_code').addEventListener('change', function () {
    updateCouponDetails(); // Gọi hàm khi sự kiện change xảy ra
});

// Hàm cập nhật thông tin mã giảm giá
function updateCouponDetails() {
    var selectedOption = document.getElementById('c_code').options[document.getElementById('c_code').selectedIndex];

    if (selectedOption.value !== "") {
        var couponType = selectedOption.getAttribute('data-coupon-type');
        var couponValue = parseInt(selectedOption.getAttribute('data-coupon-value'));
        var endDate = selectedOption.getAttribute('data-end-date');
        var conditions = selectedOption.getAttribute('data-conditions');
        var minOrderValue = parseInt(selectedOption.getAttribute('data-min-order-value'));
        var useLimit = parseInt(selectedOption.getAttribute('data-use-limit'));
        var currentUsage = parseInt(selectedOption.getAttribute('data-current-usage'));

        document.getElementById('data-end-date').textContent = endDate;
        document.getElementById('data-conditions').textContent =
            (conditions === 'all')
                ? 'Tất cả sản phẩm'
                : (conditions === 'min')
                    ? 'Giá trị đơn hàng tối thiểu: ' + minOrderValue
                    : 'Các sản phẩm nhất định';
        document.getElementById('data-coupon-value').textContent = (couponType === "money") ? couponValue : couponValue + "%";
        document.getElementById('data-current-usage').textContent = useLimit - currentUsage;

        // Hiển thị chi tiết mã giảm giá
        document.getElementById('coupon-details').style.display = 'block';
    } else {
        // Ẩn chi tiết mã giảm giá nếu không chọn mã
        document.getElementById('coupon-details').style.display = 'none';
    }
    updateAmount(); // Cập nhật tổng thanh toán
}

// lấy list idcategory và số lượng
function getFurnitureQuantities() {
    const furnitureQuantities = [];

    // Lấy tất cả các thẻ <td> chứa sản phẩm
    document.querySelectorAll('td[data-id]').forEach(function(item) {
        const furnitureID = item.getAttribute('data-id');  // Lấy ID từ thuộc tính data-id của <td>
        const quantity = item.querySelector('.quantity').value;  // Lấy giá trị số lượng từ input
        if (furnitureID && quantity > 0) {
            furnitureQuantities.push({ furnitureID, quantity });
        }
    });
    return furnitureQuantities;
}

//Kiểm tra phương thức thanh toán và xử lý
function checkMethodPayment() {
    const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
    let amount = document.getElementById('amount').textContent;
    let action = '';
    let couponCode = document.getElementById('c_code').value;

    // Tạo đối tượng FormData và thêm action cùng paymentMethod vào yêu cầu
    const formData = new URLSearchParams();
    formData.append('couponCode', couponCode);
    formData.append('paymentMethod', paymentMethod);
    const furnitureQuantities = getFurnitureQuantities();
    // Thêm dữ liệu số lượng các sản phẩm vào formData
    furnitureQuantities.forEach(furniture => {
        formData.append('listCategoryID', furniture.furnitureID);  // Dùng [] để gửi một mảng
        formData.append('quantity', furniture.quantity);
    });
    formData.append('amount', amount);
    if (paymentMethod === "bank") {
        action = 'QRCODE'
        formData.append('action', action);
        // Lấy giá trị của mô tả và số tiền từ các trường input
        const descriptionElement = document.getElementById('description');
        let description = descriptionElement.value;

        if (description.length > 15) {
            description = description.substring(0, description.length - 15).trim();
        }

        const now = new Date();
        const dateTimeNow = now.getFullYear().toString() +
            String(now.getMonth() + 1).padStart(2, '0') +
            String(now.getDate()).padStart(2, '0') + ' ' +
            String(now.getHours()).padStart(2, '0') +
            String(now.getMinutes()).padStart(2, '0') +
            String(now.getSeconds()).padStart(2, '0');

        description += " " + dateTimeNow;

        descriptionElement.value = description;

        console.log("Updated description:", description);
        const amount = document.getElementById('amount').textContent || document.getElementById('amount').innerText;
        // Hiển thị lớp phủ và thông báo chờ
        document.getElementById('overlay').style.display = 'flex';
        // Hiển thị modal thông báo thanh toán ngân hàng
        formData.append('description', description);
        formData.append('amount', amount);
        fetch('PurchaseServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        }).then(response => response.text())  // Đọc phản hồi trả về dưới dạng chuỗi (URL)
            .then(qrCodeUrl => {
                // Kiểm tra nếu URL mã QR hợp lệ
                document.getElementById('overlay').style.display = 'none';
                if (qrCodeUrl !== 'False') {
                    showPaymentModal(qrCodeUrl,amount,description,couponCode);
                } else {
                    alert('Sản phẩm không đủ hoặc đã ngưng kinh doanh!');
                }
            })
    } else {
        action = 'payment';
        formData.append('action', action);
        fetch('PurchaseServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        }).then(response => response.text())  // Đọc phản hồi dưới dạng văn bản
            .then(data => {
                if (data === 'True') {
                    alert('Đặt hàng thành công!');  // Thông báo khi thành công
                    window.location.href = "shopServlet"; // Chuyển trang
                } else if (data === 'False'){
                    alert('Đặt hàng thất bại, sản phẩm không đủ hoặc đã ngưng kinh doanh!');  // Thông báo khi thất bại
                } else
                {
                    alert('Mã giảm giá đã hết lượt sử dụng!');
                    location.reload();
                }
            })
    }
}

let checkPaidInterval; // Biến lưu ID của setInterval
// Hàm hiển thị modal với mã QR
function showPaymentModal(qrCodeUrl,amount,description,couponCode) {
    const modal = document.getElementById('paymentModal');
    const qrCodeImage = document.getElementById('qr-code-img');
    qrCodeImage.src = qrCodeUrl;
    modal.style.display = 'none';
    modal.style.display = 'block';
    document.body.classList.add('modal-open'); // Ngăn cuộn trang
    // Dừng bộ đếm trước đó nếu có
    const timerDisplay = document.getElementById("timer");
    clearInterval(countdown);

    // Reset thời gian đếm ngược về 600 giây (10 phút)
    countdownTime = 600;
    timerDisplay.textContent = `${String(Math.floor(countdownTime / 60)).padStart(2, '0')}:${String(countdownTime % 60).padStart(2, '0')}`;
    startCountdown(); // Gọi hàm bắt đầu đếm ngược
    setTimeout(() =>{
        checkPaidInterval = setInterval(() => {
            checkPaid(amount,description,couponCode);
        },1000);
    },20000);
}
// Đóng modal khi nhấn vào dấu X
function closeModal() {
    // Hiển thị modal xác nhận
    document.getElementById("confirmCloseModal").style.display = "block";
    document.body.classList.remove('modal-open'); // Cho phép cuộn lại

}

// Khi nhấn Đồng ý trong modal xác nhận
document.getElementById("confirmCloseYes").onclick = function() {
    document.getElementById("paymentModal").style.display = "none";
    document.getElementById("confirmCloseModal").style.display = "none"; // Ẩn modal xác nhận
    clearInterval(checkPaidInterval);
};

// Khi nhấn Hủy trong modal xác nhận
document.getElementById("confirmCloseNo").onclick = function() {
    document.getElementById("confirmCloseModal").style.display = "none"; // Ẩn modal xác nhận
};

//Bộ đếm thời gian của mã QR
let countdown;
let countdownTime = 600; // 10 phút tính bằng giây
function startCountdown() {
    const timerDisplay = document.getElementById("timer");

    countdown = setInterval(() => {
        let minutes = Math.floor(countdownTime / 60);
        let seconds = countdownTime % 60;

        timerDisplay.textContent =
            `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;

        if (countdownTime <= 0) {
            clearInterval(countdown);
            closeModal();
        }
        countdownTime--;
    }, 1000);
}

//Kiểm tra chuyển khoản ngân hàng và đk dừng
let isSsucess = false
let stop = true;
async function checkPaid(price, content,couponCode) {
    if(isSsucess)
    {
        if(stop) {
            const amount = document.getElementById('amount').textContent || document.getElementById('amount').innerText;
            const formDataNew = new URLSearchParams();
            const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
            const action = 'payment';
            const furnitureQuantities = getFurnitureQuantities();
            stop = false;
            furnitureQuantities.forEach(furniture => {
                formDataNew.append('listCategoryID', furniture.furnitureID);  // Dùng [] để gửi một mảng
                formDataNew.append('quantity', furniture.quantity);
            });
            formDataNew.append('couponCode', couponCode);
            formDataNew.append('paymentMethod', paymentMethod);
            formDataNew.append('amount', amount);
            formDataNew.append('action', action);
            fetch('PurchaseServlet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formDataNew.toString()
            }).then(response => response.text())  // Đọc phản hồi dưới dạng văn bản
                .then(data => {
                    if (data === 'True') {
                        alert('Đặt hàng thành công!');  // Thông báo khi thành công
                        window.location.href = "shopServlet"; // Chuyển trang
                    }
                    else if (data === 'False'){
                        alert('Đặt hàng thất bại, sản phẩm không đủ hoặc đã ngưng kinh doanh!');  // Thông báo khi thất bại
                    } else
                    {
                        alert('Mã giảm giá đã hết lượt sử dụng!');
                        location.reload();
                    }
                })
        }
    }
    else
    {
        try {
            const response = await fetch("https://script.google.com/macros/s/AKfycbyXH6MugTcWjRNvC5rH_ynvCvq44B-VWdKwCleA5fEYKemBisrgSCdBXR_Z6QccAybM/exec");
            const data = await response.json();
            const lastPaid = data.data[data.data.length -1];
            lastPrice = lastPaid["Giá trị"];
            lastContent = lastPaid["Mô tả"];
            console.log(lastPaid["Giá trị"]);
            console.log(lastPaid["Mô tả"]);
            console.log(content);
            console.log(couponCode);
            if(lastPrice>= price && lastContent.includes(content))
            {
                isSsucess = true;
            }
        } catch (error) {
            console.error("Lỗi", error);
        }
    }
}