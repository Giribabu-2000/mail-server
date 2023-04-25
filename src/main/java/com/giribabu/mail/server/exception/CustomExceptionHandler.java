package com.giribabu.mail.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    private static final String STATUS_KEY = "status";
    private static final String ERROR_KEY = "error";
    private static final String FAILED_MESSAGE = "Mail Send Failed";

    private static Map<String, String> errorMap;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("MissingServletRequestParameterException Occurred : {}", e.getMessage());
        errorMap = new LinkedHashMap<>();
        errorMap.put(STATUS_KEY, FAILED_MESSAGE);
        errorMap.put(ERROR_KEY, e.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomException e) {
        log.warn("CustomException Occurred : {}", e.getMessage());
        errorMap = new LinkedHashMap<>();
        errorMap.put(STATUS_KEY, FAILED_MESSAGE);
        errorMap.put(ERROR_KEY, e.getMessage());
        return new ResponseEntity<>(errorMap, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        log.warn("Exception Occurred : {}", e.getMessage());
        errorMap = new LinkedHashMap<>();
        errorMap.put(STATUS_KEY, FAILED_MESSAGE);
        errorMap.put(ERROR_KEY, e.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
