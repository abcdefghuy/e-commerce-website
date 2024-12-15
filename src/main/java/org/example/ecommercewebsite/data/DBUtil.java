package org.example.ecommercewebsite.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataProject");
    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
