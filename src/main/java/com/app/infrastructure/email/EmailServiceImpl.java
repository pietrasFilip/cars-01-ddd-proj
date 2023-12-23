package com.app.infrastructure.email;

import com.app.application.service.email.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.stereotype.Service;

import static j2html.TagCreator.head;
import static j2html.TagCreator.html;

@Service
public class EmailServiceImpl implements EmailService {
    final Mailer mailer;
    EmailConfiguration emailConfiguration;
    private static final Logger logger = LogManager.getRootLogger();
    public EmailServiceImpl(Mailer mailer, EmailConfiguration emailConfiguration) {
        this.mailer = mailer;
        this.emailConfiguration = emailConfiguration;
    }

    @Override
    public void send(String receiver, String subject, String message) {
        var email = EmailBuilder
                .startingBlank()
                .from(emailConfiguration.fromName, emailConfiguration.fromAddress)
                .to(receiver)
                .withSubject(subject)
                .withHTMLText(message)
                .buildEmail();
        mailer.sendMail(email)
                .thenAccept(res -> {});
    }

    private void mailSentNotification(String notificationReceiver, String receiver) {
        send(notificationReceiver, "MAIL SENT NOTIFICATION",
                html(head("MAIL SENT TO: %s".formatted(receiver))).toString());
        logger.info("MAIL SENT TO: {}%n", receiver);
    }
}
