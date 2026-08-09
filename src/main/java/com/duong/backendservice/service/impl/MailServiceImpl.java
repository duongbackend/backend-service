package com.duong.backendservice.service.impl;

import com.duong.backendservice.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j(topic = "MAIL-SERVICE")
public class MailServiceImpl implements MailService {
    @Value( "${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public MailServiceImpl(JavaMailSender javaMailSender, @Qualifier("emailTemplateEngine") TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    @Override
    public void sendEmail(String to, String name, String subject, String templateName) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
            helper.setFrom(from, "BackendService");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setSentDate(new Date());

            Context context = new Context();
            context.setVariable("name", name);

            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);

            log.info("Send email to email: {}", to);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Send email error to email: {} {}", to, e.getMessage());
        }
    }
}
