function confirmAction(orderId, action) {
    const userConfirmed = confirm("Bạn chắc chắn muốn thực hiện thao tác này?");

    if (userConfirmed) {
        // Gửi yêu cầu đến servlet để cập nhật trạng thái
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "../manageOrdersServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onload = function() {
            if (xhr.status === 200) {
                alert("Thực hiện thao tác thành công!");
                // Bạn có thể tự động làm mới trang hoặc cập nhật lại trạng thái trên giao diện
                location.reload(); // Hoặc cập nhật lại phần giao diện nếu cần
            } else {
                alert("Có lỗi xảy ra khi thực hiện thao tác.");
            }
        };

        // Gửi ID đơn hàng đến servlet
        xhr.send("orderId=" + orderId + "&action=" + action);
    }
}

function feedbackOrder(orderId, customerId) {
    // Chuyển hướng sang feedback.jsp với orderId dưới dạng tham số URL
    window.location.href = "/KhachHang/feedback.jsp?orderId=" + orderId + "&customerId=" + customerId;
}

function viewFeedback(orderId) {
    // Create a new XMLHttpRequest
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `../manageOrdersServlet?action=viewFeedback&orderId=${orderId}`, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            let feedbackData; // Declare the variable first
            try {
                feedbackData = JSON.parse(xhr.responseText); // Now parse the response
            } catch (e) {
                console.error('Failed to parse JSON:', e);
                alert("Có lỗi xảy ra khi xử lý dữ liệu phản hồi.");
                return; // Return early to stop further processing if JSON parsing fails
            }
            // Check if the response contains feedback data
            if (feedbackData) {
                // Insert the feedback text into the modal
                const modalBody = document.querySelector("#feedbackModal .modal-body");
                modalBody.innerHTML = `
                    <p><strong>Nội dung: </strong> ${feedbackData.description}</p>
                    <p>
                        <strong>Mức độ hài lòng: </strong>
                        <span style="font-size: 20px; color: gold; letter-spacing: 2px;">
                            ${'★'.repeat(feedbackData.rate)}${'☆'.repeat(5 - feedbackData.rate)}
                        </span>
                    </p>
                    <div class="d-flex flex-wrap justify-content-center" style="gap: 10px;">
                        ${feedbackData.images.map(image => `<img src="${image}" class="img-thumbnail" style="width: 150px; height: 150px;" />`).join('')}
                    </div>`;
                // Show the modal
                showModal();
            } else {
                console.error('Không tìm thấy dữ liệu phản hồi trong phản hồi JSON.');
                alert("Không tìm thấy nội dung phản hồi.");
            }
        } else {
            console.error('Request failed with status: ' + xhr.status);
            alert("Có lỗi xảy ra khi thực hiện thao tác.");
        }
    };

    xhr.onerror = function () {
        alert("Không thể kết nối đến server. Vui lòng thử lại sau.");
    };

    xhr.send(); // Send the request
}

// Function to show the modal
function showModal() {
    const modal = document.getElementById("feedbackModal");

    if (modal) {
        modal.style.display = "block";
    } else {
        console.error("Modal element not found. Ensure the ID 'feedbackModalContent' exists in the HTML.");
    }

    // Close the modal when the user clicks on the close button
    document.getElementById("closeModalBtn").onclick = closeModal;

    // Close the modal when the user clicks outside of it
    window.onclick = function(event) {
        if (event.target == modal) {
            closeModal();
        }
    };
}

// Function to close the modal
function closeModal() {
    const modal = document.getElementById("feedbackModal");
    modal.style.display = "none";
}