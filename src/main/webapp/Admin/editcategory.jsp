<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Chỉnh sửa danh mục sản phẩm ";
        const listStaffElement = document.getElementById("list-category");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Chỉnh sửa danh mục sản phẩm</h4>
                    <h6>Chỉnh sửa thông tin danh mục sản phẩm</h6>
                </div>
            </div>
            <form id="categoryForm" action="category-controller" method="post">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <input type="hidden" name="id" value="${category.categoryID}">
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Tên danh mục sản phẩm</label>
                                    <input type="text" name="categoryName" value="${category.categoryName}" required>
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Nhà sản xuất
                                    </label>
                                    <input type="text" name="manufacturerName" value="${category.manufacture}" required>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="form-group">
                                    <label>Mô tả ngắn</label>
                                    <textarea class="form-control" name="description" required>${category.categoryDescription}</textarea>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <input type="hidden" name="action" value="editCategory">
                                <input type="submit" value="Cập nhập" class="btn btn-submit me-2">
                                <a href="category-controller?action=getCategoryList" class="btn btn-cancel">Quay lại</a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>