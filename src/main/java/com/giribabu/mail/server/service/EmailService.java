package com.giribabu.mail.server.service;

import org.springframework.web.multipart.MultipartFile;

public interface EmailService {

    void sendSimpleMail(String to, String subject, String body, MultipartFile attachment)
            throws Exception;

    void sendBulkMail(String[] toMails, String subject, String body, MultipartFile attachment)
            throws Exception;
}
