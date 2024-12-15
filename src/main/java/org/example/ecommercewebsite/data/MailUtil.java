package org.example.ecommercewebsite.data;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    public static void sendMail(String from, String to, String subject, String content) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", 465);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        Message message = new MimeMessage(session);
        message.setSubject(subject);
        message.setText(content);
        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        Transport transport = session.getTransport();
        transport.connect("hdphat123@gmail.com", "nxpu okww yhje ueaw");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
