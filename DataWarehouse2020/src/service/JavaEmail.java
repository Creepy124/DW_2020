package service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class JavaEmail {
    static Session mailSession;
 
    private static void setMailServerProperties() {
        Properties emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        mailSession = Session.getDefaultInstance(emailProperties, null);
    }
 
    //prepare: receivingPerson, mail subject, mail body
    private static MimeMessage draftEmailMessage(String error, String toEmails) throws AddressException, MessagingException {
        String[] emails = toEmails.split(",");
        
        String emailSubject = "Error";
        String emailBody = error;
        
        MimeMessage emailMessage = new MimeMessage(mailSession);
        /**
         * Set the mail recipients
         * */
        for (int i = 0; i < emails.length; i++)
        {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emails[i]));
        }
        emailMessage.setSubject(emailSubject);
        /**
         * If sending HTML mail
         * */
//        emailMessage.setContent(emailBody, "text/html");
        /**
         * If sending only text mail
         * */
        emailMessage.setText(emailBody);// for a text email
        return emailMessage;
    }
 
    //send mail through smtp
    private static void sendEmail(String error, String toEmails) throws AddressException, MessagingException
    {
        /**
         * Sender's credentials
         * */
        String fromUser = "MomoiroNyanko2612@gmail.com";
//    	 String fromUser ="creepy120499@gmail.com";
        String fromUserEmailPassword = "peDIA2612!";
//    	  String fromUserEmailPassword = "antrom113";
        
        String emailHost = "smtp.gmail.com";
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        /**
         * Draft the message
         * */
        MimeMessage emailMessage = draftEmailMessage(error, toEmails);
        /**
         * Send the mail
         * */
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
    
    
    public static void prepareSending(String error, String toEmails) throws AddressException, MessagingException {
        setMailServerProperties();
        sendEmail(error, toEmails);
    }
    
    public static void main(String[] args) {
		JavaEmail j = new JavaEmail();
		try {
			j.prepareSending("error","thuyphuongnguyen0170@gmail.com, creepy120499@gmail.com");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}