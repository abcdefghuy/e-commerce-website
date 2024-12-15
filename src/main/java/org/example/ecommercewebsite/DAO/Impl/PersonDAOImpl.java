package org.example.ecommercewebsite.DAO.Impl;

import org.example.ecommercewebsite.DAO.IPersonDAO;
import org.example.ecommercewebsite.business.Person;
import org.example.ecommercewebsite.util.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class PersonDAOImpl implements IPersonDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataProject");
    String tableName;
    // Method to save a new person
    @Override
    public void savePerson(Person person) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error saving person: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    @Override
    public Person getPersonByIdOrDefault(Long personID) {
        System.out.println("Fetching Person ID: " + personID);
        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, personID);
            if (person == null) {
                // Trả về giá trị mặc định
                return new Person("Default Name", "default@example.com", "12345678");
            }
            return person;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Method to update an existing person
    @Override
    public void updatePerson(Person person) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error updating person: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Method to delete a person by ID
    @Override
    public void deletePerson(Long personID) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, personID);
            if (person != null) {
                em.remove(person);
                em.getTransaction().commit();
            } else {
                System.out.println("Person not found with ID: " + personID);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error deleting person: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Method to retrieve a person by ID
    @Override
    public Person getPersonById(Long personID) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Person.class, personID);
        } catch (Exception e) {
            System.out.println("Error retrieving person: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean emailExists(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            // Kiểm tra trong bảng Customer
            String qCustomer = "SELECT COUNT(c) FROM Customer c WHERE c.email = :email";
            Long customerCount = (Long) em.createQuery(qCustomer).setParameter("email", email).getSingleResult();
            if(customerCount > 0) {
                tableName = "Customer";
                return true;
            }

            String qOwner = "SELECT COUNT(o) FROM Owner o WHERE o.email = :email";
            Long ownerCount = (Long) em.createQuery(qOwner).setParameter("email", email).getSingleResult();
            if(ownerCount > 0) {
                tableName = "Owner";
                return true;
            }

            String qStaff = "SELECT COUNT(s) FROM Staff s WHERE s.email = :email";
            Long staffCount = (Long) em.createQuery(qStaff).setParameter("email", email).getSingleResult();
            if(staffCount > 0) {
                tableName = "Staff";
                return true;
            }
            return false;

        } finally {
            em.close();
        }
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            // Câu lệnh JPQL UPDATE
            String query = "UPDATE " + tableName + " t SET t.password = :password WHERE t.email = :email";
            int rowsUpdated = em.createQuery(query)
                    .setParameter("password", newPassword)
                    .setParameter("email", email)
                    .executeUpdate();

            em.getTransaction().commit();
            return rowsUpdated > 0; // Trả về true nếu có dòng được cập nhật
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
