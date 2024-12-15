package org.example.ecommercewebsite.mail;

import java.util.Random;


public class OTPGenerater {
    public static String generateOTP(int length) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));  // Tạo số ngẫu nhiên từ 0-9
        }
        return otp.toString();
    }
}