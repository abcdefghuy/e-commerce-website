<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="User Profile">
    <meta name="keywords" content="profile, user, details">
    <meta name="author" content="Your Name">

    <link rel="stylesheet" href="<c:url value='/assets/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/assets/css/style.css'/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

    <title>User Profile</title>
</head>
<body>

<!-- Include Header -->
<c:import url="/includes/header.jsp" />

<div class="main-wrapper">
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Profile</h4>
                    <h6>User Profile Details</h6>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <div class="profile-set">
                        <div class="profile-top">
                            <div class="profile-content">
                                <div class="profile-contentimg">
                                    <c:choose>
                                        <c:when test="${not empty person}">
                                            <img src="${avatarUrl != null ? avatarUrl : '/assets/img/default.jpg'}" alt="Profile Image" id="blah">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<c:url value='/assets/img/customer/customer1.jpg'/>" alt="img" id="blah">
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="profileupload">
                                        <input type="file" id="imgInp" name="profileImage">
                                        <a href="javascript:void(0);"><img src="<c:url value='/assets/img/icons/edit-set.svg'/>" alt="edit"></a>
                                    </div>
                                </div>
                                <div class="profile-contentname">
                                    <h2><c:out value="${person.name}" default="William Castillo"/></h2>
                                    <h4>Update your photo and personal details.</h4>
                                </div>
                            </div>
                        </div>
                    </div>

                    <form method="POST" action="/updateProfile" enctype="multipart/form-data">
                        <div class="row">
                            <!-- Full Name -->
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Full Name</label>
                                    <input type="text" class="form-control" name="name" placeholder="Enter your name" value="${person.name}">
                                </div>
                            </div>

                            <!-- Email -->
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" class="form-control" name="email" placeholder="Enter your email" value="${email}" readonly>
                                </div>
                            </div>

                            <!-- Phone -->
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Phone</label>
                                    <input type="text" class="form-control" name="phone" placeholder="Enter your phone number" value="${person.phone}">
                                </div>
                            </div>

                            <!-- Date of Birth -->
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Date of Birth</label>
                                    <input type="date" class="form-control" name="birthDate" value="${birthDate}">
                                </div>
                            </div>

                            <!-- Address -->
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Street</label>
                                    <input type="text" class="form-control" name="street" value="${person.address.street}">
                                </div>
                                <div class="form-group">
                                    <label>City</label>
                                    <input type="text" class="form-control" name="city" value="${person.address.city}">
                                </div>
                                <div class="form-group">
                                    <label>State</label>
                                    <input type="text" class="form-control" name="state" value="${person.address.province}">
                                </div>
                            </div>

                            <!-- Save Button -->
                            <div class="col-12 mt-4">
                                <button type="submit"  class="btn btn-primary w-100" href="../loadProfile">Cập nhật thông tin</button>
                            </div>
                        </div>
                    </form>

                    <c:if test="${showChangePasswordButton}">
                        <div class="col-12 mt-3">
                            <a href="<c:url value='/updatepassword.jsp'/>" class="btn btn-warning w-100">Change Password</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Include Footer -->
<c:import url="/includes/footer.jsp" />

<script src="<c:url value='/assets/js/jquery-3.6.0.min.js'/>"></script>
<script src="<c:url value='/assets/js/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/assets/js/script.js'/>"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    // Preview uploaded image
    const imgInp = document.getElementById('imgInp');
    const blah = document.getElementById('blah');

    imgInp.addEventListener('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                blah.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    // Display success or error message
    const urlParams = new URLSearchParams(window.location.search);
    const success = urlParams.get('success');
    const error = urlParams.get('error');

    if (success === 'true') {
        Swal.fire({
            title: 'Thành công!',
            text: 'Cập nhật thông tin thành công!',
            icon: 'success',
            confirmButtonText: 'OK'
        });
    } else if (error === 'true') {
        Swal.fire({
            title: 'Lỗi!',
            text: 'Đã xảy ra lỗi trong quá trình cập nhật. Vui lòng thử lại.',
            icon: 'error',
            confirmButtonText: 'OK'
        });
    }
</script>
</body>
</html>
