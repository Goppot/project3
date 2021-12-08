package com.amr.project.service.email;

import com.amr.project.model.entity.Mail;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public String sendSimpleEmail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("katamentor06@gmail.com");
        message.setTo(mail.getTo());
        message.setText(mail.getMessage());
        message.setSubject(mail.getSubject());
        mailSender.send(message);
        return "Simple mail message Send!!!";
    }

    public String sendEmailWithHTML(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(mail.getSubject(), "UTF-8");
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setFrom("katamentor06@gmail.com");
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setText(mail.getMessage(), true);
            mailSender.send(message);
            return "Mail message with HTML send!!!";
        } catch (MessagingException e) {
            return e.getMessage();
        }
    }
}
