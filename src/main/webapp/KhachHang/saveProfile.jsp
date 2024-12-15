<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Save Profile">
    <meta name="keywords" content="profile, save">
    <meta name="author" content="Your Name">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value='/assets/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/assets/css/style.css'/>">

    <!-- SweetAlert2 CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

    <title>Save Profile</title>
</head>
<body>
<div class="main-wrapper">
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Complete Your Profile</h4>
                    <h6>Fill in all the required details and save your profile.</h6>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <form method="POST" action="<c:url value='/saveProfile'/>" enctype="multipart/form-data">
                        <div class="row">
                            <!-- Name -->
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Name</label>
                                    <input
                                            type="text"
                                            class="form-control"
                                            name="name"
                                            placeholder="Enter your full name"
                                            value="${displayName}"
                                    ${empty displayName ? '' : 'readonly'}
                                            required>
                                </div>
                            </div>

                            <!-- Email -->
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" class="form-control" name="email" placeholder="Enter your email" value="${displayEmail}" readonly required>
                                </div>
                            </div>

                            <!-- Phone -->
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Phone</label>
                                    <input type="text" class="form-control" name="phone" placeholder="Enter your phone number" value="${person.phone}" required>
                                </div>
                            </div>

                            <!-- Birth Date -->
                            <div class="col-lg-6 col-sm-12">`
                                <div class="form-group">
                                    <label>Birth Date</label>
                                    <input type="date" class="form-control" name="birthDate" value="${person.birthDate}" required>
                                </div>
                            </div>

                            <!-- Address -->
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Street</label>
                                    <input type="text" class="form-control" name="street" placeholder="Enter your street address" value="${person.address.street}" required>
                                </div>
                                <div class="form-group">
                                    <label>City</label>
                                    <input type="text" class="form-control" name="city" placeholder="Enter your city" value="${person.address.city}" required>
                                </div>
                                <div class="form-group">
                                    <label>Province/State</label>
                                    <input type="text" class="form-control" name="province" placeholder="Enter your province/state" value="${person.address.province}" required>
                                </div>
                                <div class="form-group">
                                    <label>Country</label>
                                    <input type="text" class="form-control" name="country" placeholder="Enter your country" value="${person.address.country}" required>
                                </div>
                            </div>

                            <!-- Profile Image -->
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Profile Image</label>
                                    <input type="file" class="form-control" name="profileImage" accept="image/*">
                                </div>
                            </div>

                            <!-- Save Button -->
                            <div class="col-12 mt-4">
                                <button type="submit" class="btn btn-primary w-100">Save Changes</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery and Bootstrap -->
<script src="<c:url value='/assets/js/jquery-3.6.0.min.js'/>"></script>
<script src="<c:url value='/assets/js/bootstrap.bundle.min.js'/>"></script>

<!-- SweetAlert2 JS -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>

<!-- SweetAlert2 Success Notification -->
<script>
    const urlParams = new URLSearchParams(window.location.search);
    const status = urlParams.get('status');

    if (status === 'success') {
        Swal.fire({
            title: 'Thành công!',
            text: 'Thông tin của bạn đã được lưu thành công.',
            icon: 'success',
            confirmButtonText: 'OK',
            timer: 3000,
            timerProgressBar: true
        }).then(() => {
            window.location.href = '<c:url value="/indexServlet"/>';
        });
    }

</script>
</body>
</html>
