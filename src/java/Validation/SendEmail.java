/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validation;

import Models.User;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ACER NITRO
 */
public class SendEmail {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    
    public String generateRandCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
    //send email to the user email
    public boolean sendEmail(User user,String code) {
        boolean test = false;

        String toEmail = user.getEmail();
        String fromEmail = "tranminhducbxvp@gmail.com";
        String password = "voxwhknyrytimqxu";

        try {

            // your host email smtp server details
            Properties pr = new Properties();
            pr.setProperty("mail.smtp.host", "smtp.gmail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.setProperty("mail.smtp.auth", "true");
            pr.setProperty("mail.smtp.starttls.enable", "true");

            //get session to authenticate the host email address and password
            Session session = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });
            //set email message details
            Message mess = new MimeMessage(session);
            //set from email address
            mess.addHeader("Content-type", "text/HTML; charset=UTF-8");
            mess.addHeader("format", "flowed");
            mess.addHeader("Content-Transfer-Encoding", "8bit");
            mess.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
            mess.setReplyTo(InternetAddress.parse(fromEmail, false));
            //set to email address or destination email address
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            //set email subject
            mess.setSubject("User Email Verification");
            //set message text
            mess.setText("Please verify your account using this code: "+code);
            //send the message
            Transport.send(mess);
            test = true;
        } catch (UnsupportedEncodingException | MessagingException e) {
        }
        return test;
    }
  
}
