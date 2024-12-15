<%--
  Created by IntelliJ IDEA.
  User: nmtu
  Date: 11/5/2024
  Time: 8:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:import url="header.jsp" />
<%--------------------------------------------------------%>
<c:import url="sidebar.jsp" />
<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.title = "Thống kê sản phẩm";
        const listStaffElement = document.getElementById("top-product");
        if (listStaffElement) {
            listStaffElement.classList.add("active");
        }
    });
</script>

    <div class="page-wrapper">
        <div class="content">
            <div class="row" style="justify-content: center;">
                <div class="col-lg-3 col-sm-6 col-12">
                    <div class="dash-widget">
                        <div class="dash-widgetimg">
                            <span><img src="data:image/png;base64, ${imageLists[0]}" alt="img"></span>
                        </div>
                        <div class="dash-widgetcontent">
                            <h5>Bán chạy nhất</h5>
                            <h6>${topCategoriesName}</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6 col-12">
                    <div class="dash-widget">
                        <div class="dash-widgetimg">
                            <span><img src="assets/img/icons/dash4.svg" alt="img"></span>
                        </div>
                        <div class="dash-widgetcontent">
                            <h5>Tổng sản phẩm bán ra</h5>
                            <h6><span class="counters" data-count="${totalSale}"></span> </h6>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-7 col-sm-12 col-12 d-flex">
                    <div class="card flex-fill">
                        <div class="card-header pb-0 d-flex justify-content-between align-items-center">
                            <h5 class="card-title mb-0">Sản Phẩm</h5>
                            <div class="graph-sets">

                                <div class="dropdown mb-3">
                                    <button class="btn btn-white btn-sm dropdown-toggle" type="button" id="dropdownMenuButton"
                                            data-bs-toggle="dropdown" aria-expanded="false"
                                            data-series='${categorySales}'
                                            data-categories='${jsonCategoriesName}'>
                                        <span id="selectedYear">${year}</span> <img src="assets/img/icons/dropdown.svg" alt="img" class="ms-2">
                                    </button>
                                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <li>
                                            <a href="statistics?page=index2&year=all" class="dropdown-item">
                                                Tất Cả
                                            </a>
                                        </li>
                                        <c:forEach var="availableYear" items="${availableYears}">
                                            <li>
                                                <a href="statistics?page=index2&year=${availableYear}" class="dropdown-item">
                                                        ${availableYear}
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div id="s-bar" class="chart-set"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-5 col-sm-12 col-12 d-flex">
                    <div class="card flex-fill">
                        <div class="card-header pb-0 d-flex justify-content-between align-items-center">
                            <h4 class="card-title mb-0">Top 5 Sản Phẩm Bán Nhiều Nhất</h4>
                            <div class="dropdown">
                                <a href="javascript:void(0);" data-bs-toggle="dropdown" aria-expanded="false" class="dropset">
                                    <i class="fa fa-ellipsis-v"></i>
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <li>
                                        <a href="productlist.html" class="dropdown-item">Product List</a>
                                    </li>
                                    <li>
                                        <a href="addproduct.html" class="dropdown-item">Product Add</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive dataview">
                                <table class="table datatable ">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Sản Phẩm</th>
                                            <th>Lượt Bán</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="category" items="${categoriesName}" varStatus="status">
                                            <c:if test="${status.index < 5}">
                                                <tr>
                                                    <td>${status.index + 1}</td>
                                                    <td class="productimgname">
                                                        <a href="product-controller?action=displayDetailFurniture&amp;id=${listFirstFurniture[status.index].id}" class="product-img">
                                                            <img src="data:image/png;base64, ${imageLists[status.index]}" alt="product">
                                                        </a>
                                                        <a href="product-controller?action=displayDetailFurniture&amp;id=${listFirstFurniture[status.index].id}">${category}</a>
                                                    </td>
                                                    <td>${categorySales[status.index]}</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card mb-0">
                <div class="card-body">
                    <h4 class="card-title">Sản Phẩm Bán Ra</h4>
                    <div class="table-responsive">
                        <table class="table datanew">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Tên Sản Phẩm</th>
                                <th>Lượt bán ra</th>
                                <th>Tổng doanh thu</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="category" items="${categoriesName}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td class="productimgname">
                                            <a class="product-img" href="product-controller?action=displayDetailFurniture&amp;id=${listFirstFurniture[status.index].id}">
                                                <img src="data:image/png;base64, ${imageLists[status.index]}" alt="product">
                                            </a>
                                            <a href="product-controller?action=displayDetailFurniture&amp;id=${listFirstFurniture[status.index].id}">${category}</a>
                                        </td>
                                        <td>${categorySales[status.index]}</td>
                                        <td>${categoryRevenues[status.index]}</td>
                                    </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<c:import url="footer.jsp"/>
