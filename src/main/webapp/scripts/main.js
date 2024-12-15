function previewImage(event) {
    const files = event.target.files;
    const uploadedImagesContainer = document.getElementById("uploaded-images");

    // Xóa nội dung trước đó trong container

    // Lặp qua các file được chọn và tạo các phần tử ảnh preview
    Array.from(files).forEach((file) => {
        const reader = new FileReader();
        reader.onload = (e) => {
            const img = document.createElement("img");
            img.src = e.target.result;
            img.style.width = "100px"; // Điều chỉnh kích thước ảnh preview
            img.style.height = "100px";
            img.style.objectFit = "cover";
            img.style.borderRadius = "5px";
            img.style.border = "1px solid #ddd";
            uploadedImagesContainer.appendChild(img);
        };
        reader.readAsDataURL(file);
    });
}