package com.don.orderservice.service;

public interface EmailService {
    String sendMail(String to, String[] cc, String subject, String body);
}
