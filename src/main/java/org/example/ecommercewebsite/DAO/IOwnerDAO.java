package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Owner;

public interface IOwnerDAO {
    public Owner getOwnerByEmailPass(String email, String password);
}
