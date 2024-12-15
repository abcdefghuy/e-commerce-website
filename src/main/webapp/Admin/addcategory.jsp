<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Thêm danh mục sản phẩm";
        const listStaffElement = document.getElementById("add-category");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Thêm danh mục sản phẩm</h4>
                    <h6>Tạo mới danh mục sản phẩm</h6>
                </div>
            </div>
            <form id="categoryForm" action="category-controller" method="post">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Tên danh mục</label>
                                    <input type="text" name="categoryName" required>
                                </div>
                            </div>
                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Nhà sản xuất
                                    </label>
                                    <input type="text" name="manufacturerName" required>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="form-group">
                                    <label>Mô tả ngắn</label>
                                    <textarea class="form-control" name="description" required></textarea>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <input type="hidden" name="action" value="addCategory">
                                <input type="submit" value="Lưu" class="btn btn-submit me-2">
                                <a href="category-controller?action=getCategoryList" class="btn btn-cancel">Hủy</a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>


