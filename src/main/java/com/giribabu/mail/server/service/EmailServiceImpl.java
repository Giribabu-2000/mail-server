package com.giribabu.mail.server.service;

import com.giribabu.mail.server.exception.CustomException;
import com.giribabu.mail.server.utils.MailServerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSampleMail(String to, String subject, String body) throws Exception {

        if (!MailServerUtils.validateEmail(to)) {
            throw new CustomException("Given mail address is invalid", HttpStatus.BAD_REQUEST);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("Mail Send to " + to);
    }

    @Override
    public void sendSimpleMail(String to, String subject, String body, MultipartFile attachment)
            throws Exception {

        if (!MailServerUtils.validateEmail(to)) {
            throw new CustomException("Given mail address is invalid", HttpStatus.BAD_REQUEST);
        }

        MimeMessage message = getMimeMessage(to, null, subject, body, attachment);
        mailSender.send(message);

        log.info("Mail send : { to - {}, subject - {}, body - {}, attachment - {}}",
                to,
                subject,
                body,
                ((Objects.nonNull(attachment) && !attachment.isEmpty()) ? attachment.getOriginalFilename() : "none")
        );
    }

    @Override
    public void sendBulkMail(String[] toMails, String subject, String body, MultipartFile attachment)
            throws Exception {

        for (String mail : toMails) {
            if (!MailServerUtils.validateEmail(mail)) {
                throw new CustomException("Given mail addresses are invalid", HttpStatus.BAD_REQUEST);
            }
        }

        MimeMessage message = getMimeMessage(null, toMails, subject, body, attachment);
        mailSender.send(message);

        log.info("Bulk Mail send : { to - {}, subject - {}, body - {}, attachment - {}}",
                Arrays.toString(toMails),
                subject,
                body,
                ((Objects.nonNull(attachment) && !attachment.isEmpty()) ? attachment.getOriginalFilename() : "none")
        );
    }

    private MimeMessage getMimeMessage(String to, String[] toMails, String subject, String body, MultipartFile attachment)
            throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        if (Objects.nonNull(to)) {
            helper.setTo(to);
        } else {
            helper.setTo(toMails);
        }

        helper.setSubject(subject);
        helper.setText(body);

        if (Objects.nonNull(attachment) && !attachment.isEmpty()) {
            helper.addAttachment(
                    Objects.requireNonNull(attachment.getOriginalFilename()),
                    attachment,
                    Objects.requireNonNull(attachment.getContentType())
            );
        }

        return message;
    }
}
