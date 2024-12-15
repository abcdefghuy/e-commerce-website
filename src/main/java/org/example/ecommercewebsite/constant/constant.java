package org.example.ecommercewebsite.constant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class constant {
    private static final Properties properties = new Properties();

    // Các hằng số sẽ được gán từ file cấu hình
    public static String GOOGLE_CLIENT_ID;
    public static String GOOGLE_CLIENT_SECRET;

    static {
        try {
            // Tải file properties từ hệ thống (tệp config.properties trong thư mục gốc của dự án)
            FileInputStream inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);

            // Đọc các giá trị từ file cấu hình và gán vào các hằng số
            GOOGLE_CLIENT_ID = properties.getProperty("GOOGLE_CLIENT_ID");
            GOOGLE_CLIENT_SECRET = properties.getProperty("GOOGLE_CLIENT_SECRET");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/loginGG";

    public static final String GOOGLE_GRANT_TYPE = "authorization_code";

    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
}
