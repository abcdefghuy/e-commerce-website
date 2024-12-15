<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.example.ecommercewebsite.util.ImageUtil" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Chat</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chatModule.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
</head>
<body>

<div class="wrapper">
	<section class="users">

		<header>

			<a href="${pageContext.request.contextPath}/Staff/dashboard.jsp" class="back-icon">
				<i class="fas fa-arrow-left"></i>
			</a>


			<div class="content">
				<c:if test="${staff.avatar != null}">
					<img alt="Staff" src="data:image/jpeg;base64, ${ImageUtil.DisplayImage(staff.avatar)}">
				</c:if>
				<c:if test="${staff.avatar == null}">
					<img alt="Staff" src="https://via.placeholder.com/150">
				</c:if>
				<div class="details">
					<span>${staff.name}</span>
					<p>Active</p>
				</div>
			</div>
		</header>

		<div class="search">
			<span class="text">Chọn khách hàng để bắt đầu chat...</span>
			<input type="text" placeholder="Enter name to search...">
			<button><i class="fa fa-search"></i></button>
		</div>

		<%------------------------------------Start Customer List------------------------------------%>
		<div class="users-list" id="user_list">
			<c:forEach var="customer" items="${customers}">

				<!-- Chuyển hướng đến chatbox khi nhấn vào khách hàng -->
				<a href="${pageContext.request.contextPath}/Staff/chatbox?incoming_id=${staffID}&outgoing_id=${customer.personID}&currentRole=${"staff"}">

					<div class="content ${customer.status == 'InActive' ? 'inactive' : ''}">
						<c:if test="${customer.avatar != null}">
							<img alt="Customer" src="data:image/jpeg;base64, ${ImageUtil.DisplayImage(customer.avatar)}">
						</c:if>
						<c:if test="${customer.avatar == null}">
							<img alt="Customer" src="https://via.placeholder.com/150">
						</c:if>
						<div class="details">
							<span>
									${customer.status == 'InActive' ? "KH không còn trên hệ thống" : customer.name}
							</span>
							<p>
									${latestMessages[customer.personID] != null ? latestMessages[customer.personID] : "Chưa có tin nhắn"}
							</p>
						</div>
					</div>


					<c:if test="${customer.status == 'Active'}">
						<div class="status-dot online">
							<i class="fas fa-circle"></i>
						</div>
					</c:if>


				</a>
			</c:forEach>
		</div>
		<%------------------------------------End Customer List------------------------------------%>

	</section>
</div>

</body>
</html>
