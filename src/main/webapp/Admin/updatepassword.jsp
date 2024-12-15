<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Update Password</title>
  <link
          rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css"
  />
  <link
          rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.min.css"
  />
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }
    .update-password-container {
      background-color: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      max-width: 400px;
      width: 100%;
    }
    h2 {
      text-align: center;
      color: #333;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      margin-bottom: 5px;
      color: #555;
    }
    .form-group input {
      width: 90%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    .form-group input:focus {
      outline: none;
      border-color: #007bff;
    }
    .form-group .error {
      color: red;
      font-size: 14px;
    }
    .submit-btn {
      display: block;
      width: 60%;
      padding: 10px;
      background-color: #007bff;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 16px;
      margin: 20px auto;
      text-align: center;
    }
    .submit-btn:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<div class="update-password-container">
  <h2>Update Password</h2>
  <form action="updatePassword.jsp" method="post">
    <div class="form-group">
      <label for="current-password">Current Password</label>
      <input type="password" id="current-password" name="current-password" placeholder="Enter current password" />
      <div class="error" id="current-password-error"></div>
    </div>

    <div class="form-group">
      <label for="new-password">New Password</label>
      <input type="password" id="new-password" name="new-password" placeholder="Enter new password" />
      <div class="error" id="new-password-error"></div>
    </div>

    <div class="form-group">
      <label for="confirm-password">Confirm New Password</label>
      <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm new password" />
      <div class="error" id="confirm-password-error"></div>
    </div>

    <button type="submit" class="submit-btn">Update Password</button>
  </form>
</div>

<script>
  function validateForm() {
    const currentPassword = document.getElementById("current-password").value;
    const newPassword = document.getElementById("new-password").value;
    const confirmPassword = document.getElementById("confirm-password").value;

    document.getElementById("current-password-error").textContent = "";
    document.getElementById("new-password-error").textContent = "";
    document.getElementById("confirm-password-error").textContent = "";

    let hasError = false;

    if (currentPassword === "") {
      document.getElementById("current-password-error").textContent =
              "Please enter your current password.";
      hasError = true;
    }

    if (newPassword === "") {
      document.getElementById("new-password-error").textContent =
              "Please enter a new password.";
      hasError = true;
    } else if (newPassword.length < 6) {
      document.getElementById("new-password-error").textContent =
              "New password must be at least 6 characters.";
      hasError = true;
    }

    if (confirmPassword !== newPassword) {
      document.getElementById("confirm-password-error").textContent =
              "Passwords do not match.";
      hasError = true;
    }

    return !hasError; // Return false to prevent form submission if there's an error
  }

  document.querySelector("form").addEventListener("submit", function (e) {
    if (!validateForm()) {
      e.preventDefault(); // Prevent form submission if validation fails
    }
  });
</script>
</body>
</html>
