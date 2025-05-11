package com.company.company.exceptions;

import com.company.company.dto.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(Exception e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }

    @ExceptionHandler(EmptyValueException.class)
    public ResponseEntity threatEmptyValue(EmptyValueException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), "400");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
