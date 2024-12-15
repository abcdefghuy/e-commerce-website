package org.example.ecommercewebsite.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UtilsDb {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataProject");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
