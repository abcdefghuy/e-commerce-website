package org.example.ecommercewebsite.utils;
import javax.servlet.http.*;

public class SessionUtils {
    public static  boolean  isValidUser (HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);

        return session != null && session.getAttribute("customer") != null;
    }
}
