<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.ecommercewebsite.ENumeration.EFurnitureStatus" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
  document.addEventListener("DOMContentLoaded", function() {
    document.title = "Danh sách sản phẩm";
    const listStaffElement = document.getElementById("list-product");
    if (listStaffElement) {
      listStaffElement.classList.add("active");
    }
  });
</script>

<div class="page-wrapper">
  <div class="content">
    <div class="page-header">
      <div class="page-title">
        <h4>Danh sách sản phẩm</h4>
        <h6>Quản lý danh sách sản phẩm</h6>
      </div>
      <div class="page-btn">
        <a href="product-controller?action=displayCategory" class="btn btn-added"><img src="assets/img/icons/plus.svg" alt="img" class="me-1">Thêm sản phẩm</a>
      </div>
    </div>

    <div class="card">
      <div class="card-body">
        <div class="table-top">
          <div class="search-set">
            <div class="search-path">
              <a class="btn btn-filter" id="filter_search">
                <img src="assets/img/icons/filter.svg" alt="img">
                <span><img src="assets/img/icons/closes.svg" alt="img"></span>
              </a>
            </div>
            <div class="search-input">
              <a class="btn btn-searchset"><img src="assets/img/icons/search-white.svg" alt="img"></a>
            </div>
          </div>
          <div class="wordset">
            <ul>
              <li>
                <span class="badges bg-lightgreen">Đang bán</span>
              </li>
              <li>
                <span class="badges bg-lightred">Ngừng kinh doanh</span>
              </li>
              <li>
                <span class="badges bg-lightyellow">Hết hàng</span>
              </li>
            </ul>
          </div>
        </div>

        <form action="product-controller" method="GET">
          <div class="card mb-0" id="filter_inputs">
            <div class="card-body pb-0">
              <div class="row">
                <div class="col-lg-12 col-sm-12">
                  <div class="row">
                    <div class="col-lg col-sm-6 col-12">
                      <div class="form-group">
                        <select name="categoryId" class="select">
                          <option value="">Chọn danh mục</option>
                          <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryID}">
                                ${category.categoryName}
                            </option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <div class="col-lg col-sm-6 col-12">
                      <div class="form-group">
                        <select name="priceRange" class="select">
                          <option value="">Giá</option>
                          <option value="<5000000"><5000000</option>
                          <option value="5000000 - 10000000">5000000 - 10000000</option>
                          <option value="10000000 - 20000000">10000000 - 20000000</option>
                          <option value=">20000000">>20000000</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-lg-1 col-sm-6 col-12">
                      <div class="form-group">
                        <input type="hidden" name="action" value="filterFurniture">
                        <button type="submit" class="btn btn-filters ms-auto" >
                          <img src="assets/img/icons/search-whites.svg" alt="img">
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </form>

        <div class="table-responsive">
          <table class="table  datanew">
            <thead>
            <tr>
              <th>
                <label class="checkboxs">
                  <input type="checkbox" id="select-all">
                  <span class="checkmarks"></span>
                </label>
              </th>
              <th>Tên danh mục</th>
              <th>Trạng thái sản phẩm</th>
              <th>Nhà sản xuất</th>
              <th>Mô tả</th>
              <th>Giá</th>
              <th>Màu sắc</th>
              <th>Số lượng</th>
              <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${furnitureList}" varStatus="status">
              <tr>
                <td>
                  <label class="checkboxs">
                    <input type="checkbox">
                    <span class="checkmarks"></span>
                  </label>
                  <input type="hidden" name="id" value="${entry.categoryID}" />
                </td>
                <td class="productimgname" style="max-width: 200px; white-space: normal; overflow: hidden; text-overflow: ellipsis;">
                  <a href="javascript:void(0);" class="product-img">
                    <!-- Hiển thị ảnh từ Base64 -->
                    <img src="data:image/jpeg;base64,${entry.base64ImageData[0]}" alt="Product Image" />
                  </a>
                  <a href="javascript:void(0);">${entry.categoryName}</a>
                </td>
                <td>
                  <c:choose>
                    <c:when test="${entry.quantity == 0}">
                      <!-- Nếu số lượng bằng 0, đặt trạng thái là 'Hết hàng' -->
                      <span class="badges bg-lightyellow">Hết hàng</span>
                    </c:when>
                    <c:when test="${entry.furnitureStatus == 'STOP_SELLING'}">
                      <!-- Nếu trạng thái là 'STOP_SELLING', sử dụng màu đỏ -->
                      <span class="badges bg-lightred">Ngừng kinh doanh</span>
                    </c:when>
                    <c:otherwise>
                      <!-- Nếu trạng thái không phải là 'STOP_SELLING', sử dụng màu xanh -->
                      <span class="badges bg-lightgreen">Đang bán</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>${entry.manufacture}</td>
                <td style="max-width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                    ${entry.furnitureDescription}
                </td>
                <td>${entry.furniturePrice}</td>
                <td>${entry.furnitureColor}</td>
                <td>${entry.quantity}</td> <!-- Số lượng từ DTO -->
                <td>
                  <a class="me-3" href="product-controller?action=displayDetailFurniture&amp;id=${entry.id}&amp;quantity=${entry.quantity}">
                    <img src="assets/img/icons/eye.svg" alt="img">
                  </a>
                  <a class="me-3" href="product-controller?action=displayFurniture&amp;id=${entry.id}">
                    <img src="assets/img/icons/edit.svg" alt="img">
                  </a>
                  <a class="confirm-text" id="delete-btn" data-id="${entry.categoryID}">
                    <img src="assets/img/icons/delete.svg" alt="img">
                  </a>
                  <a class="confirm-text" id="restore-btn" data-id="${entry.categoryID}">
                    <img src="assets/img/icons/restore.svg" alt="img">
                  </a>
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

<script>
  // Lấy tất cả các dòng của bảng
  document.addEventListener("DOMContentLoaded", function() {
    const rows = document.querySelectorAll("tbody tr");

    rows.forEach(row => {
      // Lấy phần tử có class là 'badges' (thường là trạng thái của furniture)
      const EFurnitureStatus = row.querySelector("span.badges");
      const deleteBtn = row.querySelector("#delete-btn");
      const restoreBtn = row.querySelector("#restore-btn");

      // Kiểm tra nếu trạng thái của furniture là 'STOP_SELLING'
      if (EFurnitureStatus && EFurnitureStatus.textContent === 'Ngừng kinh doanh') {
        // Ẩn nút delete, hiển thị nút restore
        deleteBtn.style.display = "none";
        restoreBtn.style.display = "inline-block";
      } else {
        // Hiển thị nút delete, ẩn nút restore
        deleteBtn.style.display = "inline-block";
        restoreBtn.style.display = "none";
      }
    });
  });
</script>

<c:import url="footer.jsp"/>