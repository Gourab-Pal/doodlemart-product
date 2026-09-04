package com.doodlemart.product.catalog.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleProductNotFound(ProductNotFoundException exception) {
        return Map.of(
                "message", "Requested product does not exist",
                "exception", exception.getMessage(),
                "timestamp", OffsetDateTime.now()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleIllegalArgument(IllegalArgumentException exception) {
        return Map.of(
                "message", "Invalid argument passed",
                "exception", exception.getMessage(),
                "timestamp", OffsetDateTime.now()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        return Map.of(
                "message", "Duplicate SKU can not be written in database",
                "exception", exception.getMessage(),
                "timestamp", OffsetDateTime.now()
        );
    }
}
