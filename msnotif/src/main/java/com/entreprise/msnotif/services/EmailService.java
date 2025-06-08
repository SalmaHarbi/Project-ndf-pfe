package com.entreprise.msnotif.services;

import com.entreprise.msnotif.dtos.NotificationEmailModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendTemplateEmail(String to, String subject, NotificationEmailModel model) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);

        Context context = new Context();
        context.setVariable("firstname", model.getFirstname());
        context.setVariable("lastname", model.getLastname());
        context.setVariable("departementNom", model.getDepartementNom());
        context.setVariable("noteId", model.getNoteId());
        context.setVariable("type", model.getType());
        context.setVariable("message", model.getMessage());
        // Ajoute d'autres variables si besoin

        String html = templateEngine.process("emailtemplate.html", context);

        helper.setText(html, true);
        mailSender.send(mimeMessage);
    }
}