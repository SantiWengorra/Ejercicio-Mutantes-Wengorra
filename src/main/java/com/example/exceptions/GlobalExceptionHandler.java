package com.example.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> STATUS_BY_EXCEPTION = new HashMap<>();

    static {
        STATUS_BY_EXCEPTION.put(MutanteNoEncontradoException.class, HttpStatus.NOT_FOUND);
        STATUS_BY_EXCEPTION.put(ArgumentoNoValidoException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<Map<String, Object>> processCustomException(
            BaseApiException ex, HttpServletRequest request) {

        return buildErrorResponse(
                ex,
                request.getRequestURI(),
                ex.getMessage()
        );
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            Exception ex, String uri, Object detailMessage) {

        HttpStatus status = STATUS_BY_EXCEPTION.getOrDefault(
                ex.getClass(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        Map<String, Object> body = new HashMap<>();
        body.put("exception", ex.getClass().getName());
        body.put("message", detailMessage);
        body.put("status", status.value());
        body.put("path", uri);
        body.put("error", status.getReasonPhrase());

        return ResponseEntity.status(status).body(body);
    }
}
