package org.example.ecommercewebsite.DAO.Impl;

import org.example.ecommercewebsite.DAO.IChatDAO;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Message;
import org.example.ecommercewebsite.business.Staff;
import org.example.ecommercewebsite.data.DBUtil;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class ChatDAOImpl implements IChatDAO {

    @Override
    public List<Customer> getCustomerList(Long staffID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Customer> customers = null;

        String query = "SELECT DISTINCT c " +
                "FROM Customer c " +
                "LEFT JOIN Message m ON c.personID = m.incomingMsgID";

        try {
            customers = em.createQuery(query, Customer.class).getResultList();
            customers = customers.stream()
                    .sorted((c1, c2) -> {

                        Message msg1 = getLatestMessage(c1.getPersonID(), staffID);
                        Message msg2 = getLatestMessage(c2.getPersonID(), staffID);

                        if (msg1 != null && msg2 != null) {
                            return msg2.getSentDate().compareTo(msg1.getSentDate());
                        }

                        if (msg1 == null && msg2 == null) {
                            return 0;
                        } else if (msg1 == null) {
                            return 1;
                        } else {
                            return -1;
                        }
                    })
                    .collect(Collectors.toList());

            System.out.println(customers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return customers;
    }

    @Override
    public List<Staff> getStaffChatList(Long customerID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Staff> staffs = null;
        String query = "SELECT DISTINCT s " +
                "FROM Staff s " +
                "LEFT JOIN Message m ON s.personID = m.incomingMsgID ";

        try {
            staffs = em.createQuery(query, Staff.class).getResultList();

            staffs = staffs.stream()
                    .sorted((s1, s2) -> {
                        Message msg1 = getLatestMessage(s1.getPersonID(), customerID);
                        Message msg2 = getLatestMessage(s2.getPersonID(), customerID);

                        if (msg1 != null && msg2 != null) {
                            return msg2.getSentDate().compareTo(msg1.getSentDate());
                        }

                        if (msg1 == null && msg2 == null) {
                            return 0;
                        } else if (msg1 == null) {
                            return 1;
                        } else {
                            return -1;
                        }
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return staffs;
    }

    @Override
    public List<Message> getChatHistory(Long outgoingID, Long incomingID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Message> chatHistory = null;
        try {
            String query = "SELECT m FROM Message m " +
                    "WHERE (m.incomingMsgID = :incomingID AND m.outgoingMsgID = :outgoingID) " +
                    "   OR (m.incomingMsgID = :outgoingID AND m.outgoingMsgID = :incomingID) " +
                    "ORDER BY m.sentDate ASC";
            chatHistory = em.createQuery(query, Message.class)
                    .setParameter("incomingID", incomingID)
                    .setParameter("outgoingID", outgoingID)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return chatHistory;
    }

    @Override
    public Message getLatestMessage(Long personID1, Long personID2) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Message> messages = null;

        String query = "SELECT m FROM Message m " +
                "WHERE (m.incomingMsgID = :personID1 AND m.outgoingMsgID = :personID2) " +
                "   OR (m.incomingMsgID = :personID2 AND m.outgoingMsgID = :personID1) " +
                "ORDER BY m.sentDate DESC";
        messages = em.createQuery(query, Message.class)
                .setParameter("personID1", personID1)
                .setParameter("personID2", personID2)
                .setMaxResults(1)
                .getResultList();
        //getSingleResult()

        if (messages.isEmpty()) {
            return null;
        } else {
            return messages.get(0);
        }
    }

    @Override
    public boolean insertMessage(Message message) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(message);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }


}

