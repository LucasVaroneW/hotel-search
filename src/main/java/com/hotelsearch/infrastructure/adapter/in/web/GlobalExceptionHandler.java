package com.hotelsearch.infrastructure.adapter.in.web;

import com.hotelsearch.domain.exception.BusinessRuleException;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessRuleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBusinessRule(BusinessRuleException ex) {
        return Map.of("error", ex.getMessage());
    }
}
