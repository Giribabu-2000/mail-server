package com.giribabu.mail.server.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MailServerUtils {

    private static final String STATUS_KEY = "status";
    private static final String EMAIL_VALIDATOR = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)"
            + "*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)"
            + "*(\\.[A-Za-z]{2,})$";

    public static Boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_VALIDATOR);
        return email != null && !email.equals("") && pattern.matcher(email).matches();
    }

    public static ResponseEntity<Map<String, String>> sendResponse(String message) {
        Map<String, String> customResponse = new LinkedHashMap<>();
        customResponse.put(STATUS_KEY, message);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
}
