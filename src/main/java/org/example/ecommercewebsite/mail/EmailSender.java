package org.example.ecommercewebsite.mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    public static boolean sendOTP (String toEmail, String otp) {
        // Cấu hình thuộc tính SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP của Gmail
        properties.put("mail.smtp.port", "587"); // Cổng TLS

        // Tài khoản Gmail gửi OTP
        String myAccountEmail = "phamhoanghuy.2000vn@gmail.com";
        String password = "xxvb pzff upvb rshu"; // Mật khẩu email hoặc App password

        // Xác thực tài khoản Gmail
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        try {
            // Tạo thông điệp email
            Message message = prepareMessage(session, myAccountEmail, toEmail, otp);

            // Gửi email
            if (message!= null){
                Transport.send(message);
                System.out.println("OTP đã được gửi thành công đến email: " + toEmail);
                return true;
            }
        } catch (MessagingException e) {
            System.err.println("Có lỗi khi gửi email: " + e.getMessage());
        }
        return false;
    }

    private static Message prepareMessage(Session session, String fromEmail, String toEmail, String otp) {
        if (isValidEmail(toEmail)){
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromEmail)); // Sender email address
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail)); // Recipient email address
                message.setSubject("Your OTP Verification Code");
                message.setText("Hello,\n\nYour OTP code is: " + otp + "\n\nThank you!");
                return message;
            } catch (MessagingException e) {
                System.err.println("Error while preparing the email: " + e.getMessage());
            }
        }
        return null;
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return email.matches(emailRegex);
    }
}
