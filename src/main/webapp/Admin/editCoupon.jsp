<%--
  Created by IntelliJ IDEA.
  User: nmtu
  Date: 11/5/2024
  Time: 8:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%
  List<String> errors = (List<String>) session.getAttribute("errors");

  session.removeAttribute("couponName");
  session.removeAttribute("couponType");
  session.removeAttribute("couponValue");
  session.removeAttribute("startDate");
  session.removeAttribute("endDate");
  session.removeAttribute("useLimit");
  session.removeAttribute("useCondition");
  session.removeAttribute("minOrderValue");
  session.removeAttribute("selectedCategoryIds");
  session.removeAttribute("errors");
%>
<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
  document.addEventListener("DOMContentLoaded", function() {
    document.title = "Chỉnh sửa mã giảm giá";
    const listStaffElement = document.getElementById("create-coupon");
    if (listStaffElement) {
      listStaffElement.classList.add("active");
    }
  });
</script>

  <div class="page-wrapper cardhead">
    <div class="content container-fluid">

      <div class="page-header">
        <div class="row">
          <div class="col">
            <h3 class="page-title"> Cập Nhật Khuyến Mãi</h3>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-12">
          <div class="card">
            <div class="card-header">
              <h5 class="card-title">Thông Tin</h5>
            </div>
            <div class="card-body">
              <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
              </c:if>
              <% if (errors != null && !errors.isEmpty()) { %>
              <div class="alert alert-danger">
                <ul>
                  <% for (String error : errors) { %>
                  <li><%= error %></li>
                  <% } %>
                </ul>
              </div>
              <% } %>
              <form action="CouponController" method="POST">
                <input type="hidden" name="action" value="edit">
                <div class="form-group row">
                  <label class="col-form-label col-md-2">Tên Mã Khuyến Mãi</label>
                  <div class="col-md-10">
                    <input type="text" class="form-control" placeholder="Tên mã Khuyến Mãi" name="couponName" value="${coupon.couponName}" required>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-form-label col-md-2">Loại mã giảm giá</label>
                  <div class="col-md-10">
                    <select class="form-control form-small select" name="couponType" required>
                      <!-- Set default selected to 'percent' -->
                      <option value="percent" ${coupon.couponType == 'percent' ? 'selected' : ''}>Phần trăm (%)</option>
                      <option value="money" ${coupon.couponType == 'money' ? 'selected' : ''}>Số tiền</option>
                    </select>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-form-label col-md-2">Giá trị giảm giá</label>
                  <div class="col-md-10">
                    <input type="text" class="form-control" placeholder="Giá trị giảm giá" name="couponValue" value="${coupon.couponValue}" required>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-form-label col-md-2">Ngày Bắt Đầu</label>
                  <div class="col-md-10">
                    <div class="input-groupicon">
                      <input type="text" placeholder="DD-MM-YYYY" class="datetimepicker" name="startDate" value="<fmt:formatDate value='${coupon.startDate}' pattern='dd-MM-yyyy'/>" required>
                      <div class="addonset">
                        <img src="assets/img/icons/calendars.svg" alt="img">
                      </div>
                    </div>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-form-label col-md-2">Ngày Kết Thúc</label>
                  <div class="col-md-10">
                    <div class="input-groupicon">
                      <input type="text" placeholder="DD-MM-YYYY" class="datetimepicker" name="endDate" value="<fmt:formatDate value='${coupon.endDate}' pattern='dd-MM-yyyy'/>" required>
                      <div class="addonset">
                        <img src="assets/img/icons/calendars.svg" alt="img">
                      </div>
                    </div>
                  </div>
                </div>


                <div class="form-group row">
                  <label class="col-form-label col-md-2">Điều kiện áp dụng</label>
                  <div class="col-md-10">
                    <select id="useCondition" class="form-control select" name="useCondition" required onchange="toggleFields()">
                      <option value="all" ${coupon.useCondition == 'all' ? 'selected' : ''}>Tất cả hóa đơn</option>
                      <option value="min" ${coupon.useCondition == 'min' ? 'selected' : ''}>Trên số tiền nhất định</option>
                      <option value="product" ${coupon.useCondition == 'product' ? 'selected' : ''}>Sản Phẩm</option>
                    </select>
                  </div>
                </div>

                <div class="form-group row" id="minOrderValueDiv" style="display: none;">
                  <label class="col-form-label col-md-2">Số tiền tối thiểu (nếu có)</label>
                  <div class="col-md-10">
                    <input type="text" id="minOrderValue" class="form-control" placeholder="Số tiền tối thiểu" name="minOrderValue" value="${coupon.minOrderValue}">
                  </div>
                </div>

                <div class="form-group row" id="categorySelectDiv" style="display: none;">
                  <label class="col-form-label col-md-2">Danh mục (nếu có)</label>
                  <div class="col-md-10">
                    <select id="categoryIds" class="form-control tagging" multiple="multiple" name="categoryIds">
                      <!-- Lặp qua danh sách tất cả category -->
                      <c:forEach var="category" items="${categoryList}">
                        <option value="${category.id}"
                                <c:forEach var="selectedCategory" items="${selectedCategories}">
                                  <c:if test="${category.id == selectedCategory.id}">
                                    selected
                                  </c:if>
                                </c:forEach>
                        >
                            ${category.categoryName}
                        </option>
                      </c:forEach>
                    </select>
                  </div>
                </div>


                <div class="form-group row">
                  <label class="col-form-label col-md-2">Số lần được áp dụng</label>
                  <div class="col-md-10">
                    <input type="text" class="form-control" placeholder="Số lần được áp dụng" name="useLimit" value="${coupon.useLimit}" required>
                  </div>
                </div>

                <div class="col-lg-12">
                  <button type="submit" class="btn btn-submit me-2">Cập nhật</button>
                  <a href="CouponController" class="btn btn-cancel">Hủy</a>
                </div>

              </form>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</div>

<script>
  $(document).ready(function() {
    // Khởi tạo Select2
    $('#product-select').select2();

    // Lấy giá trị khi nhấn nút
    $('#get-selected-values').click(function() {
      const selectedValues = $('#product-select').val(); // Lấy mảng các giá trị đã chọn

      // Kiểm tra và hiển thị thông báo
      if (selectedValues.length > 0) {
        $('#selected-values-message').html(`Các giá trị đã chọn: ${selectedValues.join(', ')}`);
      } else {
        $('#selected-values-message').html("Không có giá trị nào được chọn.");
      }
    });
  });
</script>

<script>
  function toggleFields() {
    const useCondition = document.getElementById("useCondition").value;
    const minOrderValueDiv = document.getElementById("minOrderValueDiv");
    const minOrderValueInput = document.getElementById("minOrderValue");
    const categorySelectDiv = document.getElementById("categorySelectDiv");
    const categorySelectInput = document.getElementById("categoryIds");

    // Ẩn tất cả các trường và loại bỏ thuộc tính required
    minOrderValueDiv.style.display = "none";
    minOrderValueInput.removeAttribute("required");

    categorySelectDiv.style.display = "none";
    categorySelectInput.removeAttribute("required");

    // Hiển thị trường và thêm thuộc tính required dựa trên giá trị useCondition
    if (useCondition === "min") {
      minOrderValueDiv.style.display = "flex";
      minOrderValueInput.setAttribute("required", "required");
    } else if (useCondition === "product") {
      categorySelectDiv.style.display = "flex";
      categorySelectInput.setAttribute("required", "required");
    }
  }

  // Gọi hàm toggleFields khi tải trang (để xử lý trường hợp chỉnh sửa)
  document.addEventListener("DOMContentLoaded", toggleFields);
</script>
<c:import url="footer.jsp"/>
