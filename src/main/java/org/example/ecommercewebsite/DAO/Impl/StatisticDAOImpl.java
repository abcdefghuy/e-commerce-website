package org.example.ecommercewebsite.DAO.Impl;
import org.example.ecommercewebsite.DAO.IStatisticDAO;
import org.example.ecommercewebsite.ENumeration.EOrderStatus;
import org.example.ecommercewebsite.business.*;
import org.example.ecommercewebsite.data.DBUtil;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class StatisticDAOImpl implements IStatisticDAO {
    // Phương thức lấy số đơn bán ra trong tháng
    @Override
    public  List<List<Double>> getRevenueAndSalesData(int year) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            String jpql = "SELECT FUNCTION('MONTH', COALESCE(p.paymentDate, p.order.orderDate)), " +
                    "SUM(p.money), COUNT(p.paymentID) " +
                    "FROM Payment p " +
                    "WHERE FUNCTION('YEAR', COALESCE(p.paymentDate, p.order.orderDate)) = :year " +
                    "AND (p.order.status = :accepted or p.order.status = :feedbacked)" +
                    "GROUP BY FUNCTION('MONTH', COALESCE(p.paymentDate, p.order.orderDate)) " +
                    "ORDER BY FUNCTION('MONTH', COALESCE(p.paymentDate, p.order.orderDate))";

            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setParameter("year", year);
            query.setParameter("accepted", EOrderStatus.ACCEPTED);
            query.setParameter("feedbacked", EOrderStatus.FEEDBACKED);

            List<Object[]> results = query.getResultList();

            List<Double> revenueList = new ArrayList<>();
            List<Double> salesList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                revenueList.add(0.0);
                salesList.add(0.0);
            }

            for (Object[] result : results) {
                int month = (int) result[0];
                double totalRevenue = (result[1] != null) ? (double) result[1] : 0.0;
                long orderCount = (result[2] != null) ? (long) result[2] : 0;

                revenueList.set(month - 1, totalRevenue);
                salesList.set(month - 1, (double) orderCount);
            }

            List<List<Double>> data = new ArrayList<>();
            data.add(revenueList);
            data.add(salesList);

            return data;
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  List<List<Double>> getAllRevenueAndSalesData(int year) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            String jpql = "SELECT FUNCTION('MONTH', p.paymentDate), SUM(p.money), COUNT(p.paymentID) " +
                    "FROM Payment p " +
                    "WHERE FUNCTION('YEAR', p.paymentDate) = :year " +
                    "GROUP BY FUNCTION('MONTH', p.paymentDate) " +
                    "ORDER BY FUNCTION('MONTH', p.paymentDate)";

            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setParameter("year", year);

            List<Object[]> results = query.getResultList();

            List<Double> revenueList = new ArrayList<>();
            List<Double> salesList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                revenueList.add(0.0);
                salesList.add(0.0);
            }

            // Tổng doanh thu và tổng số đơn bán
            double totalRevenue = 0.0;
            int totalSales = 0;

            for (Object[] result : results) {
                int month = (int) result[0];
                double totalRevenueMonth = (result[1] != null) ? (double) result[1] : 0.0;
                long orderCount = (result[2] != null) ? (long) result[2] : 0;

                revenueList.set(month - 1, totalRevenueMonth);
                salesList.set(month - 1, (double) orderCount);

                // Cộng dồn doanh thu và số lượng đơn bán
                totalRevenue += totalRevenueMonth;
                totalSales += orderCount;
            }

            // Tạo một danh sách để chứa doanh thu và số lượng đơn bán theo tháng
            List<List<Double>> data = new ArrayList<>();
            data.add(revenueList);  // Doanh thu theo tháng
            data.add(salesList);    // Số lượng đơn bán theo tháng

            // Thêm tổng doanh thu và số lượng đơn bán vào danh sách
            // Bạn có thể sử dụng chúng trong Controller hoặc JSP
            data.add(List.of(totalRevenue));  // Tổng doanh thu
            data.add(List.of((double) totalSales)); // Tổng số lượng đơn bán

            return data;
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  List<Double> getTotalRevenueAndSales(int year) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL để tính tổng doanh thu và số lượng đơn bán cho một năm
            String jpql = "SELECT SUM(p.money), COUNT(p.paymentID) " +
                    "FROM Payment p " +
                    "WHERE FUNCTION('YEAR', COALESCE(p.paymentDate, p.order.orderDate)) = :year "+
                    "AND (p.order.status = :accepted or p.order.status = :feedbacked)" ;

            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setParameter("year", year);
            query.setParameter("accepted", EOrderStatus.ACCEPTED);
            query.setParameter("feedbacked", EOrderStatus.FEEDBACKED);
            List<Object[]> results = query.getResultList();

            double totalRevenue = 0.0;
            long orderCount = 0;

            if (!results.isEmpty()) {
                Object[] result = results.get(0);
                totalRevenue = (result[0] != null) ? (double) result[0] : 0.0;
                orderCount = (result[1] != null) ? (long) result[1] : 0;
            }

            List<Double> data = new ArrayList<>();
            data.add(totalRevenue);
            data.add((double) orderCount);

            return data;
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  List<List<Double>> getRevenueAndSalesDataForAllYears() {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để lấy tổng doanh thu và số lượng đơn theo năm
            String jpql = "SELECT FUNCTION('YEAR', COALESCE(p.paymentDate, p.order.orderDate)), SUM(p.money), COUNT(p.paymentID) " +
                    "FROM Payment p " +
                    "WHERE (p.order.status = :accepted or p.order.status = :feedbacked) "+
                    "GROUP BY FUNCTION('YEAR', COALESCE(p.paymentDate, p.order.orderDate)) " +
                    "ORDER BY FUNCTION('YEAR', COALESCE(p.paymentDate, p.order.orderDate))";
            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setParameter("accepted", EOrderStatus.ACCEPTED);
            query.setParameter("feedbacked", EOrderStatus.FEEDBACKED);
            List<Object[]> results = query.getResultList();

            List<Double> revenueListAll = new ArrayList<>();
            List<Double> salesListAll = new ArrayList<>();
            for (Object[] result : results) {
                int year = (int) result[0];
                double totalRevenue = (result[1] != null) ? (double) result[1] : 0.0;
                long orderCount = (result[2] != null) ? (long) result[2] : 0;

                // Cộng dồn doanh thu và số lượng đơn bán cho tất cả các năm
                revenueListAll.add(totalRevenue);
                salesListAll.add((double) orderCount);
            }

            List<List<Double>> data = new ArrayList<>();
            data.add(revenueListAll);
            data.add(salesListAll);

            return data;
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  List<Payment> getPayments(int year) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để lấy các payment theo năm
            String jpql = "SELECT p FROM Payment p WHERE FUNCTION('YEAR', COALESCE(p.paymentDate, p.order.orderDate)) = :year AND (p.order.status = :accepted or p.order.status = :feedbacked) ORDER BY COALESCE(p.paymentDate, p.order.orderDate) DESC";
            TypedQuery<Payment> query = entityManager.createQuery(jpql, Payment.class);
            query.setParameter("accepted", EOrderStatus.ACCEPTED);
            query.setParameter("feedbacked", EOrderStatus.FEEDBACKED);
            query.setParameter("year", year);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  List<Payment> getAllPayments() {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để lấy tất cả các payment
            String jpql = "SELECT p FROM Payment p WHERE (p.order.status = :accepted or p.order.status = :feedbacked) ORDER BY COALESCE(p.paymentDate, p.order.orderDate) DESC";
            TypedQuery<Payment> query = entityManager.createQuery(jpql, Payment.class);
            query.setParameter("accepted", EOrderStatus.ACCEPTED);
            query.setParameter("feedbacked", EOrderStatus.FEEDBACKED);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    // Phương thức lấy số đơn đã giao và đã hủy trong từng tháng của năm
    @Override
    public  List<List<Integer>> getDeliveredAndCanceledData(int year) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để lấy số lượng đơn đã giao và đã hủy theo tháng trong năm
            String jpql = "SELECT FUNCTION('MONTH', o.orderDate), " +
                    "SUM(CASE WHEN o.status = :accepted or o.status = :feedbacked THEN 1 ELSE 0 END), " +
                    "SUM(CASE WHEN o.status = :canceled  THEN 1 ELSE 0 END) " +
                    "FROM Order o " +
                    "WHERE FUNCTION('YEAR', o.orderDate) = :year " +
                    "GROUP BY FUNCTION('MONTH', o.orderDate) " +
                    "ORDER BY FUNCTION('MONTH', o.orderDate)";

            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setParameter("year", year);
            query.setParameter("accepted", EOrderStatus.ACCEPTED);
            query.setParameter("feedbacked", EOrderStatus.FEEDBACKED);// So sánh với enum
            query.setParameter("canceled", EOrderStatus.CANCELED);  // So sánh với enum

            List<Object[]> results = query.getResultList();

            // Khởi tạo danh sách số lượng đơn đã giao và đã hủy cho 12 tháng
            List<Integer> deliveredList = new ArrayList<>();
            List<Integer> canceledList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                deliveredList.add(0);
                canceledList.add(0);
            }

            // Điền dữ liệu vào các danh sách đã giao và đã hủy
            for (Object[] result : results) {
                int month = (int) result[0];
                Long deliveredCount = (Long) result[1];
                Long canceledCount = (Long) result[2];

                deliveredList.set(month - 1, deliveredCount.intValue());
                canceledList.set(month - 1, -canceledCount.intValue());
            }

            // Tạo một danh sách chứa 2 danh sách (đã giao, đã hủy)
            List<List<Integer>> data = new ArrayList<>();
            data.add(deliveredList);
            data.add(canceledList);

            return data;

        } finally {
            entityManager.close();
        }
    }
    // Hàm tính số lượng đơn hàng đã giao và đã hủy theo các năm
    @Override
    public  List<List<Integer>> getDeliveredAndCanceledOrdersForAllYears() {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để lấy số lượng đơn đã giao và đã hủy cho mỗi năm
            String jpql = "SELECT FUNCTION('YEAR', o.orderDate), " +
                    "SUM(CASE WHEN o.status = :accepted or o.status = :feedbacked THEN 1 ELSE 0 END), " +
                    "SUM(CASE WHEN o.status = :canceled THEN 1 ELSE 0 END) " +
                    "FROM Order o " +
                    "GROUP BY FUNCTION('YEAR', o.orderDate) " +
                    "ORDER BY FUNCTION('YEAR', o.orderDate)";

            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setParameter("accepted", EOrderStatus.ACCEPTED);
            query.setParameter("feedbacked", EOrderStatus.FEEDBACKED);
            query.setParameter("canceled", EOrderStatus.CANCELED);
            List<Object[]> result = query.getResultList();

            // Khởi tạo danh sách các năm, đơn đã giao và đơn đã hủy
            List<Integer> years = new ArrayList<>();
            List<Integer> deliveredList = new ArrayList<>();
            List<Integer> canceledList = new ArrayList<>();

            // Duyệt qua kết quả và phân tách thành các danh sách
            for (Object[] row : result) {
                int year = (Integer) row[0];
                int deliveredCount = ((Long) row[1]).intValue();
                int canceledCount = ((Long) row[2]).intValue();

                years.add(year);
                deliveredList.add(deliveredCount);
                canceledList.add(canceledCount*(-1));
            }

            List<List<Integer>> finalResult = new ArrayList<>();
            finalResult.add(deliveredList);
            finalResult.add(canceledList);

            return finalResult;

        } finally {
            entityManager.close();
        }
    }
    @Override
    public  long getTotalOrdersByYear(int year) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để đếm số lượng đơn hàng trong một năm
            String jpql = "SELECT COUNT(o) FROM Order o " +
                    "WHERE FUNCTION('YEAR', o.orderDate) = :year";

            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("year", year);

            // Trả về số lượng đơn hàng trong năm
            return query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  long getTotalOrders() {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để đếm số lượng đơn hàng trong một năm
            String jpql = "SELECT COUNT(o) FROM Order o ";

            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);

            return query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  List<Order> getCompletedAndCanceledOrders(int year) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để lấy các đơn hàng đã hoàn thành và đã hủy trong năm
            String jpql = "SELECT o FROM Order o " +
                    "WHERE FUNCTION('YEAR', o.orderDate) = :year " +
                    "AND (o.status = :accepted OR o.status = :feedbacked OR o.status = :canceled) " +
                    "ORDER BY o.orderDate DESC"; // Sắp xếp theo ngày giảm dần

            TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
            query.setParameter("year", year);
            query.setParameter("accepted", EOrderStatus.ACCEPTED);
            query.setParameter("feedbacked", EOrderStatus.FEEDBACKED);// Trạng thái đã hoàn thành
            query.setParameter("canceled", EOrderStatus.CANCELED);  // Trạng thái đã hủy

            // Lấy kết quả
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  List<Order> getDeliveredOrCanceledOrders() {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để lấy các đơn hàng đã giao hoặc đã hủy trong năm
            String jpql = "SELECT o FROM Order o " +
                    "WHERE (o.status = :accepted OR o.status = :feedbacked OR o.status = :canceled) " +
                    "ORDER BY o.orderDate DESC";

            TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
            query.setParameter("accepted", EOrderStatus.ACCEPTED);
            query.setParameter("feedbacked", EOrderStatus.FEEDBACKED);// Trạng thái đã giao
            query.setParameter("canceled", EOrderStatus.CANCELED);  // Trạng thái đã hủy

            // Lấy kết quả
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    //Lấy doanh thu và số lượng bán ra của sản phẩm
    @Override
    public  List<List<Object>> getRevenueAndSalesByCategoryByYear(int year) {
        EntityManager entityManager = DBUtil.getEntityManager();

        try {
            // JPQL query to calculate revenue and sales quantity for each category in the specified year
            String jpql = "SELECT oi.category.categoryName, " +
                    "       COUNT(oi), " +
                    "       SUM(oi.furniturePrice) " + // Calculate total revenue for the category
                    "FROM Order o " +
                    "JOIN o.listFurniture oi " +
                    "WHERE FUNCTION('YEAR', o.orderDate) = :year " +
                    "GROUP BY oi.category " + // Group by category
                    "ORDER BY COUNT(oi) DESC"; // Sort by sales quantity in descending order

            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
            query.setParameter("year", year);

            List<Object[]> results = query.getResultList();

            // Initialize lists for categories, quantities, and revenues
            List<String> categories = new ArrayList<>();
            List<Long> revenueList = new ArrayList<>();
            List<Long> quantityList = new ArrayList<>();

            // Process query results
            for (Object[] result : results) {
                String category = (String) result[0]; // Category name
                Long totalQuantity = (Long) result[1]; // Total sales quantity
                Long totalRevenue = (Long) result[2]; // Total revenue

                categories.add(category);
                quantityList.add(totalQuantity);
                revenueList.add(totalRevenue);
            }

            // Combine results into a single list of lists
            List<List<Object>> statistics = new ArrayList<>();
            statistics.add(Collections.singletonList(categories)); // List of categories
            statistics.add(Collections.singletonList(quantityList)); // Sales quantities
            statistics.add(Collections.singletonList(revenueList)); // Revenues

            return statistics;

        } finally {
            entityManager.close();
        }
    }
    @Override
    public  long getTotalSalesByCategoryByYear(int year) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {

            String jpql = "SELECT COUNT(oi) " +
                    "FROM Order o " +
                    "JOIN o.listFurniture oi " +
                    "WHERE FUNCTION('YEAR', o.orderDate) = :year";

            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("year", year);

            // Trả về tổng số lượt bán của tất cả các category trong năm
            return query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  List<List<Object>> getAllRevenueAndSalesByCategory() {
        EntityManager entityManager = DBUtil.getEntityManager();

        try {
            // JPQL query to calculate revenue and sales quantity for each category in the specified year
            String jpql = "SELECT oi.category.categoryName, " +
                    "       COUNT(oi), " +
                    "       SUM(oi.furniturePrice) " + // Calculate total revenue for the category
                    "FROM Order o " +
                    "JOIN o.listFurniture oi " +
                    "GROUP BY oi.category " + // Group by category
                    "ORDER BY COUNT(oi) DESC,SUM(oi.furniturePrice) DESC "; // Sort by sales quantity in descending order

            TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);

            List<Object[]> results = query.getResultList();

            // Initialize lists for categories, quantities, and revenues
            List<String> categories = new ArrayList<>();
            List<Long> revenueList = new ArrayList<>();
            List<Long> quantityList = new ArrayList<>();

            // Process query results
            for (Object[] result : results) {
                String category = (String) result[0]; // Category name
                Long totalQuantity = (Long) result[1]; // Total sales quantity
                Long totalRevenue = (Long) result[2]; // Total revenue

                categories.add(category);
                quantityList.add(totalQuantity);
                revenueList.add(totalRevenue);
            }

            // Combine results into a single list of lists
            List<List<Object>> statistics = new ArrayList<>();
            statistics.add(Collections.singletonList(categories)); // List of categories
            statistics.add(Collections.singletonList(quantityList)); // Sales quantities
            statistics.add(Collections.singletonList(revenueList)); // Revenues

            return statistics;

        } finally {
            entityManager.close();
        }
    }
    @Override
    public  long getTotalSalesByCategory() {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {

            String jpql = "SELECT COUNT(oi) " +
                    "FROM Order o " +
                    "JOIN o.listFurniture oi ";

            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);

            // Trả về tổng số lượt bán của tất cả các category trong năm
            return query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  List<Integer> getOrderYears() {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL query để lấy các năm của các đơn hàng
            String jpql = "SELECT DISTINCT FUNCTION('YEAR', o.orderDate) " +
                    "FROM Order o " +
                    "ORDER BY FUNCTION('YEAR', o.orderDate)";

            TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);

            // Trả về danh sách các năm
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public  Category findCategoryByName(String categoryName) {
        // Sử dụng EntityManager để tìm danh mục từ cơ sở dữ liệu
        EntityManager em = DBUtil.getEntityManager();
        TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE c.categoryName = :name", Category.class);
        query.setParameter("name", categoryName);
        List<Category> categories = query.getResultList();
        return categories.isEmpty() ? null : categories.get(0);
    }
}
