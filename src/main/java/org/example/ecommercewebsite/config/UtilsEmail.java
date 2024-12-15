package org.example.ecommercewebsite.config;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class UtilsEmail {
    // Hằng số email và mật khẩu
    private static final String USERNAME = "furnitureshop267@gmail.com";
    private static final String PASSWORD = "mhtl zdjn hyaj udsa";

    /**
     * Phương thức gửi email
     *
     * @param recipientEmail Địa chỉ email người nhận
     * @param subject        Tiêu đề email
     * @param content        Nội dung email (dạng HTML)
     */
    public static void sendEmail(String recipientEmail, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Tạo session với thông tin xác thực
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Tạo email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            try {
                message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println("Failed to encode email subject.");
            }
            // Tạo nội dung email
            MimeBodyPart textPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            textPart.setContent(content, "text/html; charset=UTF-8");
            multipart.addBodyPart(textPart);
            message.setContent(multipart);

            // Gửi email
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email to " + recipientEmail);
        }
    }
}
