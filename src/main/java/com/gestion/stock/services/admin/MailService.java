package com.gestion.stock.services.admin;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@Log
public class MailService {

    public static int noOfQuickServiceThreads = 20;
    private JavaMailSender sender;
    /**
     * this statement create a thread pool of twenty threads
     * here we are assigning send mail task using ScheduledExecutorService.submit();
     */
    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads); // Creates a thread pool that reuses fixed number of threads(as specified by noOfThreads in this case).

    @Autowired
    public MailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void send(String to, String subject, String body) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        quickService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    sender.send(message);
                } catch (Exception e) {
                    log.severe(e.getLocalizedMessage());
                }
            }
        });
    }
}
