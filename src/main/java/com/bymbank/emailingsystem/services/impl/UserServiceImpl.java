package com.bymbank.emailingsystem.services.impl;

import com.bymbank.emailingsystem.models.User;
import com.bymbank.emailingsystem.repositories.UserRepository;
import com.bymbank.emailingsystem.services.EmailService;
import com.bymbank.emailingsystem.services.UserService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private TemplateEngine templateEngine;
    private EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           TemplateEngine templateEngine,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.templateEngine = templateEngine;
        this.emailService = emailService;
    }
    @Override
    public void notifyTellerWithCreation(Long tellerId, String password) {
        User teller = userRepository.findById(tellerId).get();
        Context ctx = new Context();
        ctx.setVariable("teller", teller);
        ctx.setVariable("password", password);
        final String htmlContent = this.templateEngine.process("html/emails/users/create-teller.html", ctx);

        try {
            this.emailService.sendEmail(teller.getEmail(), "Your account has been created successfully", htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyAdminWithTellerCreation(Long adminId, Long tellerId) {
        User teller = userRepository.findById(tellerId).get();
        User admin = userRepository.findById(adminId).get();
        Context ctx = new Context();

        ctx.setVariable("teller", teller);
        ctx.setVariable("admin", admin);

        final String htmlContent = this.templateEngine.process("html/emails/users/admin-create-teller.html", ctx);

        try {
            this.emailService.sendEmail(admin.getEmail(), "Teller account has been created successfully", htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyUserWithCreation(Long userId, String password) {
        User user = userRepository.findById(userId).get();

        Context ctx = new Context();

        ctx.setVariable("user", user);
        ctx.setVariable("password", password);

        final String htmlContent = this.templateEngine.process("html/emails/users/create-user.html", ctx);

        try {
            this.emailService.sendEmail(user.getEmail(), "Your account has been created successfully", htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyTellerWithUserCreation(Long tellerId, Long userId) {
        User teller = userRepository.findById(tellerId).get();
        User user = userRepository.findById(userId).get();

        Context ctx = new Context();

        ctx.setVariable("user", user);
        ctx.setVariable("teller", teller);

        final String htmlContent = this.templateEngine.process("html/emails/users/teller-create-user.html", ctx);

        try {
            this.emailService.sendEmail(user.getEmail(), "User account has been created successfully", htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
