package org.example.ecommercewebsite.controller;

import org.example.ecommercewebsite.DAO.IStatisticDAO;
import org.example.ecommercewebsite.DAO.Impl.StatisticDAOImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.example.ecommercewebsite.business.Category;
import org.example.ecommercewebsite.business.Furniture;
import com.google.gson.Gson;

import org.example.ecommercewebsite.business.Payment;
import org.example.ecommercewebsite.business.Order;


@WebServlet(name="statistics", urlPatterns = "/statistics")
public class StatisticController extends HttpServlet {
    IStatisticDAO statisticDAO = new StatisticDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String page = request.getParameter("page");
        List<Integer> availableYears = statisticDAO.getOrderYears();
        if ("index2".equals(page)) {
            productStatistic(request,response,availableYears);
            request.setAttribute("availableYears", availableYears);
            request.getRequestDispatcher("/Admin/index2.jsp").forward(request, response);
        } else if ("index3".equals(page)) {
            statusStatistic(request,response,availableYears);
            request.setAttribute("availableYears", availableYears);
            request.getRequestDispatcher("/Admin/index3.jsp").forward(request, response);
        } else {
            revenueStatistic(request,response,availableYears);
            request.setAttribute("availableYears", availableYears);
            request.getRequestDispatcher("/Admin/index1.jsp").forward(request, response);
        }
    }

    protected void revenueStatistic(HttpServletRequest request, HttpServletResponse response, List<Integer> availableYears ) {
        String yearParam = request.getParameter("year");

        if ("all".equals(yearParam)) {
            List<List<Double>> allRevenueAndSales = statisticDAO.getRevenueAndSalesDataForAllYears();
            List<Double> revenueListAll = allRevenueAndSales.get(0);
            List<Double> salesListAll = allRevenueAndSales.get(1);
            List<Payment> payments = statisticDAO.getAllPayments();
            double totalSales = 0.0;
            for (Double value : salesListAll) {
                totalSales += value;
            }
            double totalRevenue = 0.0;
            for (Double value : revenueListAll) {
                totalRevenue += value;
            }
            request.setAttribute("year", "Tất Cả");
            request.setAttribute("time", availableYears);
            request.setAttribute("payments", payments);
            request.setAttribute("revenueListNow", revenueListAll);
            request.setAttribute("salesListNow", salesListAll);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("totalSales", totalSales);
        }
        else {
            int year = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : java.time.Year.now().getValue();

            List<List<Double>> data = statisticDAO.getRevenueAndSalesData(year);
            List<Double> revenueListNow = data.get(0);
            List<Double> salesListNow = data.get(1);

            List<Double> totalData = statisticDAO.getTotalRevenueAndSales(year);
            Double totalRevenue = totalData.get(0);
            Double totalSales = totalData.get(1);

            List<Payment> payments = statisticDAO.getPayments(year);
            List<Integer> month = List.of(1,2,3,4,5,6,7,8,9,10,11,12);

            System.out.println(payments);


            request.setAttribute("payments", payments);
            request.setAttribute("year", year);
            request.setAttribute("revenueListNow", revenueListNow);
            request.setAttribute("salesListNow", salesListNow);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("time", month);
            request.setAttribute("totalSales", totalSales);
        }

    }

    protected void productStatistic(HttpServletRequest request, HttpServletResponse response, List<Integer> availableYears ) {
        String yearParam = request.getParameter("year");
        if ("all".equals(yearParam)) {

            List<List<Object>> data = statisticDAO.getAllRevenueAndSalesByCategory();
            List<String> categoriesName =(List<String>) data.get(0).get(0); // Lấy danh sách danh mục
            List<Long> sales = (List<Long>) data.get(1).get(0); // Lấy số lượng bán hàng
            List<Long> revenues = (List<Long>) data.get(2).get(0); // Lấy doanh thu

            Gson gson = new Gson();
            String jsonListName = gson.toJson(categoriesName);

            String topCategoriesName="";
            if (!categoriesName.isEmpty()) {topCategoriesName = categoriesName.get(0);}

            Long totalSale = statisticDAO.getTotalSalesByCategory();

            List<String> imageLists = Category.getListImage(categoriesName);
            List<Furniture> listFirstFurniture = Category.getListFirstFurniture(categoriesName);

            request.setAttribute("categorySales", sales);
            request.setAttribute("topCategoriesName", topCategoriesName);
            request.setAttribute("jsonCategoriesName", jsonListName);
            request.setAttribute("categoriesName", categoriesName);
            request.setAttribute("categoryRevenues", revenues);
            request.setAttribute("totalSale", totalSale);
            request.setAttribute("imageLists", imageLists);
            request.setAttribute("listFirstFurniture", listFirstFurniture);
            request.setAttribute("year", "Tất Cả");
        }
        else
        {
            int year = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : java.time.Year.now().getValue();

            List<List<Object>> data = statisticDAO.getRevenueAndSalesByCategoryByYear(year);
            List<String> categoriesName =(List<String>) data.get(0).get(0);
            List<Long> sales = (List<Long>) data.get(1).get(0);
            List<Long> revenues = (List<Long>) data.get(2).get(0);

            Gson gson = new Gson();
            String jsonListName = gson.toJson(categoriesName);
            String topCategoriesName="";
            if (!categoriesName.isEmpty()) {topCategoriesName = categoriesName.get(0);}

            Long totalSale = statisticDAO.getTotalSalesByCategoryByYear(year);

            List<String> imageLists = Category.getListImage(categoriesName);

            List<Furniture> listFirstFurniture = Category.getListFirstFurniture(categoriesName);

            request.setAttribute("categorySales", sales);
            request.setAttribute("topCategoriesName", topCategoriesName);
            request.setAttribute("jsonCategoriesName", jsonListName);
            request.setAttribute("categoriesName", categoriesName);
            request.setAttribute("categoryRevenues", revenues);
            request.setAttribute("totalSale", totalSale);
            request.setAttribute("imageLists", imageLists);
            request.setAttribute("listFirstFurniture", listFirstFurniture);
            request.setAttribute("year", year);

        }
    }

    protected void statusStatistic(HttpServletRequest request, HttpServletResponse response, List<Integer> availableYears ){
        String yearParam = request.getParameter("year");
        if ("all".equals(yearParam)) {
            List<List<Integer>> deliveredAndCanceledData = statisticDAO.getDeliveredAndCanceledOrdersForAllYears();

            List<Integer> deliveredList = deliveredAndCanceledData.get(0);
            int totalDelivered = deliveredList.stream().mapToInt(Integer::intValue).sum();
            List<Integer> canceledList = deliveredAndCanceledData.get(1);
            int totalCanceled = -canceledList.stream().mapToInt(Integer::intValue).sum();

            List<Order> orders = statisticDAO.getDeliveredOrCanceledOrders();
            long totalOrders = statisticDAO.getTotalOrders();

            request.setAttribute("year", "Tất Cả");
            request.setAttribute("time",availableYears);
            request.setAttribute("orders",orders);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("deliveredList",deliveredList);
            request.setAttribute("canceledList", canceledList);
            request.setAttribute("totalDelivered", totalDelivered);
            request.setAttribute("totalCanceled", totalCanceled);
        }
        else {
            int year = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : java.time.Year.now().getValue();

            List<List<Integer>> deliveredAndCanceledData = statisticDAO.getDeliveredAndCanceledData(year);

            // Số liệu đã giao và đã hủy theo tháng
            List<Integer> deliveredList = deliveredAndCanceledData.get(0);
            int totalDelivered = deliveredList.stream().mapToInt(Integer::intValue).sum();
            List<Integer> canceledList = deliveredAndCanceledData.get(1);
            int totalCanceled = -canceledList.stream().mapToInt(Integer::intValue).sum();

            List<Integer> month = List.of(1,2,3,4,5,6,7,8,9,10,11,12);
            List<Order> orders = statisticDAO.getCompletedAndCanceledOrders(year);
            long totalOrders = statisticDAO.getTotalOrdersByYear(year);


            request.setAttribute("year", year);
            request.setAttribute("time",month);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("orders",orders);
            request.setAttribute("deliveredList", deliveredList);
            request.setAttribute("canceledList", canceledList);
            request.setAttribute("totalDelivered", totalDelivered);
            request.setAttribute("totalCanceled", totalCanceled);
        }
    }
}

