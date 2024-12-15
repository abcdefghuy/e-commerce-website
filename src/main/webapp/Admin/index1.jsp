<%--
  Created by IntelliJ IDEA.
  User: nmtu
  Date: 11/5/2024
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
  document.addEventListener("DOMContentLoaded", function() {
    document.title = "Thống kê doanh thu";
    const listStaffElement = document.getElementById("revenue");
    if (listStaffElement) {
      listStaffElement.classList.add("active");
    }
  });
</script>

<div class="page-wrapper">
  <div class="content">

    <div class="row">
      <div class="col-lg-7 col-sm-12 col-12 d-flex">

        <div class="card flex-fill">
          <div class="card-header pb-0 d-flex justify-content-between align-items-center">
            <h5 class="card-title mb-0">Biểu đồ bán hàng trong năm</h5>
            <div class="graph-sets">
              <ul>
                <li>
                  <span>Bán</span>
                </li>
                <li>
                  <span>Nhập</span>
                </li>
              </ul>
              <div class="dropdown mb-3">
                <button class="btn btn-white btn-sm dropdown-toggle" type="button" id="dropdownMenuButton"
                        data-bs-toggle="dropdown" aria-expanded="false" data-series-revenue="${revenueListNow}"
                        data-series-sales="${salesListNow}"
                        data-series-labels="${availableYears}"
                        data-series-label="${time}">
                  <span id="selectedYear">${year}</span> <img src="assets/img/icons/dropdown.svg" alt="img" class="ms-2">
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                  <li>
                    <a href="statistics?page=index1&year=all" class="dropdown-item">
                      Tất Cả
                    </a>
                  </li>
                  <c:forEach var="availableYear" items="${availableYears}">
                    <li>
                      <a href="statistics?page=index1&year=${availableYear}" class="dropdown-item">
                          ${availableYear}
                      </a>
                    </li>
                  </c:forEach>
                </ul>
              </div>

            </div>
          </div>
          <div class="card-body">
            <div id="mixed-chart"></div>
          </div>
        </div>
      </div>
      <div class="col-lg-5 col-sm-12 col-12 d-flex">
        <div class="card flex-fill">
          <div class="card-header pb-0 d-flex justify-content-between align-items-center">
            <h4 class="card-title mb-0">Tổng quan</h4>
          </div>
          <div class="card-body d-flex justify-content-center">

            <div class="col-lg-3 col-sm-6 col-12 d-flex flex-column align-items-center justify-content-center" >
              <div class="dash-widget dash2" style="width: 12cm;">
                <div class="dash-widgetimg">
                  <span><img src="assets/img/icons/dash3.svg" alt="img"></span>
                </div>
                <div class="dash-widgetcontent">
                  <h5><span class="counters" data-count="${totalRevenue}"></span> VNĐ</h5>
                  <h6>Doanh Thu</h6>
                </div>
              </div>

              <div class="dash-count das3" style="width: 12cm;">
                <div class="dash-counts">
                  <h4><span class="counters" data-count="${totalSales}"></span></h4>
                  <h5>Hóa đơn bán</h5>
                </div>
                <div class="dash-imgs">
                  <i data-feather="file"></i>
                </div>
              </div>
            </div>



          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="card mb-0">
    <div class="card-body">
      <h4 class="card-title">Danh sách Hóa Đơn</h4>
      <div class="table-responsive">
        <table class="table datanew">
          <thead>
          <tr>
            <th>Mã hóa đơn</th>
            <th>Mã đơn hàng</th>
            <th>Phương thức Thanh Toán</th>
            <th>Tổng Giá Trị</th>
            <th>Ngày Thanh Toán</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="payment" items="${payments}">
            <tr>
              <td>${payment.paymentID}</td>
              <td><a href="javascript:void(0);" onclick="viewListProduct(${payment.order.id})">${payment.order.id}</a></td>
              <td>${payment.method}</td>
              <td>${payment.getLongMoney()}</td>
              <td><fmt:formatDate value="${empty payment.paymentDate ? payment.order.orderDate : payment.paymentDate}" pattern="dd-MM-yyyy"/></td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</div>
<div class="modal fade" id="productOfOrderList" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
  <div class="modal-dialog modal-xl" role="document" style="max-width: 90%; width: 90%;">
    <div class="modal-content" id="orderModalContent">
      <div class="modal-header" id="orderModalHeader">
        <h5 class="modal-title" id="modalTitle">Chi Tiết Hóa Đơn</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#productOfOrderList').modal('hide')">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="orderModalBody">
        <!-- Thông tin thanh toán -->
        <div id="paymentInfo">
          <h5>Thông Tin Thanh Toán</h5>
          <table>
            <tr>
              <td class="payment-label">Tổng chi phí:</td>
              <td class="payment-value" id="paymentTotalPrice">13,500,000 VNĐ</td>
            </tr>
            <tr>
              <td class="payment-label">Mã giảm giá:</td>
              <td class="payment-value" id="paymentCoupon">Giảm giá 5%</td>
            </tr>
            <tr>
              <td class="payment-label">Phương thức thanh toán:</td>
              <td class="payment-value" id="paymentMethod">Cash on Delivery</td>
            </tr>
            <tr>
              <td class="payment-label">Tiền thanh toán:</td>
              <td class="payment-value" id="paymentMoney">2,000,000 VNĐ</td>
            </tr>
          </table>
        </div>
        <!-- Danh sách sản phẩm -->
        <div id="productList">
          <h5>Danh Sách Sản Phẩm</h5>
          <div class="table-responsive1">
            <table class="table table-bordered1">
              <thead>
              <tr>
                <th>STT</th>
                <th>Nội Thất</th>
                <th>Mô Tả</th>
                <th>Giá Một Sản Phẩm</th>
                <th>Trạng Thái</th>
                <th>Số Lượng</th>
                <th>Tổng Chi Phí</th>
              </tr>
              </thead>
              <tbody id="productOrderTableBody">
              <!-- Dữ liệu sẽ được điền động từ JavaScript -->
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal-footer" id="orderModalFooter">
        <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="$('#productOfOrderList').modal('hide')">Đóng</button>
      </div>
    </div>
  </div>
</div>
</div>
<c:import url="footer.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/ordercustomer/loadProductOfOrder.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/ordercustomer/customer.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/ordercustomer/modelDetailBill.css">