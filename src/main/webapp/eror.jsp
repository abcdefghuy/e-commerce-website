<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Error Page">
    <meta name="keywords" content="error, failure, message">
    <meta name="author" content="Your Name">

    <link rel="stylesheet" href="<c:url value='/assets/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/assets/css/style.css'/>">
    <title>Error Page</title>
</head>
<body>
<div class="main-wrapper">
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Error</h4>
                    <h6>Something went wrong</h6>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <div class="error-message">
                        <h3>Oops! Something went wrong.</h3>
                        <p>We encountered an issue while processing your request.</p>

                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">
                                <strong>Error: </strong><c:out value="${errorMessage}"/>
                            </div>
                        </c:if>

                        <div class="col-12 mt-4">
                            <a href="<c:url value='/profile.jsp'/>" class="btn btn-primary w-100">Go Back to Profile</a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="<c:url value='/assets/js/jquery-3.6.0.min.js'/>"></script>
<script src="<c:url value='/assets/js/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/assets/js/script.js'/>"></script>
</body>
</html>
