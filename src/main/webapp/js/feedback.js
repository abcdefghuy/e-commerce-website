const listImage = [];
const maxImages = 5;

document.addEventListener("DOMContentLoaded", () => {
    const imageInput = document.getElementById('imageInput');
    const previewContainer = document.getElementById('previewContainer');

    imageInput.addEventListener('change', function (event) {
        const files = event.target.files;

        // Clear previous previews and images
        previewContainer.innerHTML = '';
        listImage.length = 0;

        if (files.length > maxImages) {
            alert(`Bạn chỉ được chọn tối đa ${maxImages} ảnh!`);
            this.value = ''; // Reset file input
            return;
        }

        Array.from(files).forEach((file) => {
            const reader = new FileReader();
            reader.onload = function (e) {
                // Add Base64 string to listImage
                listImage.push(e.target.result);

                // Create preview
                const imgElement = document.createElement('img');
                imgElement.src = e.target.result;
                imgElement.className = 'image-preview';
                previewContainer.appendChild(imgElement);
            };
            reader.readAsDataURL(file); // Convert file to Base64
        });
    });
});

let rate = 0;

function selectIcon(selectedIcon, value) {
    rate = value;
    document.querySelectorAll(".emoji-rate").forEach((emoji) => {
        emoji.classList.remove("selected");
    });
    selectedIcon.classList.add("selected");
}

let description = "";

function feedbackOrder(customerId, orderId) {
    if (rate === 0) {
        alert("Bạn chưa chọn mức độ hài lòng!");
        return; // Dừng thực hiện hàm
    }

    const textFeedback = document.getElementById('textFeedback');
    description = textFeedback ? textFeedback.value : "";

    const feedbackData = {
        action: "feedbackOrder",
        customerId: customerId,
        orderId: orderId,
        rate: rate,
        imageFeedbacks: listImage, // Already in Base64
        description: description
    };

    fetch('../manageOrdersServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(feedbackData)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            alert('Gửi phản hồi thành công!');
            window.location.href = '../manageOrdersServlet?action=loadOrders';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Gửi phản hồi thất bại.');
        });
}
