package com.don.mailservice.service;

import com.don.mailservice.model.Mail;

public interface MailService {
    String sendMail(Mail mail);
}
