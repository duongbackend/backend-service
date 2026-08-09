package com.duong.backendservice.service;

public interface MailService {
    void sendEmail(String to, String name, String subject, String templateName);
}
