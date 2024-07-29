package com.codepar.bussinesclientsv1.core.advice;

import com.codepar.bussinesclientsv1.core.exception.CoderPadException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCoderPadException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse();
        HttpStatus finalStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if  (ex instanceof CoderPadException coderPadException) {
            response.setMessage(coderPadException.getMessage());
            finalStatus = coderPadException.getStatus();
        }
        if  (ex instanceof CallNotPermittedException callNotPermittedException) {
            response.setMessage("Tenemos incovenientes con nuestro servicio");
            finalStatus = HttpStatus.FAILED_DEPENDENCY;
        }

        return new ResponseEntity<>(response,finalStatus);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleCoderPadException(IOException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        HttpStatus finalStatus = HttpStatus.INTERNAL_SERVER_ERROR;


        return new ResponseEntity<>(response,finalStatus);
    }
}
