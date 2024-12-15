<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Danh sách khách hàng";
        const listStaffElement = document.getElementById("list-customer");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>
<form action="${pageContext.request.contextPath}/admin-customer-list" method="get"
      id="listForm">
    <div class="page-wrapper">
        <div class="content">

            <div class="page-header">
                <div class="page-title">
                    <h4>Quản Lý Khách Hàng</h4>
                    <h6>Tìm kiếm/xem chi tiết khách hàng </h6>

                </div>

                <div class="button-container" style="display: flex; flex-direction: row; align-items: center; gap: 10px;">
                    <div class="page-btn" id="btnDeleteCustomers" style="margin: 0; padding: 0;">
                        <a class="btn btn-added" style="display: inline-flex; align-items: center; justify-content: center; gap: 5px; text-decoration: none; padding: 10px 20px; font-size: 16px; font-weight: 500; line-height: 1;">
                            <img src="${pageContext.request.contextPath}/assets/img/icons/delete.svg" alt="img" style="width: 16px; height: 16px;">
                            Khóa Khách Hàng
                        </a>
                    </div>
                    <div class="page-btn" id="btnUnlockCustomers" style="margin: 0; padding: 0;">
                        <a class="btn btn-success" style="display: inline-flex; align-items: center; justify-content: center; gap: 5px; text-decoration: none; padding: 10px 20px; font-size: 16px; font-weight: 500; line-height: 1;">
                            <img src="${pageContext.request.contextPath}/assets/img/icons/edit-set.svg" alt="img" style="width: 16px; height: 16px;">
                            Mở Khóa Khách Hàng
                        </a>
                    </div>

                </div>

            </div>

            <div class="card">
                <div class="card-body">
                    <%-- <div class="table-top">--%>
                    <%--&lt;%&ndash; <div class="search-set">&ndash;%&gt;--%>
                    <%--&lt;%&ndash; <div class="search-path">&ndash;%&gt;--%>
                    <%--&lt;%&ndash; <a class="btn btn-filter"
                        id="filter_search">&ndash;%&gt;--%>
                    <%--&lt;%&ndash; <img
                        src="${pageContext.request.contextPath}/assets/img/icons/filter.svg"
                        alt="img">&ndash;%&gt;--%>
                    <%--&lt;%&ndash; <span><img
                            src="${pageContext.request.contextPath}/assets/img/icons/closes.svg"
                            alt="img"></span>&ndash;%&gt;--%>
                    <%--&lt;%&ndash; </a>&ndash;%&gt;--%>
                    <%--&lt;%&ndash; </div>&ndash;%&gt;--%>
                    <%--&lt;%&ndash; </div>&ndash;%&gt;--%>
                    <%-- </div>--%>
                    <div class="search-header">
                        <h6 class="search-title"
                            style="font-size: 1.2em; font-weight: 400; color: #4a4a4a; font-family: 'Poppins', sans-serif; font-style: italic; margin: 0;
           padding: 8px 12px; display: flex; align-items: center; background-color: #f9fbfd; border-radius: 8px;
           box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); text-shadow: 0px 1px 1px rgba(0, 0, 0, 0.1); letter-spacing: 0.5px;">
                            🔍 <span style="margin-left: 10px;">Tìm Kiếm Khách Hàng</span>
                        </h6>
                    </div>
                    <div class="card-body pb-0">

                        <div class="row">
                            <div
                                    class="col-lg-2 col-sm-6 col-12">
                                <div class="form-group">
                                    <input type="text"
                                           name="phone"
                                           value="${customerSearch.phone}"
                                           placeholder="Nhập số điện thoại...">
                                </div>
                            </div>
                            <div
                                    class="col-lg-2 col-sm-6 col-12">
                                <div class="form-group">
                                    <input type="text"
                                           name="name"
                                           value="${customerSearch.name}"
                                           placeholder="Nhập tên... ">
                                </div>
                            </div>
                            <div
                                    class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <input type="text"
                                           name="email"
                                           value="${customerSearch.email}"
                                           placeholder="Nhập email...">
                                </div>
                            </div>
                            <div
                                    class="col-lg-2 col-sm-6 col-12 ms-auto">
                                <button type="button"
                                        class="btn btn-success"
                                        id="btnSearchCustomer">
                                    <svg xmlns="http://www.w3.org/2000/svg"
                                         width="16"
                                         height="16"
                                         fill="currentColor"
                                         class="bi bi-search"
                                         viewBox="0 0 16 16">
                                        <path
                                                d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0">
                                        </path>
                                    </svg>
                                    Tìm Kiếm
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="table-responsive">
                    <table class="table" id="customerList">
                        <thead>
                        <tr>
                            <th>
                                <label class="checkboxs">
                                    <input type="checkbox" id="select-all">
                                    <span class="checkmarks"></span>
                                </label>
                            </th>
                            <th>Tên Khách Hàng</th>
                            <%-- <th>Ngày Sinh</th>--%>
                            <th>Số Điện Thoại</th>
                            <th>Email</th>
                            <th>Địa Chỉ</th>
                            <th>Trạng Thái</th>
                            <th>Hành Động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Duyệt qua danh sách customerList và hiển thị dữ liệu -->
                        <c:forEach var="customer" items="${customerList}">
                            <tr>
                                <td>
                                    <label class="checkboxs">
                                        <input type="checkbox" value="${customer.personID}">
                                        <span class="checkmarks"></span>
                                    </label>
                                </td>
                                <td class="productimgname">
                                    <a href="javascript:void(0);" class="product-img">
                                        <c:choose>
                                            <c:when test="${not empty customer.avatar}">
                                                <img src="data:image/jpeg;base64,${customer.avatar}" alt="Avatar" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://cellphones.com.vn/sforum/wp-content/uploads/2023/10/avatar-trang-4.jpg" alt="Avatar" />
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                    <a href="javascript:void(0);">${customer.name}</a>
                                </td>
                                <td>${customer.phone}</td>
                                <td>${customer.email}</td>
                                <td>${customer.address}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${customer.status == 'Active'}">
                                            <span class="badges bg-lightgreen">Active</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badges bg-lightred">In Active</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a class="me-3" href="${pageContext.request.contextPath}/admin-customer-order?customerId=${customer.personID}" title="Xem Đơn Hàng">
                                        <img src="${pageContext.request.contextPath}/assets/img/icons/product.svg" alt="order">
                                    </a>
                                    <a class="me-3" href="${pageContext.request.contextPath}/admin-customer-furniture?customerId=${customer.personID}" title="Xem Đơn Hàng">
                                        <img src="${pageContext.request.contextPath}/assets/img/icons/eye.svg" alt="order">
                                    </a>

                                    <a class="me-3" href="javascript:void(0);" onclick="deleteCustomer('${customer.personID}', '${customer.status}')" title="Khóa Tài Khoản">
                                        <img src="${pageContext.request.contextPath}/assets/img/icons/delete.svg" alt="delete">
                                    </a>
                                    <a class="me-3" href="javascript:void(0);" onclick="unlockCustomer('${customer.personID}', '${customer.status}')" title="Mở Khóa Tài Khoản">
                                        <img src="${pageContext.request.contextPath}/assets/img/icons/edit.svg" alt="edit">
                                    </a>

                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
                <!-- Phân trang -->
                <div class="container mt-5">
                    <div class="d-flex justify-content-center pagination form-group">
                        <button id="prev-page" type="button" class="btn btn-primary me-2" disabled>&lt;</button>
                        <span id="page-info" class="align-self-center">Page 1 of X</span>
                        <button id="next-page" type="button" class="btn btn-primary ms-2">&gt;</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmDeleteModalLabel">Xác nhận </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#confirmDeleteModal').modal('hide')">
                    <span aria-hidden="true">&times; </span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Bạn có chắc chắn muốn xóa khách hàng này không?</h6>
                <br>
                <div class="form-group">
                    <label for="deleteReason">Chọn lý do xóa:</label>
                    <select id="deleteReason" class="form-control">
                        <option value="">-- Chọn lý do --</option>
                        <option value="Không nhận hàng">Không nhận hàng</option>
                        <option value="Tài khoản bị nghi ngờ lừa đảo">Tài khoản bị nghi ngờ lừa đảo</option>
                        <option value="Yêu cầu của khách hàng">Yêu cầu của khách hàng</option>
                        <option value="Khác">Khác</option>
                    </select>
                </div>
                <div class="form-group">
                    <textarea id="deleteReasonText" class="form-control" placeholder="Nhập lý do khác (nếu có)" style="display: none;"></textarea>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="$('#confirmDeleteModal').modal('hide')">Hủy</button>
                <button type="button" class="btn btn-danger" id="confirmDeleteButton">Xóa</button>
            </div>

        </div>
    </div>
</div>
<div class="modal fade" id="deleteCustomer" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="t">Xác nhận </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#deleteCustomer').modal('hide')">
                    <span aria-hidden="true">&times; </span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Bạn chưa chọn khách hàng trong danh sách ? </h6>
                <br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="$('#deleteCustomer').modal('hide')">Đóng</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="deleteCustomerSuccess" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="thu">Xác nhận </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#deleteCustomerSuccess').modal('hide')">
                    <span aria-hidden="true">&times; </span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Khách hàng đã được xóa thành công ? </h6>
                <br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="$('#deleteCustomerSuccess').modal('hide')">Đóng</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="unlockCustomerSuccess" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="thu1">Xác nhận mở khóa</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#unlockCustomerSuccess').modal('hide')">
                    <span aria-hidden="true">&times; </span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Khách hàng đã được mở khóa thành công ? </h6>
                <br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="$('#unlockCustomerSuccess').modal('hide')">Đóng</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="errorModalLabel">Thông Báo Lỗi</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#errorModal').modal('hide')">
                    <span aria-hidden="true">&times; </span>
                </button>
            </div>
            <div class="modal-body">
                <h6 id="errorMessage"></h6>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="$('#errorModal').modal('hide')">Đóng</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="customerStatus" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="status">Xác nhận </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#customerStatus').modal('hide')">
                    <span aria-hidden="true">&times; </span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Khách hàng này đã được xóa thành công trước đây. </h6>
                <br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="$('#customerStatus').modal('hide')">Đóng</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="unlockCustomerStatus" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="unlockStatus">Xác nhận </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#unlockCustomerStatus').modal('hide')">
                    <span aria-hidden="true">&times; </span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Khách hàng này đang hoạt động . </h6>
                <br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="$('#unlockCustomerStatus').modal('hide')">Đóng</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal bỏ chọn  Khóa -->
<div class="modal fade" id="customerStatusList" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="statusList">Xác nhận </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#customerStatusList').modal('hide')">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" id="btnUncheckCustomers" >Bỏ chọn</button>
                <button type="button" class="btn btn-danger" onclick="$('#customerStatusList').modal('hide')">Đóng</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal bỏ chọn Mở Khóa -->
<div class="modal fade" id="unlockCustomerStatusList" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="statusLit">Xác nhận </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#unlockCustomerStatusList').modal('hide')">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" id="btnUnlockUncheckCustomers" >Bỏ chọn</button>
                <button type="button" class="btn btn-danger" onclick="$('#unlockCustomerStatusList').modal('hide')">Đóng</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal Xác nhận Mở Khóa -->
<div class="modal fade" id="confirmUnlockModal" tabindex="-1" role="dialog" aria-labelledby="confirmUnlockModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmUnlockModalLabel">Xác nhận mở khóa tài khoản</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#confirmUnlockModal').modal('hide')">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <h6>Bạn có chắc chắn muốn mở khóa tài khoản của khách hàng này không?</h6>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="$('#confirmUnlockModal').modal('hide')">Hủy</button>
                <button type="button" class="btn btn-success" id="confirmUnlockButton">Mở Khóa</button>
            </div>
        </div>
    </div>
</div>

<c:import url="footer.jsp"/>

<script src="${pageContext.request.contextPath}/managermentCustomer/searchCustomer.js"></script>
<script src="${pageContext.request.contextPath}/managermentCustomer/pagination.js"></script>

<jsp:include page="${pageContext.request.contextPath}/managermentCustomer/lockCustomer.jsp"></jsp:include>

<jsp:include page="${pageContext.request.contextPath}/managermentCustomer/unlockCustomer.jsp"></jsp:include>
