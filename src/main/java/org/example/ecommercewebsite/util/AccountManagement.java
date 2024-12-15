package org.example.ecommercewebsite.util;

import java.security.SecureRandom;

public class AccountManagement
{
    // Các ký tự để tạo mật khẩu
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";

    private static final String ALL_CHARACTERS = UPPER_CASE + LOWER_CASE + DIGITS;
    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(int length) {
        if (length < 6) {
            throw new IllegalArgumentException("Độ dài mật khẩu nên từ 6 ký tự trở lên để đảm bảo tính bảo mật.");
        }

        StringBuilder password = new StringBuilder(length);

        // Thêm các ký tự ngẫu nhiên khác để đạt đủ độ dài yêu cầu
        for (int i = 0; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // Trộn chuỗi để đảm bảo tính ngẫu nhiên
        return shuffleString(password.toString());
    }

    // Hàm phụ trợ để trộn chuỗi
    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int j = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }
}
