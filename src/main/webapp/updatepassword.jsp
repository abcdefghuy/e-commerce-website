<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Update Password">
  <meta name="keywords" content="password, update, user">
  <meta name="author" content="Your Name">

  <link rel="stylesheet" href="<c:url value='/assets/css/bootstrap.min.css'/>">
  <link rel="stylesheet" href="<c:url value='/assets/css/style.css'/>">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
  <title>Update Password</title>
</head>
<body>

<!-- Include Header -->
<c:import url="/includes/header.jsp" />

<div class="main-wrapper">
  <div class="page-wrapper">
    <div class="content">
      <div class="page-header">
        <div class="page-title">
          <h4>Update Password</h4>
          <h6>Secure your account by updating your password.</h6>
        </div>
      </div>

      <div class="card">
        <div class="card-body">
          <form method="POST" action="/updatePassword">
            <div class="row">
              <!-- Current Password -->
              <div class="col-lg-6 col-sm-12">
                <div class="form-group">
                  <label>Current Password</label>
                  <input type="password" class="form-control" name="currentPassword" placeholder="Enter current password" required>
                </div>
              </div>

              <!-- New Password -->
              <div class="col-lg-6 col-sm-12">
                <div class="form-group">
                  <label>New Password</label>
                  <input type="password" class="form-control" name="newPassword" placeholder="Enter new password" required>
                </div>
              </div>

              <!-- Confirm New Password -->
              <div class="col-lg-6 col-sm-12">
                <div class="form-group">
                  <label>Confirm New Password</label>
                  <input type="password" class="form-control" name="confirmPassword" placeholder="Confirm new password" required>
                </div>
              </div>
            </div>

            <!-- Submit and Cancel -->
            <div class="row">
              <div class="col-12 mt-4">
                <button type="submit" class="btn btn-primary">Update Password</button>
                <a href="<c:url value='profile.jsp' />" class="btn btn-secondary">Cancel</a>
              </div>
            </div>
          </form>
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
  const urlParams = new URLSearchParams(window.location.search);
  const success = urlParams.get('success');
  const error = urlParams.get('error');

  if (success) {
    Swal.fire({
      title: 'Thành công!',
      text: decodeURIComponent(success),
      icon: 'success',
      confirmButtonText: 'OK'
    });
  } else if (error) {
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
