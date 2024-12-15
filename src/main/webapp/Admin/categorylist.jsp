<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
  document.addEventListener("DOMContentLoaded", function() {
    document.title = "Danh sách danh mục sản phẩm";
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
          <h4>Danh sách danh mục sản phẩm</h4>
          <h6>Xem danh mục sản phẩm</h6>
        </div>
        <div class="page-btn">
          <a href="category-controller?action=null" class="btn btn-added">
            <img src="assets/img/icons/plus.svg" class="me-1" alt="img">Thêm danh mục sản phẩm mới
          </a>
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
                  <a data-bs-toggle="tooltip" data-bs-placement="top" title="pdf"><img src="assets/img/icons/pdf.svg" alt="img"></a>
                </li>
                <li>
                  <a data-bs-toggle="tooltip" data-bs-placement="top" title="excel"><img src="assets/img/icons/excel.svg" alt="img"></a>
                </li>
                <li>
                  <a data-bs-toggle="tooltip" data-bs-placement="top" title="print"><img src="assets/img/icons/printer.svg" alt="img"></a>
                </li>
              </ul>
            </div>
          </div>
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
                <th>Nhà sản xuất</th>
                <th>Mô tả ngắn</th>
                <th>Được tạo bởi</th>
                <th>Hành động</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="entry" items="${categoryList}">
                <tr>
                  <td>
                    <label class="checkboxs">
                      <input type="checkbox">
                      <span class="checkmarks"></span>
                    </label>
                    <input type="hidden" name="categoryID" value="${entry.categoryID}">
                  </td>
                  <td>
                    <a href="javascript:void(0);">${entry.categoryName}</a>
                  </td>
                  <td>${entry.manufacture}</td>
                  <td style="max-width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">${entry.categoryDescription}</td>
                  <td>Admin</td>
                  <td>
                    <a class="me-3" href="category-controller?action=displayCategory&amp;categoryID=${entry.categoryID}">
                      <img src="assets/img/icons/edit.svg" alt="img">
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
<c:import url="footer.jsp"/>