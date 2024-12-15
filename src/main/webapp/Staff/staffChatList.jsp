<%--
  Created by IntelliJ IDEA.
  User: React.useState();
  Date: 11/21/2024
  Time: 7:09 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.example.ecommercewebsite.utils.ImageUtil" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Furni Support</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chatModule.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
</head>
<body>

<div class="wrapper">
  <section class="users">

    <header>


      <a href="${pageContext.request.contextPath}/indexServlet" class="back-icon">
        <i class="fas fa-arrow-left"></i>
      </a>


      <div class="content">
        <c:if test="${customer.avatar != null}">
          <img alt="Customer" src="data:image/jpeg;base64, ${ImageUtil.DisplayImage(customer.avatar)}">
        </c:if>
        <c:if test="${customer.avatar == null}">
          <img alt="Customer" src="https://via.placeholder.com/150">
        </c:if>
        <div class="details">
          <span>${customer.name}</span>
          <p>Active</p>
        </div>
      </div>
    </header>

    <div class="search">
      <span class="text">Chọn nhân viên muốn chat...</span>
      <input type="text" placeholder="Enter name to search...">
      <button><i class="fas fa-search"></i></button>
    </div>

    <%------------------------------------Start Staff List------------------------------------%>
    <div class="users-list" id="user_list">
      <c:forEach var="staff" items="${staffs}">

        <!-------------------------------- Forward chatbox -------------------------------->
        <a href="${pageContext.request.contextPath}/Staff/chatbox?incoming_id=${customerID}&outgoing_id=${staff.personID}&currentRole=${"customer"}">

          <div class="content ${staff.status == 'InActive' ? 'inactive' : ''}">

            <c:if test="${staff.avatar != null}">
              <img alt="Staff" src="data:image/jpeg;base64, ${ImageUtil.DisplayImage(staff.avatar)}">
            </c:if>
            <c:if test="${staff.avatar == null}">
              <img alt="Staff" src="https://via.placeholder.com/150">
            </c:if>


            <div class="details">
                  <span>
                      ${staff.status == 'InActive' ? "NV không còn trên hệ thống" : staff.name}
                  </span>
              <p>
                  ${latestMessages[staff.personID] != null ?
                          latestMessages[staff.personID] : "Chưa có tin nhắn"
                          }
              </p>
            </div>

          </div>


          <c:if test="${staff.status == 'Active'}">
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
