package org.example.ecommercewebsite.DAO.Impl;
import org.example.ecommercewebsite.DAO.IFurnitureDAO;
import org.example.ecommercewebsite.DTO.request.FurnitureRequest;
import org.example.ecommercewebsite.ENumeration.EFurnitureStatus;
import org.example.ecommercewebsite.ENumeration.EOrderStatus;
import org.example.ecommercewebsite.business.Feedback;
import org.example.ecommercewebsite.business.Furniture;
import org.example.ecommercewebsite.business.Image;
import org.example.ecommercewebsite.data.DBUtil;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FurnitureDAOImpl implements IFurnitureDAO {
    private EntityManagerFactory emf;
    public FurnitureDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("dataProject");
    }
    @Override
    public void addFurniture(List<Furniture> furnitures) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            int batchSize = 50; // Kích thước mỗi batch
            for (int i = 0; i < furnitures.size(); i++) {
                Furniture furniture = furnitures.get(i);

                // Đảm bảo tất cả ảnh trong danh sách `furnitureImages` được liên kết đúng
                List<Image> images = furniture.getFurnitureImages();
                if (images != null && !images.isEmpty()) {
                    for (Image image : images) {
                        image.setFurniture(furniture); // Liên kết Image với Furniture
                    }
                }

                em.persist(furniture); // Lưu đối tượng Furniture cùng với các Image liên quan

                // Flush và clear sau mỗi batch để giảm tải bộ nhớ
                if ((i + 1) % batchSize == 0) {
                    em.flush();  // Đẩy thay đổi vào DB
                    em.clear();  // Giải phóng bộ nhớ của EntityManager
                }
            }
            trans.commit();
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm danh sách sản phẩm: " + e.getMessage());
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public int updateFurnitureByCategory(Furniture furniture) {
        // Kiểm tra đầu vào của đối tượng furniture
        if (furniture == null || furniture.getCategory() == null) {
            throw new IllegalArgumentException("Invalid furniture object or category.");
        }

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        int updatedCount = 0; // Biến lưu trữ số lượng bản ghi cập nhật

        try {
            // Bắt đầu giao dịch
            trans.begin();

            // Thực hiện truy vấn cập nhật
            updatedCount = em.createQuery(
                            "UPDATE Furniture f SET f.furniturePrice = :price, " +
                                    "f.furnitureColor = :color, " +
                                    "f.furnitureDescription = :description, " +
                                    "f.furnitureStatus = :status " +
                                    "WHERE f.category.id = :categoryId and f.order is null")
                    .setParameter("price", furniture.getFurniturePrice())
                    .setParameter("color", furniture.getFurnitureColor())
                    .setParameter("description", furniture.getFurnitureDescription())
                    .setParameter("status", furniture.getFurnitureStatus())
                    .setParameter("categoryId", furniture.getCategory().getId())
                    .executeUpdate();

            // Commit giao dịch sau khi truy vấn thành công
            trans.commit();
        } catch (Exception e) {
            // Rollback nếu có lỗi
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e; // Ném lại lỗi để có thể xử lý bên ngoài
        } finally {
            // Đảm bảo đóng EntityManager để giải phóng tài nguyên
            em.close();
        }
        // Trả về số lượng bản ghi đã cập nhật
        return updatedCount;
    }
    public void deleteImagesByCategory(Long categoryId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.createQuery(
                            "DELETE FROM Image img WHERE img.furniture.id IN " +
                                    "(SELECT f.id FROM Furniture f WHERE f.category.id = :categoryId and f.order is null)")
                    .setParameter("categoryId", categoryId)
                    .executeUpdate();
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public void saveImagesInBatch(List<Image> images) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            int batchSize = 20;
            for (int i = 0; i < images.size(); i++) {
                em.persist(images.get(i));
                if (i > 0 && i % batchSize == 0) {
                    em.flush();
                    em.clear();
                }
            }
            em.flush();
            em.clear();
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    @Override
    public List<Furniture> getFurnitureByCategoryID(Long categoryID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM Furniture f " +
                "WHERE f.category.id = :id";
        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("id", categoryID);
        List<Furniture> furnitureList = null;
        try {
            furnitureList = q.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return furnitureList;
    }
    @Override
    public Furniture getFurnitureByID(Long id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM Furniture f " +
                "WHERE f.id = :ID";
        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("ID", id);
        Furniture furniture = null;
        try {
            furniture = q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return furniture;
    }
    @Override
    public Map<Furniture, Long> getFurnitureList() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Map<Furniture, Long> furnitureCountMap = new HashMap<>();
        try {
            // Lấy danh sách các Furniture từ cơ sở dữ liệu
            List<Furniture> furnitures = em.createQuery("SELECT f FROM Furniture f WHERE f.id IN (" +
                    "SELECT MIN(f1.id) FROM Furniture f1 where f1.order is null Group BY f1.category.id )", Furniture.class).getResultList();

            for (Furniture furniture : furnitures) {
                // Tính số lượng các đối tượng Furniture có cùng furnitureID
                Long count = (Long) em.createQuery(
                                "SELECT COUNT(f) FROM Furniture f WHERE f.category.id = :id AND f.order IS NULL")
                        .setParameter("id", furniture.getCategory().getId())
                        .getSingleResult();

                furnitureCountMap.put(furniture, count); // Thêm vào map
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc ghi log
        } finally {
            em.close(); // Đảm bảo đóng EntityManager
        }
        return furnitureCountMap;
    }
    public List<Image> getImagesByFurnitureId(Long id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Image> images = null;
        try {
            // Truy vấn để lấy các ảnh dựa trên furnitureId
            TypedQuery<Image> query = em.createQuery(
                    "SELECT i FROM Image i WHERE i.furniture.id = :furnitureId", Image.class);
            query.setParameter("furnitureId", id);

            images = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return images;
    }
    @Override
    public List<Furniture> getFurnitureByFilters(Long categoryId, String priceRange) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Furniture> furnitureList = null;

        try {
            // Bắt đầu truy vấn
            StringBuilder query = new StringBuilder("SELECT f FROM Furniture f WHERE f.id IN (");
            query.append("SELECT MIN(f1.id) FROM Furniture f1 WHERE f1.category.id = f.category.id");

            // Lọc theo categoryId nếu có
            if (categoryId != null) {
                query.append(" AND f1.category.id = :categoryId");
            }

            // Lọc theo priceRange nếu có
            if (priceRange != null && !priceRange.isEmpty()) {
                switch (priceRange) {
                    case "<5000000":
                        query.append(" AND f1.furniturePrice < 5000000");
                        break;
                    case "5000000 - 10000000":
                        query.append(" AND f1.furniturePrice BETWEEN 5000000 AND 10000000");
                        break;
                    case "10000000 - 20000000":
                        query.append(" AND f1.furniturePrice BETWEEN 10000000 AND 20000000");
                        break;
                    case ">20000000":
                        query.append(" AND f1.furniturePrice > 20000000");
                        break;
                }
            }

            // Đóng ngoặc cho truy vấn con
            query.append(")");

            // Tạo và thực thi truy vấn
            TypedQuery<Furniture> q = em.createQuery(query.toString(), Furniture.class);

            // Set tham số cho categoryId nếu có
            if (categoryId != null) {
                q.setParameter("categoryId", categoryId);
            }

            furnitureList = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // hoặc ghi log
        } finally {
            em.close();
        }

        return furnitureList;
    }

    @Override
    public List<Furniture> getAllFurnitures(int limit, int skip, String keyword, int price, String color, String nsx) {
        if (keyword == null){
            keyword = "";
        }
        List<String> words =  Arrays.asList(keyword.split(" "));
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM Furniture f WHERE f.id IN (" +
                "SELECT MIN(f2.id) FROM Furniture f2 WHERE " +
                "f2.order IS NULL AND " +
                "f2.furnitureStatus = :status AND ";
        if (words != null && !words.isEmpty()) {
            qString += "(";
            for (int i = 0; i < words.size(); i++) {
                qString += " f2.category.categoryName LIKE :words" + i;
                if (i < words.size()-1){
                    qString += " AND ";
                }
            }
            qString += ") AND ";
        }
        qString += "f2.furniturePrice <= :price ";
        if (color != null && !color.isEmpty()) {
            qString += "AND f2.furnitureColor LIKE :color ";
        }
        if (nsx != null && !nsx.isEmpty()) {
            qString += "AND f2.category.manufacture LIKE :nsx ";
        }
        qString +=  "GROUP BY f2.category.id)";

        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("status", EFurnitureStatus.ON_SALE);
        q.setParameter("price", price);
        if (words != null && !words.isEmpty()) {
            for (int i = 0; i < words.size(); i++) {
                q.setParameter("words" + i, "%" + words.get(i) + "%");
            }
        }
        if (color != null && !color.isEmpty()) {
            q.setParameter("color", "%" + color + "%");
        }
        if (nsx != null && !nsx.isEmpty()) {
            q.setParameter("nsx", "%" + nsx + "%");
        }
        q.setFirstResult(skip); // Số lượng record bỏ qua
        q.setMaxResults(limit);  // Số lượng record lấy

        try {
            List<Furniture> listFurniture = q.getResultList();
            return listFurniture;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Furniture> getFurnitureNew() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM Furniture f WHERE f.id IN (" +
                "SELECT MIN(f2.id) FROM Furniture f2 WHERE " +
                "f2.order IS NULL AND " +
                "f2.furnitureStatus = :status " +
                "GROUP BY f2.category.id) " +
                "order by f.category.id desc ";
        TypedQuery q = em.createQuery(qString, Furniture.class);
        q.setParameter("status", EFurnitureStatus.ON_SALE);
        q.setMaxResults(5);
        try{
            List<Furniture> listFurniture = q.getResultList();
            return listFurniture;
        }
        catch (NoResultException exception){
            return null;
        }
        finally {
            em.close();
        }
    }

    @Override
    public long countFurniture(String keyword, int price, String color, String nsx) {
        if (keyword == null){
            keyword = "" ;
        }
        List<String> words =  Arrays.asList(keyword.split(" "));
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT COUNT(f) FROM Furniture f WHERE f.id IN (" +
                "SELECT MIN(f2.id) FROM Furniture f2 WHERE " +
                "f2.order IS NULL AND " +
                "f2.furnitureStatus = :status AND ";
        if (words != null && !words.isEmpty()) {
            qString += "(";
            for (int i = 0; i < words.size(); i++) {
                qString += " f2.category.categoryName LIKE :words" + i;
                if (i < words.size()-1){
                    qString += " AND ";
                }
            }
            qString += ") AND ";
        }
        qString += "f2.furniturePrice <= :price ";
        if (color != null && !color.isEmpty()) {
            qString += "AND f2.furnitureColor LIKE :color ";
        }
        if (nsx != null && !nsx.isEmpty()) {
            qString += "AND f2.category.manufacture LIKE :nsx ";
        }
        qString +=  "GROUP BY f2.category.id)";

        Query q = em.createQuery(qString);
        q.setParameter("status", EFurnitureStatus.ON_SALE);
        q.setParameter("price", price);
        if (words != null && !words.isEmpty()) {
            for (int i = 0; i < words.size(); i++) {
                q.setParameter("words" + i, "%" + words.get(i) + "%");
            }
        }
        if (color != null && !color.isEmpty()) {
            q.setParameter("color", "%" + color + "%");
        }
        if (nsx != null && !nsx.isEmpty()) {
            q.setParameter("nsx", "%" + nsx + "%");
        }

        try {
            return (long) q.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public Furniture getFurniture(int id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM Furniture f where f.category.id = :ID AND " +
                "f.order IS NULL AND " +
                "f.furnitureStatus = :status ";
        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("ID", id);
        q.setParameter("status", EFurnitureStatus.ON_SALE);
        q.setMaxResults(1);
        try {
            Furniture furniture = q.getSingleResult();
            return furniture;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    @Override
    public List<Furniture> getFurnitureQuantity(String categoryID, int quantity) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        // Tạo câu truy vấn lọc sản phẩm theo category và status
        String qString = "SELECT f FROM Furniture f " +
                "WHERE f.category.id = :categoryId " +
                "AND f.furnitureStatus = :status " +
                "AND f.order IS NULL";

        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("categoryId", Long.parseLong(categoryID));
        q.setParameter("status", EFurnitureStatus.ON_SALE);

        try {
            q.setMaxResults(quantity);  // Nếu số lượng đủ, giới hạn kết quả lấy theo số lượng yêu cầu
            List<Furniture> listFurniture = q.getResultList();
            System.out.println(listFurniture.size());
            // Kiểm tra số lượng sản phẩm trả về
            if (listFurniture.size() < quantity) {
                return null;  // Nếu số lượng ít hơn yêu cầu, trả về null
            }
            return listFurniture;

        } catch (NoResultException e) {
            return null;  // Trả về null nếu không có kết quả
        } finally {
            em.close();  // Đảm bảo EntityManager được đóng
        }
    }
    @Override
    public Furniture getFurnitureDiscount(String categoryID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        // Tạo câu truy vấn lọc sản phẩm theo category và status
        String qString = "SELECT f FROM Furniture f " +
                "WHERE f.category.id = :categoryId";

        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("categoryId", Long.parseLong(categoryID));

        try {
            // Lấy danh sách các sản phẩm
            List<Furniture> furnitureList = q.getResultList();

            // Kiểm tra nếu danh sách không rỗng, trả về một sản phẩm ngẫu nhiên
            if (!furnitureList.isEmpty()) {
                // Lấy một phần tử ngẫu nhiên trong danh sách
                return furnitureList.get(0); // Hoặc có thể thay `0` bằng một chỉ số ngẫu nhiên
            } else {
                return null;  // Trả về null nếu không có kết quả
            }
        } catch (NoResultException e) {
            return null;  // Trường hợp không có kết quả
        } finally {
            em.close();  // Đảm bảo EntityManager được đóng
        }
    }
    @Override
    public List<String> getListColor (){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f.furnitureColor FROM Furniture f group by f.furnitureColor";
        Query q = em.createQuery(qString);
        q.setMaxResults(10);
        try {
            List<String> listColor = (List<String>) q.getResultList();
            return listColor;
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }

    @Override
    public List<String> getListNSX (){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f.category.manufacture FROM Furniture f group by f.category.manufacture";
        Query q = em.createQuery(qString);
        q.setMaxResults(7);
        try {
            List<String> listColor = (List<String>) q.getResultList();
            return listColor;
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }
    @Override
    public List<Feedback> getFeedBacks (){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT fe from Feedback fe ORDER BY fe.rate DESC";
        TypedQuery<Feedback> q = em.createQuery(qString, Feedback.class);
        try {
            List<Feedback> listFeedback = q.getResultList();
            return listFeedback;
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }

    @Override
    public List<Furniture> getFurnituresHot (int topLimit){

        EntityManager em = DBUtil.getEmFactory().createEntityManager();


        String qStringSub = "SELECT f2.category.id " +
                "FROM Order o JOIN o.listFurniture f2 " +
                "WHERE o.status = :statusO OR o.status = :statusO1 " +
                "GROUP BY f2.category.id " +
                "ORDER BY COUNT(f2) DESC";

        TypedQuery<Integer> subQuery = em.createQuery(qStringSub, Integer.class);
        subQuery.setParameter("statusO", EOrderStatus.ACCEPTED);
        subQuery.setParameter("statusO1", EOrderStatus.FEEDBACKED);
        subQuery.setMaxResults(topLimit); // Chỉ lấy top N
        List<Integer> topCategoryIds  = null;
        try {
            topCategoryIds = subQuery.getResultList();
        }
        catch (NoResultException e) {
            topCategoryIds = null;
        }

        System.out.println(topCategoryIds);
        if (topCategoryIds == null || topCategoryIds.isEmpty()) {
            return null;
        }

        String qStringMain = "SELECT f FROM Furniture f WHERE " +
                "f.category.id IN :topCategoryIds " +
                "AND f.id = (SELECT MIN(f3.id) FROM Furniture f3 WHERE f3.category.id = f.category.id AND f3.order is null) " +
                "AND f.order IS NULL " +
                "AND f.furnitureStatus = :status " ;

        TypedQuery<Furniture> mainQuery = em.createQuery(qStringMain, Furniture.class);
        mainQuery.setParameter("topCategoryIds", topCategoryIds);
        mainQuery.setParameter("status", EFurnitureStatus.ON_SALE);

        try {
            List<Furniture> listFurniture = mainQuery.getResultList();
            System.out.println(listFurniture);
            return listFurniture;
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public Furniture getFurnitureIfExists(String categoryName, String furnitureColor, Long furniturePrice) {
        // Tạo truy vấn để tìm sản phẩm với tên, màu sắc, và giá tương ứng
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT f FROM Furniture f WHERE f.category.categoryName  = :categoryName AND f.furnitureColor = :furnitureColor AND f.furniturePrice = :furniturePrice";
        TypedQuery<Furniture> typedQuery = em.createQuery(query, Furniture.class);
        typedQuery.setParameter("categoryName", categoryName);
        typedQuery.setParameter("furnitureColor", furnitureColor);
        typedQuery.setParameter("furniturePrice", furniturePrice);

        List<Furniture> result = typedQuery.getResultList();

        if (result.isEmpty()) {
            return null; // Không tìm thấy sản phẩm
        } else {
            return result.get(0); // Trả về sản phẩm đã tồn tại
        }
    }
    public Long countFurnitureByCategoryId(Long categoryId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            String jpql = "SELECT COUNT(f) FROM Furniture f WHERE f.category.id = :id";
            return em.createQuery(jpql, Long.class)
                    .setParameter("id", categoryId)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            em.close();
        }
    }
    public List<Furniture> getFurnitureByIDs(List<Long> ids) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT f FROM Furniture f WHERE f.id IN :ids";
        return em.createQuery(query, Furniture.class)
                .setParameter("ids", ids)
                .getResultList();
    }
    @Override
    public void updateFurnitureList(List<Furniture> furnitureList) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        // Bắt đầu transaction
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin(); // Bắt đầu transaction

            for (Furniture furniture : furnitureList) {
                em.merge(furniture); // Sử dụng merge để cập nhật
            }

            transaction.commit(); // Commit transaction
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback nếu có lỗi
            }
            throw e; // Ném lại lỗi sau khi rollback
        } finally {
            em.close(); // Đóng EntityManager
        }
    }
    @Override
    public List<Furniture> getFurnituresByOrderId(Long orderId) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT f FROM Furniture f WHERE f.order.id = :orderId";
        TypedQuery<Furniture> query = em.createQuery(jpql, Furniture.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
    @Override
    public List<Furniture> getFurnituresByCustomerId(FurnitureRequest furnitureRequest) {
        EntityManager em = emf.createEntityManager();

        StringBuilder jpql = new StringBuilder("SELECT f FROM Furniture f " +
                "JOIN f.order o " +
                "JOIN f.category c " +
                "WHERE o.customer.personID = :customerId ");
        jpql.append("AND o.status IN (:status1, :status2, :status3) ");

        if (furnitureRequest.getCategoryName() != null && !furnitureRequest.getCategoryName().isEmpty()) {
            String[] searchTerms = furnitureRequest.getCategoryName().split("\\s+");  // Tách chuỗi theo khoảng trắng
//AND (c.categoryName LIKE :categoryName0 OR c.categoryName LIKE :categoryName1 OR c.categoryName LIKE :categoryName2)
            for (int i = 0; i < searchTerms.length; i++) {
                if (i == 0) {
                    jpql.append("AND (c.categoryName LIKE :categoryName" + i + " ");
                } else {
                    jpql.append("OR c.categoryName LIKE :categoryName" + i + " ");
                }
            }
            jpql.append(") ");
        }

        if (furnitureRequest.getPriceStart() != null) {
            jpql.append("AND f.furniturePrice >= :priceStart ");
        }
        if (furnitureRequest.getPriceEnd() != null) {
            jpql.append("AND f.furniturePrice <= :priceEnd ");
        }

        // Tạo câu truy vấn
        TypedQuery<Furniture> query = em.createQuery(jpql.toString(), Furniture.class);
        query.setParameter("customerId", furnitureRequest.getCustomerId());
        query.setParameter("status1", EOrderStatus.FEEDBACKED);
        query.setParameter("status2", EOrderStatus.DELIVERED);
        query.setParameter("status3", EOrderStatus.DELIVERING);

        if (furnitureRequest.getCategoryName() != null && !furnitureRequest.getCategoryName().isEmpty()) {
            String[] searchTerms = furnitureRequest.getCategoryName().split("\\s+");  // Tách chuỗi tìm kiếm
            for (int i = 0; i < searchTerms.length; i++) {
                query.setParameter("categoryName" + i, "%" + searchTerms[i] + "%");  // Thêm dấu % để sử dụng LIKE
            }
        }
        if (furnitureRequest.getPriceStart() != null) {
            query.setParameter("priceStart", furnitureRequest.getPriceStart());
        }
        if (furnitureRequest.getPriceEnd() != null) {
            query.setParameter("priceEnd", furnitureRequest.getPriceEnd());
        }
        return query.getResultList();
    }

}
