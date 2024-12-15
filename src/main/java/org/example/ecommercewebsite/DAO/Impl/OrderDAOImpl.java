package org.example.ecommercewebsite.DAO.Impl;
import org.example.ecommercewebsite.DAO.IOrderDAO;
import org.example.ecommercewebsite.DTO.request.OrderRequest;
import org.example.ecommercewebsite.ENumeration.EOrderStatus;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Furniture;
import org.example.ecommercewebsite.business.Order;
import org.example.ecommercewebsite.util.DBUtil;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements IOrderDAO {
    private EntityManagerFactory emf;

    public OrderDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("dataProject"); // Tạo EntityManagerFactory
    }
    @Override
    public List<Order> getOrder(OrderRequest orderRequest) {
        EntityManager em = emf.createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT o FROM Order o WHERE o.customer.personID = :customerId");

            if (orderRequest.getId() != null) {
                jpql.append(" AND o.id = :orderId");
            }
            if (orderRequest.getOrderDate() != null) {
                jpql.append(" AND o.orderDate = :orderDate");
            }
            if (orderRequest.getStatus() != null && !orderRequest.getStatus().isEmpty()) {
                jpql.append(" AND o.status = :status");
            }

            // Tạo truy vấn
            TypedQuery<Order> query = em.createQuery(jpql.toString(), Order.class);
            query.setParameter("customerId", orderRequest.getCustomerId());

            if (orderRequest.getId() != null) {
                query.setParameter("orderId", orderRequest.getId());
            }
            if (orderRequest.getOrderDate() != null) {
                query.setParameter("orderDate", orderRequest.getOrderDate());
            }

            // Chuyển đổi String thành EOrderStatus
            if (orderRequest.getStatus() != null && !orderRequest.getStatus().isEmpty()) {
                EOrderStatus statusEnum = EOrderStatus.valueOf(orderRequest.getStatus());
                query.setParameter("status", statusEnum);
            }


            return query.getResultList();
        } finally {
            em.close();
        }
    }
    @Override
    public List<Order> getAllOrders() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT o FROM Order o";
        return em.createQuery(query, Order.class).getResultList();
    }
    @Override
    public void updateOrderStatus(Long orderId, String newStatus) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            Order order = em.find(Order.class, orderId);
            if (order != null) {
                order.setStatus(EOrderStatus.valueOf(newStatus));
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    @Override
    public List<Order> filterOrdersByStatus(String status) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            // JPQL lọc theo trạng thái
            String jpql = "SELECT o FROM Order o WHERE o.status = :status";
            TypedQuery<Order> query = em.createQuery(jpql, Order.class);
            query.setParameter("status", EOrderStatus.valueOf(status));
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean insertOrder(Order order) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            // Lưu trữ đơn hàng vào cơ sở dữ liệu
            em.persist(order);
            // Cập nhật order_id cho các đối tượng Furniture hiện có
            for (Furniture furniture : order.getListFurniture()) {
                // Nếu furniture đã tồn tại trong DB, chỉ cập nhật order_id mà không tạo mới
                if (furniture.getId() != null) {
                    furniture.setOrder(order); // Cập nhật lại order_id của furniture
                    em.merge(furniture); // Merge để cập nhật furniture vào DB
                }
            }
            trans.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Loi: " + e);
            if (trans.isActive()) {
                trans.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateOrderStatus(Long orderId, EOrderStatus orderStatus) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "UPDATE Order o SET o.status = :status WHERE o.id = :orderId";

        // Bắt đầu giao dịch
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Tạo truy vấn và thiết lập tham số
            TypedQuery<Order> q = em.createQuery(qString, Order.class);
            q.setParameter("status", orderStatus);
            q.setParameter("orderId", orderId);

            // Cập nhật dữ liệu
            int rowsUpdated = q.executeUpdate();

            // Commit giao dịch nếu có thay đổi
            transaction.commit();

            // Nếu ít nhất một bản ghi bị ảnh hưởng, trả về true
            return rowsUpdated > 0;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback nếu có lỗi
            }
            return false;
        } finally {
            em.close(); // Đảm bảo đóng EntityManager
        }
    }

    @Override
    public Order getOrder(Long id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Order order = null;
        String query = "SELECT o FROM Order o " + "WHERE o.id = :id";
        TypedQuery<Order> q = em.createQuery(query, Order.class);
        q.setParameter("id", id);
        try{
            order = q.getSingleResult();
            return order;
        }
        catch(NoResultException e){
            return null;
        }
        finally{
            em.close();
        }
    }

    @Override
    public ArrayList<Order> filterOrders(Customer customer, EOrderStatus orderStatus) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT o FROM Order o WHERE o.customer = :customer AND o.status = :orderStatus";
        TypedQuery<Order> q = em.createQuery(qString, Order.class);
        q.setParameter("customer", customer);
        q.setParameter("orderStatus", orderStatus);

        ArrayList<Order> orders;
        try {
            orders = new ArrayList<>(q.getResultList());
        } finally {
            em.close();
        }
        return orders;
    }

    @Override
    public ArrayList<Order> loadOrdersOfCustomer(Customer customer) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT o FROM Order o WHERE o.customer = :customer";
        TypedQuery<Order> q = em.createQuery(qString, Order.class);
        q.setParameter("customer", customer);

        ArrayList<Order> orders;
        try {
            orders = new ArrayList<>(q.getResultList());
        } finally {
            em.close();
        }
        return orders;
    }

}
