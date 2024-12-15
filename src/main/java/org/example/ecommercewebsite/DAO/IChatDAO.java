package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.business.Message;
import org.example.ecommercewebsite.business.Staff;

import java.util.List;

public interface IChatDAO {
    public List<Customer> getCustomerList(Long staffID);
    public List<Staff> getStaffChatList(Long customerID);
    public List<Message> getChatHistory(Long outgoingID, Long incomingID);
    public Message getLatestMessage(Long personID1, Long personID2);
    public boolean insertMessage(Message message);
}
