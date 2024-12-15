
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="author" content="Untree.co">
  <link rel="shortcut icon" href="favicon.png">

  <meta name="description" content="" />
  <meta name="keywords" content="bootstrap, bootstrap4" />

  <!-- Bootstrap CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
  <link href="css/tiny-slider.css" rel="stylesheet">
  <link href="css/style.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">

  <title>Trang FeedBack</title>
</head>

<body>

<c:import url="../includes/header.jsp" />

<!-- Start Hero Section -->
<div class="hero">
  <div class="container">
    <div class="row justify-content-between">
      <div class="col-lg-5">
        <div class="intro-excerpt">
          <h1>Trang đánh giá</h1>
          <p class="mb-4">Đây là những đánh giá của khách hàng về shop của chúng tôi</p>
          <p><a href="" class="btn btn-secondary me-2">Shop Now</a></p>
        </div>
      </div>
      <div class="col-lg-7">
        <div class="hero-img-wrap">
          <img src="images/couch.png" class="img-fluid">
        </div>
      </div>
    </div>
  </div>
</div>
<!-- End Hero Section -->
<c:if test="${not empty listFeedBack}">
  <c:forEach var="feedback" items="${listFeedBack}" varStatus="status">
    <div class="untree_co-section">
      <div class="container">
        <div class="block">
          <div class="row justify-content-center">
            <div class="col-md-8 col-lg-8 pb-4">

              <div class="row mb-5">
                <h3>Số sao:
                  <c:forEach var="i" begin="1" end="${feedback.rate}">
                    <i class="fas fa-star text-warning"></i>
                  </c:forEach>
                  <c:forEach var="i" begin="1" end="${5 - feedback.rate}">
                    <i class="far fa-star text-warning"></i>
                  </c:forEach>
                </h3>
                <h3>Những hình ảnh: </h3>
                <c:if test="${not empty feedback.imageFeedbacks}">
                  <c:forEach var="i" begin="0" end="${fn:length(feedback.imageFeedbacks) > 3 ? 2 : fn:length(feedback.imageFeedbacks) - 1}">
                    <div class="col-lg-4 mb-3">
                      <div class="card">
                        <img src="data:image/png;base64,${feedback.imageFeedbacks[i].base64Image}" class="card-img-top img-fluid" alt="Feedback Image">
                      </div>
                    </div>
                  </c:forEach>
                </c:if>
              </div>

              <h3>Những sản phẩm đã mua: </h3>
              <ul class="list-unstyled custom-list my-4">
                <c:forEach var="furniture" items="${feedback.order.listFurniture}">
                  <li>
                    <!-- Form cho từng furniture -->
                    <form action="../furnitureServlet" method="post" class="mb-3">
                      <!-- Hidden field để gửi ID hoặc thông tin liên quan -->

                      <input type="hidden" name="furnitureId" value="${furniture.id}">
                      <input type="hidden" name="furnitureCategoryID" value="${furniture.category.id}">



                      <!-- Hiển thị thông tin furniture -->
                      <div>
                        <label for="category-${furniture.id}" class="form-label">Tên sản phẩm:</label>
                        <input type="text" id="category-${furniture.id}" name="categoryName" class="form-control"
                               value="${furniture.category.categoryName}" readonly>
                      </div>

                      <div>
                        <label for="color-${furniture.id}" class="form-label">Màu:</label>
                        <input type="text" id="color-${furniture.id}" name="furnitureColor" class="form-control"
                               value="${furniture.furnitureColor}" readonly>
                      </div>

                      <!-- Nút submit -->
                      <button type="submit" class="btn btn-primary mt-2">Xem</button>
                    </form>
                  </li>
                </c:forEach>
              </ul>

              <form>
                <div class="row">
                  <div class="col-6">
                    <div class="form-group">
                      <label class="text-black" for="fname">Tên khách</label>
                      <input type="text" class="form-control" id="fname" value="${feedback.customer.name}" readonly>
                    </div>
                  </div>
                </div>

                <div class="form-group">
                  <label class="text-black" for="email">Địa chỉ email</label>
                  <input type="email" class="form-control" id="email" value="${feedback.customer.email}" readonly>
                </div>

                <div class="form-group mb-5">
                  <label class="text-black" for="message">Nội dung</label>
                  <textarea name="" class="form-control" id="message" cols="30" rows="5" readonly>${feedback.description}</textarea>
                </div>
              </form>
            </div>
          </div>
          <c:if test="${status.index < fn:length(listFeedBack)-1}">
            <hr class="custom-divider">
          </c:if>
        </div>
      </div>
    </div>
    <!-- End Contact Form -->
  </c:forEach>
</c:if>


<c:import url="../includes/footer.jsp" />

<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/tiny-slider.js"></script>
<script src="js/custom.js"></script>
</body>

</html>


