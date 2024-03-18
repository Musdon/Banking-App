package com.musdon.bankingapp.exception;

import com.musdon.bankingapp.payload.ErrorResponse;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDuplicateDataError(DuplicateRequestException exception, WebRequest webRequest){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Duplicate data found")
                .url(webRequest.getContextPath())
                .timeStamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
