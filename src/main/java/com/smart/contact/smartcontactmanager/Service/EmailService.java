package com.smart.contact.smartcontactmanager.Service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  public boolean sendEmail(String subject, String message, String to) {
    boolean flag = false;
    String from = "md.shahidalam.cse24@heritageit.edu.in";
    //variable for gmail
    String host = "smtp.gmail.com";

    //get system properties
    Properties properties = System.getProperties();
    System.out.println("Properties " + properties);

    //Setting important info to properties object

    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    // Step 1 to get session object
    Session session = Session.getInstance(
      properties,
      new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            //Edit password before login
          return new PasswordAuthentication(
            "md.shahidalam.cse24@heritageit.edu.in",
            "***********"
          );
        }
      }
    );

    session.setDebug(true);
    MimeMessage m = new MimeMessage(session);
    try {
      m.setFrom(from);
      m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      m.setSubject(subject);
      m.setText(message);
      Transport.send(m);
      //   System.out.println("Sent Success");
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }
}
