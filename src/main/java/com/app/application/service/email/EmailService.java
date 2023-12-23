package com.app.application.service.email;


public interface EmailService {
    void send(String recipient, String subject, String content);
}
