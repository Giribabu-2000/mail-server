package com.giribabu.mail.server.controller;

import com.giribabu.mail.server.service.EmailService;
import com.giribabu.mail.server.utils.MailServerUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/v1/mail-server")
@Slf4j
public class MailController {

    private final EmailService emailService;

    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/send/mail", consumes = "multipart/form-data")
    @ApiOperation(value = "Send Single Mail",
            notes = "With the use of this api, an email can be sent, with or without attachments, to a single recipient.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Mail Send Successfully"),
            @ApiResponse(code = 400, message = "Invalid Request Inputs"),
            @ApiResponse(code = 500, message = "Mail Send Failed")
    })
    public ResponseEntity<Map<String, String>> sendSimpleMailWithAttachment(@RequestParam("to") String to,
                                                                            @RequestParam("subject") String subject,
                                                                            @RequestParam("body") String body,
                                                                            @RequestParam("attachment") @Nullable MultipartFile attachment) throws Exception {
        emailService.sendSimpleMail(to, subject, body, attachment);
        return MailServerUtils.sendResponse("Mail Send...!");
    }

    @PostMapping(value = "/send/bulk-mail", consumes = "multipart/form-data")
    @ApiOperation(value = "Send Bulk Mail",
            notes = "With the use of this api, an email can be sent, with or without attachments, to a multiple recipient.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Bulk Mail Send Successfully"),
            @ApiResponse(code = 400, message = "Invalid Request Inputs"),
            @ApiResponse(code = 500, message = "Bulk Mail Send Failed")
    })
    public ResponseEntity<Map<String, String>> sendBulkMail(@RequestParam("toMails") String[] toMails,
                                                            @RequestParam("subject") String subject,
                                                            @RequestParam("body") String body,
                                                            @RequestParam("attachment") @Nullable MultipartFile attachment) throws Exception {
        emailService.sendBulkMail(toMails, subject, body, attachment);
        return MailServerUtils.sendResponse("Bulk Mail Send...!");
    }

}
