package com.don.mailservice.controller;

import com.don.mailservice.model.Mail;
import com.don.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mails")
public class MailController {

    private final MailService emailService;

    @PostMapping("/sendMail")
    public String sendEmail(@RequestBody Mail mail){
        return emailService.sendMail(mail);
    }
}
