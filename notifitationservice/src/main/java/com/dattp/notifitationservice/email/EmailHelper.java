package com.dattp.notifitationservice.email;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailHelper {
    // public static String sendMailOutlook(String mailFrom, String mailTo, String subTitle, String content, String password){
    //     try {
    //         Properties prop = new Properties();
    //         prop.put("mail.smtp.host", "smtp.office365.com");
    //         prop.put("mail.smtp.starttls.enable", "true");
    //         prop.put("mail.smtp.port", "587");
    //         prop.put("mail.smtp.auth", "true");

    //         Session session = Session.getInstance(prop, new Authenticator() {
    //             @Override
    //             protected PasswordAuthentication getPasswordAuthentication() {
    //                 return new PasswordAuthentication(mailFrom, password);
    //             }
    //         });

    //         Message message = new MimeMessage(session);
    //         message.setFrom(new InternetAddress(mailFrom));
    //         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
    //         message.setSubject(subTitle);
    //         MimeBodyPart mimeBodyPart = new MimeBodyPart();
    //         mimeBodyPart.setContent(content, "text/html; charset=utf-8");
    //         Multipart multipart = new MimeMultipart();
    //         multipart.addBodyPart(mimeBodyPart);
    //         message.setContent(multipart);
    //         Transport.send(message);
    //     } catch (Exception e) {
    //         return e.getMessage();
    //     }
    //     return "OK";
    // }
    public static String sendMailOutlook(String mailFrom, String mailTo, String subTitle, String content, String password){
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.office365.com");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");


            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setJavaMailProperties(prop);
            mailSender.setUsername(mailFrom);
            mailSender.setPassword(password);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
            );
            helper.setFrom(mailFrom);
            helper.setTo(mailTo);
            helper.setSubject(subTitle);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }
    public static void main(String[] args) {
        System.out.println(EmailHelper.sendMailOutlook("kiennc.b19at101@stu.ptit.edu.vn", "dattp.b19at040@stu.ptit.edu.vn"
        , "MƯỢN MAIL TEST TÝ", "Cho mượng test tí", "3Au5Re9U"));
    }
}