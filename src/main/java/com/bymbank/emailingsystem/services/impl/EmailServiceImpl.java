package com.bymbank.emailingsystem.services.impl;

import com.bymbank.emailingsystem.services.EmailService;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String text, boolean html) throws MessagingException {
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setFrom("noreply@bym-bank.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text, html);
        this.mailSender.send(message.getMimeMessage());
    }
}
