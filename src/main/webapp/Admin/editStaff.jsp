<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Chi tiết nhân viên";// Lấy phần tử bằng id
        const listStaffElement = document.getElementById("list-staff");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>
<%------------------------------------------------%>

<div class="page-wrapper">
    <div class="content">

        <div class="page-header">
            <div class="page-title">
                <h4>Quản lý nhân viên</h4>
                <h6>Thêm/Chỉnh sửa nhân viên</h6>
            </div>
        </div>

        <div class="card">
            <div class="card-body">
                <form action="" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="emp-id" value="${staff.personID}">
                    <div class="row">
                        <c:if test="${not empty message}">
                            <c:if test="${isSuccess == true}">
                                <div id="success-alert" class="alert alert-success alert-dismissible fade show" role="alert">
                                        ${message}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                            <c:if test="${isSuccess == false}">
                                <div id="success-alert" class="alert alert-warning alert-dismissible fade show" role="alert">
                                        ${message}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                        </c:if>
                        <div class="d-flex justify-content-center">
                            <div class="col-lg-3">
                                <div class="form-group avatar-preview text-center">
                                    <%@ page import="org.example.ecommercewebsite.data.ImageUtil" %>
                                    <c:if test="${staff.avatar != null}">
                                        <img id="avatar-image" src="data:image/jpeg;base64,${ImageUtil.DisplayImage(staff.avatar)}" alt="Avatar" class="img-thumbnail mb-3" style="width: 150px; height: 150px;">
                                    </c:if>
                                    <c:if test="${staff.avatar == null}">
                                        <img id="avatar-image" src="images/blankavatar.jpg" alt="Avatar" class="img-thumbnail mb-3" style="width: 150px; height: 150px;">
                                    </c:if>
                                    <label for="avatar" class="btn btn-primary btn-sm">Upload Avatar</label>
                                    <input type="file" class="form-control-file" id="avatar" name="avatar" accept="image/*" onchange="previewAvatar(event)" value="${staff.avatar}" style="display: none;">
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Tên nhân viên</label>
                                <input type="text" name="emp-name" value="${staff.name}" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Email</label>
                                <input type="text" name="email" value="${staff.email}" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Số điện thoại</label>
                                <input type="tel" name="phone" class = "form-control" pattern="^[0-9]{10}$" value="${staff.phone}" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label> Ngày tháng năm sinh</label>
                                <input type="date" id="birth-date" name="birth-date" class = "form-control" value="<fmt:formatDate value='${staff.birthDate}' pattern='yyyy-MM-dd' />" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Quốc gia</label>
                                <input type="text" name="address-country" value = "${staff.address.country}" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Tỉnh/Thành phố</label>
                                <input type="text" name="address-province" value = "${staff.address.province}" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Quận/Huyện</label>
                                <input type="text" name="address-city" value = "${staff.address.city}" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Đường</label>
                                <input type="text" name="address-street" value = "${staff.address.street}" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Tiền lương/Tháng</label>
                                <input type="number" name="salary" class="form-control" value="<fmt:formatNumber value='${staff.salary}' pattern='0.##' />" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Ngày bắt đầu làm việc</label>
                                <input type="date" name = "work-date" class="form-control" value="<fmt:formatDate value='${staff.workDate}' pattern='yyyy-MM-dd' />" required>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Mật khẩu tài khoản:</label>
                                <input type="text" name = "password" class="form-control" value="${staff.password}" readonly>
                            </div>
                        </div>

                        <div class="col-lg-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Xem ca làm việc trong tháng</label>
                                <select name="list-shift" id="list-shift" class="form-control mb-3">
                                    <c:forEach var="shift" items="${listShift}">
                                        <option value="${shift.shiftID}">Ca ngày: <fmt:formatDate value="${shift.shiftDate}" pattern="yyyy-MM-dd" />: từ <span>${shift.startTime} - ${shift.endTime}</span></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="col-lg-12">
                            <button type="submit" name="action" value="edit" class="btn btn-submit me-2" <c:if test="${staff.status == 'InActive'}">disabled</c:if>>Chỉnh sửa</button>
                            <button class="btn btn-cancel" type="button" onclick="window.location.href='${pageContext.request.contextPath}/listStaff'">Quay lại</button>
                        </div>

                    </div>
                </form>
            </div>
        </div>

    </div>
</div>
</div>
<script src="scripts/loadAvatar.js"></script>
<c:import url="footer.jsp"/>