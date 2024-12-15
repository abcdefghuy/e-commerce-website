package org.example.ecommercewebsite.controller;

import org.example.ecommercewebsite.DAO.ICategoryDAO;
import org.example.ecommercewebsite.DAO.ICouponDAO;
import org.example.ecommercewebsite.DAO.Impl.CategoryDAOImpl;
import org.example.ecommercewebsite.business.Coupon;
import org.example.ecommercewebsite.DAO.Impl.CouponDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.util.List;

import org.example.ecommercewebsite.business.Category;

@WebServlet(name = "CouponController", urlPatterns = "/CouponController")
public class CouponController extends HttpServlet {

    private final ICouponDAO couponDAO = new CouponDAOImpl();
    private final ICategoryDAO categoryDAO = new CategoryDAOImpl();
    String couponIdParam;
    String action;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        couponIdParam = request.getParameter("id");

        action = request.getParameter("action");

        setCategoryAndCoupon(request);

        if (couponIdParam != null) {
            handleCouponActions(request, response);
            return;
        }

        forwardToPage("/Admin/coupon.jsp", request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getParameter("action");
        if (action.equals("delete")) {
            couponIdParam = request.getParameter("id");
            couponDAO.delete(couponIdParam);
            response.sendRedirect("CouponController");
        }
        else if ("add".equals(action) || "edit".equals(action)) {
            handleAddOrEditCoupon(action, request, response);
        }
        else {
            response.sendRedirect("CouponController");
        }

    }

    private void forwardToPage(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    private void handleCouponActions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if ("edit".equals(action)) {
            Coupon coupon = couponDAO.getCouponById(couponIdParam);
            if (coupon != null) {
                if (coupon.getUseCondition().equals("product")) {
                    List<Category> selectedCategories = couponDAO.getCategoriesByCoupon(couponIdParam);

                    request.setAttribute("selectedCategories", selectedCategories);
                }
                request.setAttribute("coupon", coupon);
            }
            forwardToPage("/Admin/editCoupon.jsp", request, response);
        }
    }

    private void setCategoryAndCoupon(HttpServletRequest request) {
        List<Category> categories = categoryDAO.getCategoryList();
        request.getSession().setAttribute("categoryList", categories);
        request.setAttribute("categoryListEdit", categories);

        List<Coupon> couponList = couponDAO.getAllCoupons();
        request.setAttribute("couponList", couponList);
    }

    private void handleAddOrEditCoupon(String action, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> errors = new ArrayList<>();
        String couponName = request.getParameter("couponName");
        String couponType = request.getParameter("couponType");
        String couponValueStr = request.getParameter("couponValue");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String useLimitStr = request.getParameter("useLimit");
        String useCondition = request.getParameter("useCondition");


        double couponValue = parseDouble(couponValueStr, "Giá trị giảm giá phải là số hợp lệ.", errors);
        validateCouponValue(couponType, couponValue, errors);

        int useLimit = parseInt(useLimitStr, "Số lần áp dụng không hợp lệ.", errors);
        if (useLimit < 0) errors.add("Số lần áp dụng phải lớn hơn 0");

        Date startDate = parseDate(startDateStr, "Ngày bắt đầu không hợp lệ.", errors);
        Date endDate = parseDate(endDateStr, "Ngày kết thúc không hợp lệ.", errors);
        validateDateRange(startDate, endDate, errors);

        String minOrderValueStr = "";
        double minOrderValue = 0;
        List<Category> selectedCategories = new ArrayList<>();
        if ("min".equals(useCondition)) {
            minOrderValueStr = request.getParameter("minOrderValue");
            minOrderValue = parseDouble(minOrderValueStr, "Số tiền tối thiểu không hợp lệ.", errors);
            if (minOrderValue <= 0) errors.add("Giá trị tối thiểu phải lớn hơn 0");
        } else if ("product".equals(useCondition)) {
            selectedCategories = getSelectedCategories(request.getParameterValues("categoryIds"));
        }

        if (action.equals("add")) {
            if (couponDAO.existedCoupon(couponName)) errors.add("Mã khuyến mãi đã tồn tại.");
        }

        if (action.equals("edit"))
        {
            Coupon couponToEdit = couponDAO.getCouponById(couponIdParam);
            if (couponToEdit != null && !couponName.equals(couponToEdit.getCouponName()) && couponDAO.existedCoupon(couponName)) {
                errors.add("Mã khuyến mãi đã tồn tại.");
            }
        }
        if (!errors.isEmpty()) {
            saveFormStateToSession(request, couponName, couponType, couponValueStr, startDateStr, endDateStr, useLimitStr, useCondition, minOrderValueStr, selectedCategories, errors);
            redirectToEditOrAddPage(request, response);
            return;
        }

        Coupon coupon = new Coupon(couponName, couponType, couponValue, startDate, endDate, useCondition, minOrderValue, selectedCategories, useLimit, 0);
        if ("add".equals(action)) {
            couponDAO.insert(coupon);
            request.getSession().setAttribute("successMessageAdd", "Thêm mã giảm giá thành công.");

        } else if ("edit".equals(action)) {
            Coupon couponToEdit = couponDAO.getCouponById(couponIdParam);
            if (couponToEdit != null) {
                couponToEdit.setCouponName(couponName);
                couponToEdit.setCouponType(couponType);
                couponToEdit.setCouponValue(couponValue);
                couponToEdit.setStartDate(startDate);
                couponToEdit.setEndDate(endDate);
                couponToEdit.setUseCondition(useCondition);
                couponToEdit.setMinOrderValue(minOrderValue);
                couponToEdit.setApplicableFurniture(selectedCategories);
                couponToEdit.setUseLimit(useLimit);
                couponDAO.update(couponToEdit);

                // Lấy danh sách danh mục và các thông tin sửa đổi để hiển thị lại trên trang
                List<Category> categoryList = categoryDAO.getCategoryList();
                request.setAttribute("categoryList", categoryList);
                request.setAttribute("coupon", couponToEdit);

                if ("product".equals(couponToEdit.getUseCondition())) {
                    List<Category> updatedSelectedCategories = couponDAO.getCategoriesByCoupon(couponIdParam);
                    request.setAttribute("selectedCategories", updatedSelectedCategories);
                }

                request.setAttribute("successMessage", "Cập nhật thông tin thành công.");

                RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin/editCoupon.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
        response.sendRedirect("CouponController");
    }

    private double parseDouble(String value, String errorMessage, List<String> errors) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            errors.add(errorMessage);
            return 0;
        }
    }

    private int parseInt(String value, String errorMessage, List<String> errors) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            errors.add(errorMessage);
            return 0;
        }
    }

    private Date parseDate(String value, String errorMessage, List<String> errors) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(value);
        } catch (ParseException e) {
            errors.add(errorMessage);
            return null;
        }
    }

    private void validateDateRange(Date startDate, Date endDate, List<String> errors) {
        if (startDate != null && endDate != null && !startDate.before(endDate)) {
            errors.add("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
        }
    }

    private void redirectToEditOrAddPage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (couponIdParam != null) {
            response.sendRedirect("CouponController?id=" + couponIdParam + "&action=edit");
        } else {
            response.sendRedirect("CouponController");
        }
    }

    private List<Category> getSelectedCategories(String[] categoryIds) {
        List<Category> selectedCategories = new ArrayList<>();
        if (categoryIds != null) {
            for (String categoryId : categoryIds) {
                Category category = categoryDAO.getCategoryByID(Long.parseLong(categoryId));
                if (category != null) {
                    selectedCategories.add(category);
                }
            }
        }
        return selectedCategories;
    }

    private void validateCouponValue(String couponType, double couponValue, List<String> errors) {
        if ("percent".equalsIgnoreCase(couponType) && (couponValue <= 0 || couponValue > 100)) {
            errors.add("Giá trị phần trăm phải lớn hơn 0 và không vượt quá 100.");
        } else if ("money".equalsIgnoreCase(couponType) && couponValue <= 0) {
            errors.add("Giá trị tiền phải lớn hơn 0.");
        }
    }

    private void saveFormStateToSession(HttpServletRequest request, String couponName, String couponType, String couponValueStr,
                                        String startDateStr, String endDateStr, String useLimitStr, String useCondition,
                                        String minOrderValueStr, List<Category> selectedCategories, List<String> errors) {
        request.getSession().setAttribute("errors", errors);
        request.getSession().setAttribute("couponName", couponName);
        request.getSession().setAttribute("couponType", couponType);
        request.getSession().setAttribute("couponValue", couponValueStr);
        request.getSession().setAttribute("startDate", startDateStr);
        request.getSession().setAttribute("endDate", endDateStr);
        request.getSession().setAttribute("useLimit", useLimitStr);
        request.getSession().setAttribute("useCondition", useCondition);
        if ("min".equals(useCondition)) {
            request.getSession().setAttribute("minOrderValue", minOrderValueStr);
        }
        else if ("product".equals(useCondition)) {
            request.getSession().setAttribute("selectedCategories", selectedCategories);
        }

    }
}
