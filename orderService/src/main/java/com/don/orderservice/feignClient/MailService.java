package com.don.orderservice.feignClient;

import com.don.orderservice.model.mail.Mail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-service", path = "/mails")
public interface MailService {
    @PostMapping("/sendMail")
    String sendEmail(@RequestBody Mail mail);
}
