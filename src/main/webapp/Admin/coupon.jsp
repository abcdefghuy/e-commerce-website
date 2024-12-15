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
<%@ page import="org.example.ecommercewebsitebusiness.Category" %>

<%
  String couponName = (String) session.getAttribute("couponName");
  String couponType = (String) session.getAttribute("couponType");
  String couponValue = (String) session.getAttribute("couponValue");
  String startDate = (String) session.getAttribute("startDate");
  String endDate = (String) session.getAttribute("endDate");
  String useLimit = (String) session.getAttribute("useLimit");
  String useCondition = (String) session.getAttribute("useCondition");
  String successMessageAdd = (String) session.getAttribute("successMessageAdd");
  List<Category> categoryList = (List<Category>) session.getAttribute("categoryList");

  List<Category> selectedCategories = (List<Category>) session.getAttribute("selectedCategories");
  String minOrderValue = (String) session.getAttribute("minOrderValue");
  List<String> errors = (List<String>) session.getAttribute("errors");


  // Xóa session sau khi sử dụng để tránh giữ dữ liệu không cần thiết
  session.removeAttribute("couponName");
  session.removeAttribute("successMessageAdd");
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
    document.title = "Tạo mã khuyến mãi";
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
            <h3 class="page-title">Tạo Mã Khuyến Mãi</h3>
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
              <% if (successMessageAdd != null && !successMessageAdd.isEmpty()) { %>
              <div class="alert alert-success">
                <ul>
                  <li><%= successMessageAdd %></li>
                </ul>
              </div>
              <% } %>
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
                <input type="hidden" name="action" value="add">
                <div class="form-group row">
                  <label class="col-form-label col-md-2">Tên Mã Khuyến Mãi</label>
                  <div class="col-md-10">
                    <input type="text" class="form-control" placeholder="Tên mã Khuyến Mãi" name="couponName"
                           value="<%= couponName != null ? couponName : "" %>" required>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-form-label col-md-2">Loại giảm giá</label>
                  <div class="col-md-10">
                    <select class="form-control form-small select" name="couponType" required>
                      <option value="percent" <%= "percent".equals(couponType) ? "selected" : "" %>>Phần trăm (%)</option>
                      <option value="money" <%= "money".equals(couponType) ? "selected" : "" %>>Số tiền</option>
                    </select>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-form-label col-md-2">Giá trị giảm giá</label>
                  <div class="col-md-10">
                    <input type="text" class="form-control" placeholder="Giá trị giảm giá" name="couponValue"
                           value="<%= couponValue != null ? couponValue : "" %>" required>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-form-label col-md-2">Ngày Bắt Đầu</label>
                  <div class="col-md-10">
                    <input type="text" placeholder="DD-MM-YYYY" class="datetimepicker" name="startDate"
                           value="<%= startDate != null ? startDate : "" %>" required>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-form-label col-md-2">Ngày Kết Thúc</label>
                  <div class="col-md-10">
                    <input type="text" placeholder="DD-MM-YYYY" class="datetimepicker" name="endDate"
                           value="<%= endDate != null ? endDate : "" %>" required>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-form-label col-md-2">Điều kiện áp dụng</label>
                  <div class="col-md-10">
                    <select id="useCondition" class="form-control select" name="useCondition" required onchange="toggleFields()">
                      <option value="all" <%= "all".equals(useCondition) ? "selected" : "" %>>Tất cả hóa đơn</option>
                      <option value="min" <%= "min".equals(useCondition) ? "selected" : "" %>>Trên số tiền nhất định</option>
                      <option value="product" <%= "product".equals(useCondition) ? "selected" : "" %>>Sản Phẩm</option>
                    </select>
                  </div>
                </div>

                <div class="form-group row" id="minOrderValueDiv" style="display: none;">
                  <label class="col-form-label col-md-2">Số tiền tối thiểu (nếu có)</label>
                  <div class="col-md-10">
                    <input type="text" id="minOrderValue" class="form-control" placeholder="Số tiền tối thiểu"
                           name="minOrderValue" value="<%= minOrderValue != null ? minOrderValue : "" %>">
                  </div>
                </div>

                <div class="form-group row" id="categorySelectDiv" style="display: none;">
                  <label class="col-form-label col-md-2">Danh mục (nếu có)</label>
                  <div class="col-md-10">
                    <select id="categoryIds" class="form-control tagging" multiple="multiple" name="categoryIds">
                      <%
                        // Lặp qua tất cả các category trong danh sách categoryList
                        for (Category category : categoryList) {
                          boolean isSelected = false;
                          // Kiểm tra xem category có trong selectedCategories không
                          if (selectedCategories != null) {
                            for (Category selectedCategory : selectedCategories) {
                              if (category.getId().equals(selectedCategory.getId())) {
                                isSelected = true;
                                break;
                              }
                            }
                          }
                      %>
                      <option value="<%= category.getId() %>"
                              <%= isSelected ? "selected" : "" %> >
                        <%= category.getCategoryName() %>
                      </option>
                      <% } %>
                    </select>
                  </div>
                </div>


                <div class="form-group row">
                  <label class="col-form-label col-md-2">Số lần được áp dụng</label>
                  <div class="col-md-10">
                    <input type="text" class="form-control" placeholder="Số lần được áp dụng" name="useLimit"
                           value="<%= useLimit != null ? useLimit : "" %>" required>
                  </div>
                </div>

                <div class="col-lg-12">
                  <button type="submit" class="btn btn-submit me-2">Thêm</button>
                </div>
              </form>
            </div>
          </div>
          <div class="card">
            <div class="table-responsive">
              <table class="table datanew">
                <thead>
                <tr>
                  <th>Tên Mã Khuyến Mãi</th>
                  <th>Kiểu Giảm Giá</th>
                  <th>Giá Trị Giảm Giá</th>
                  <th>Ngày Bắt Đầu</th>
                  <th>Ngày Kết Thúc</th>
                  <th>Điều kiện áp dụng</th>
                  <th>Số lần được áp dụng</th>
                  <th>Số lần đã áp dụng</th>
                  <th>Hành Động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="coupon" items="${couponList}">
                  <tr>
                    <td>${coupon.couponName}</td>
                    <td>
                      <c:choose>
                        <c:when test="${coupon.couponType == 'percent'}">Phần trăm</c:when>
                        <c:when test="${coupon.couponType == 'money'}">Số tiền</c:when>
                      </c:choose>
                    </td>
                    <td>${coupon.couponValue}</td>
                    <td><fmt:formatDate value="${coupon.startDate}" pattern="dd-MM-yyyy" /></td>
                    <td><fmt:formatDate value="${coupon.endDate}" pattern="dd-MM-yyyy" /></td>
                    <td>
                      <c:choose>
                        <c:when test="${coupon.useCondition == 'all'}">Tất cả hóa đơn</c:when>
                        <c:when test="${coupon.useCondition == 'min'}">Trên số tiền nhất định</c:when>
                        <c:when test="${coupon.useCondition == 'product'}">Sản Phẩm</c:when>
                      </c:choose>
                    </td>
                    <td>${coupon.useLimit}</td>
                    <td>${coupon.currentUsage}</td>
                    <td>
                      <a class="me-3" href="CouponController?action=edit&id=${coupon.couponID}">
                        <img src="${pageContext.request.contextPath}/assets/img/icons/edit.svg" alt="Edit">
                      </a>
                      <c:if test="${coupon.currentUsage == 0}">
                        <form id="deleteForm" action="CouponController" method="POST" style="display:none;">
                          <input type="hidden" name="action" value="delete">
                          <input type="hidden" name="id" value="${coupon.couponID}">
                        </form>
                        <a class="confirm-click" href="javascript:void(0);">
                          <img src="assets/img/icons/delete.svg" alt="Delete">
                        </a>
                      </c:if>
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
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
