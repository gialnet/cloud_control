/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.alta;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author antonio
 */
public class SendEmail {


      // Sender's email ID needs to be mentioned
      private final String from = "comercial@redmoon.es";

      // Assuming you are sending email from localhost
      private final String host = "smtp.redmoon.es";
      
      private final String usuario="comercial@redmoon.es";
      private final String passwd="Comer2010";
            

    /**
     * Enviar correo con documento adjunto
     * @param destino
     * @param asunto
     * @param cuerpo
     * @param adjunto 
     */
    public void EnviarWithAdjunto(String destino, String asunto, String cuerpo, byte[] adjunto)
    {
    /*
        *   
            email: comercial@redmoon.es
            pasword: Comer2010
            * Servidor de correo saliente (SMTP): smtp.redmoon.es
            * Servidor de correo entrante (POP/IMAP/IMAP SSL): mail.redmoon.es
        */
      

      // Get system properties
      Properties propiedades = new Properties();
      
      // Setup mail server
      
      propiedades.setProperty("mail.smtp.host", host);
      propiedades.setProperty("mail.smtp.starttls.enable", "true");
      propiedades.setProperty("mail.smtp.auth", "true");
      propiedades.setProperty("mail.user", "comercial@redmoon.es");
      propiedades.setProperty("mail.password", "Comer2010");
      propiedades.setProperty("mail.smtp.port", "25");
      
      // no estoy seguro
      //propiedades.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
      
      // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, passwd);
            }
        };
        
        // Get the default Session object.
        Session session = Session.getInstance(propiedades, auth);

      
      

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         //message.setHeader("Content-Type","multipart/mixed; boundary=\"frontera\"");
         
         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(destino));

         // Set Subject: header field
         message.setSubject(asunto);

         // Create the message part 
         BodyPart messageBodyPart = new MimeBodyPart();

         // Fill the message
         messageBodyPart.setText(cuerpo);
         
         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         
         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
         //messageBodyPart = new MimeBodyPart();
         //String filename = "myTabla.pdf";
         
         // Type of mail, only sopported types related (html) and mixed.
         DataSource ds = new ByteArrayDataSource(adjunto, "multipart/mixed");
         //DataSource ds = new ByteArrayDataSource(bytes, "multipart/"+type);

	MimeMultipart mp = new MimeMultipart(ds);
       
	MimeMultipart multiPart = new MimeMultipart("mixed");

	for (int i = 0; i < mp.getCount(); i ++) {
            multiPart.addBodyPart(mp.getBodyPart(i));
	}

         /*DataSource source = new FileDataSource(filename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filename);
         
         multipart.addBodyPart(messageBodyPart);*/

         // Send the complete message parts
         message.setContent(multipart);

         // Send message
         Transport.send(message);
         //System.out.println("Sent message successfully....");
         
      }catch (MessagingException mex) {
          Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, mex);
      }
   }
}
