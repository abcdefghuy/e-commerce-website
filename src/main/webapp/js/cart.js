document.getElementById('checkoutBtn').addEventListener('click', function(e) {
    var selectedProducts = [];
    var checkboxes = document.getElementsByName('selectedProducts');

    // Thu thập tất cả các sản phẩm đã chọn
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            selectedProducts.push(checkboxes[i].value);
        }
    }


    // Nếu không có sản phẩm nào được chọn, ngừng gửi form và hiển thị thông báo
    if (selectedProducts.length === 0) {
        alert("Vui lòng chọn ít nhất một sản phẩm để mua.");
        e.preventDefault(); // Ngừng gửi form
        return;
    }

    // Thêm các sản phẩm đã chọn vào input ẩn
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'listFurnitureID'; // Tên input ẩn
    input.value = selectedProducts.join(','); // Dữ liệu của các sản phẩm chọn, nối lại bằng dấu phẩy
    document.getElementById('purchaseForm').appendChild(input); // Thêm vào form

});
let selectedFurnitureId = null; // Lưu furnitureID khi hiển thị modal

function showConfirmModal(furnitureId) {
    const modal = document.getElementById('confirmCloseModal');
    modal.style.display = 'block';
    selectedFurnitureId = furnitureId; // Lưu furnitureID để dùng khi gửi form
}

function closeModal() {
    const modal = document.getElementById('confirmCloseModal');
    modal.style.display = 'none';
    selectedFurnitureId = null; // Xóa giá trị khi đóng modal
}

function submitForm() {
    const form = document.createElement('form');
    form.action = "../PurchaseServlet";  // URL xử lý form
    form.method = "POST";
    form.style.display = "inline";  // Đảm bảo form không bị ẩn

    // Tạo input ẩn cho hành động
    const actionInput = document.createElement('input');
    actionInput.type = "hidden";
    actionInput.name = "action";
    actionInput.value = "removefromcart";
    form.appendChild(actionInput);

    // Tạo input ẩn cho ID sản phẩm
    const furnitureInput = document.createElement('input');
    furnitureInput.type = 'hidden';
    furnitureInput.name = 'furnitureID';
    furnitureInput.value = selectedFurnitureId;
    form.appendChild(furnitureInput);

    // Đưa form vào body và gửi nó
    document.body.appendChild(form);
    form.submit();  // Gửi form
    closeModal();
}

