<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Thêm sản phẩm";
        const listStaffElement = document.getElementById("add-product");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>


<div class="page-wrapper">
    <div class="content">
        <div class="page-header">
            <div class="page-title">
                <h4>Thêm sản phẩm</h4>
                <h6>Tạo mới sản phẩm</h6>
            </div>
        </div>
        <form id="furnitureForm" action="product-controller" method="post" enctype="multipart/form-data">
            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Danh mục</label>
                                <select class="select" name="category" id="categorySelect" required>
                                    <option value="">Chọn danh mục</option>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.categoryID}">${category.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Giá</label>
                                <input type="number" name="price" class="form-control" required>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Màu sắc</label>
                                <select class="select" name="color" required>
                                    <option>Chọn màu sắc</option>
                                    <option>Đen</option>
                                    <option>Nâu</option>
                                    <option>Be</option>
                                    <option>Màu tự nhiên</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Số lượng</label>
                                <input type="number" name="quantity" class="form-control" required min="1" value="1">
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label>Mô tả sản phẩm</label>
                                <textarea class="form-control" name="description" style="height: 150px" required></textarea>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label>Ảnh sản phẩm</label>
                                <div class="image-upload">
                                    <input type="file" id="file-input" name="images" multiple onchange="previewImage(event)">
                                    <div class="image-uploads" id="image-preview">
                                        <img src="assets/img/icons/upload.svg" alt="img">
                                        <h4>Kéo thả file từ thư mục để thêm ảnh</h4>
                                    </div>
                                </div>
                                <div id="uploaded-images" style="margin-top: 15px; display: flex; flex-wrap: wrap; gap: 10px;">
                                    <!-- Hiển thị các ảnh đã tải lên tại đây -->
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <input type="hidden" name="action" value="addProduct">
                            <input type="submit" value="Lưu" class="btn btn-submit me-2">
                            <a href="product-controller?action=getFurnitureList" class="btn btn-cancel">Quay lại</a>
                        </div>
                    </div>
                </div>
            </div>
        </form>

    </div>
</div>
</div>
<c:import url="footer.jsp"/>
