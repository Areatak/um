package com.areatak.util;

import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alirezaghias on 5/8/2017 AD.
 */
@Component
@Scope("singleton")
public class MailUtil {
    @Value("${simplejavamail.smtp.host}")
    String host;
    @Value("${simplejavamail.smtp.username}")
    String username;
    @Value("${simplejavamail.smtp.password}")
    String password;
    @Value("${simplejavamail.smtp.port}")
    String port;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    public void sendMail(String emailAddress, String subject, String messageHtml) {
        sendMail(emailAddress, subject, messageHtml, "", null);
    }
    public void sendMail(String emailAddress, String subject, String messageHtml, String imageName, byte[] image) {
        sendMail(emailAddress, subject, messageHtml, imageName, image, "", null);
    }
    @Async
    public void sendMail(String emailAddress, String subject, String messageHtml, String imageName, byte[] image, String pdfName, byte[] pdfData) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Email email = new Email();
                email.setFromAddress("Utadoc", "info@utadoc.com");
                email.addRecipient(emailAddress, emailAddress, Message.RecipientType.TO);
                email.setSubject(subject);
                email.setText("");
                email.setTextHTML(messageHtml);
                if (pdfData != null)
                    email.addAttachment(pdfName, pdfData, "application/pdf");
                if (image != null)
                    email.addEmbeddedImage(imageName, image, "image/png");
                new Mailer(
                        new ServerConfig(host, Integer.parseInt(port)),
                        TransportStrategy.SMTP_PLAIN
                ).sendMail(email);
            }
        });
    }
}
