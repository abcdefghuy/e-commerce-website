package org.example.ecommercewebsite.util;

public class ValidationUtils {
    public static boolean isValidEmail (String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";
        return email.matches(emailRegex);
    }
}
