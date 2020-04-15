package com.bymbank.emailingsystem.services;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String text, boolean html) throws MessagingException;
}
