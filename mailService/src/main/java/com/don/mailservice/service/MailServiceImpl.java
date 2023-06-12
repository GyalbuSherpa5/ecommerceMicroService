package com.don.mailservice.service;

import com.don.mailservice.model.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;
    @Override
    public String sendMail(Mail mail) {
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setCc(mail.getCc());
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mail.getBody());

            mailSender.send(mimeMailMessage);

            return "mail send successfully";

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
