
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Phân ca làm việc";
        const listStaffElement = document.getElementById("set-shift");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>
<%------------------------------------------------%>

<div class="page-wrapper">
    <div class="content">

        <div class="card">
            <div class="card-body">
                <form action="" method="post">
                    <input type="hidden" name="month" value="${currentMonth}">
                    <input type="hidden" name="year" value="${currentYear}">
                    <div class="row">

                        <div class="col-lg-3 border p-3 bg-light rounded shadow">
                            <p class="fw-bold text-primary mb-3"><b>Chọn 1 nhân viên từ danh sách dưới đây:</b></p>
                            <select id="employeeSelect" name="listStaff" class="form-control form-select form-select-md mb-3" size="5">
                                <%@ page import="org.example.ecommercewebsite.DAO.Impl.StaffDAOImpl" %>
                                <c:forEach var="staff" items="${listStaff}">
                                    <option value="${staff.personID}"
                                        ${StaffDAO.getShiftInMonth(staff, currentMonth, currentYear).size() == 15 ? 'disabled' : ''}>
                                        -- ${staff.name} - số ca: ${StaffDAO.getShiftInMonth(staff, currentMonth, currentYear).size()}
                                    </option>
                                </c:forEach>
                            </select>
                            <div id="selectedShifts" class="mt-3 text-center text-muted"></div>
                            <div class="container mt-4 p-3 border rounded bg-white shadow-sm">
                                <p id="status" class="text-center text-muted mb-3"></p>
                                <div class="mb-4">
                                    <p class="fw-bold text-primary">Ca sáng: 8h00 - 11h30:</p>
                                    <ul class="list-group">
                                        <c:forEach var="staff" items="${amStaffs}">
                                            <li class="list-group-item">
                                                - ${staff.name} - ${staff.phone}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <div class="mb-4">
                                    <p class="fw-bold text-success">Ca chiều: 13h00 - 17h00:</p>
                                    <ul class="list-group">
                                        <c:forEach var="staff" items="${pmStaffs}">
                                            <li class="list-group-item">
                                                - ${staff.name} - ${staff.phone}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <c:if test="${not empty warningMessage}">
                                    <div class="alert alert-warning text-center mt-3">
                                            ${warningMessage}
                                    </div>
                                </c:if>
                            </div>
                            <button type="submit" name="action" value="submit" class="btn btn-success w-100 mt-2" onclick="return confirmSubmission()">Xác Nhận</button>
                        </div>

                        <div class="col-lg-9">

                            <div class="mt-4">
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <button type="submit" name="action" value="pre" class="btn btn-submit">Tháng Trước</button>
                                    <h2 id="monthTitle" class="text-center flex-grow-1">Lịch làm việc tháng <span>${currentMonth}</span> - <span>${currentYear}</span></h2>
                                    <button type="submit" name="action" value="next" class="btn btn-submit">Tháng Sau</button>
                                </div>
                                <div class="container mt-5">
                                    <div class="table-responsive">${calendarTable}</div>
                                </div>

                            </div>
                        </div>

                    </div>
                </form>

            </div>
        </div>

    </div>

</div>
</div>
<script src="scripts/countSelectedShift.js"></script>
<script>
    function confirmSubmission() {
        const userConfirmed = confirm("Xác nhận chọn các ca làm việc này?");
        return userConfirmed;
    }
</script>
<c:import url="footer.jsp"/>
