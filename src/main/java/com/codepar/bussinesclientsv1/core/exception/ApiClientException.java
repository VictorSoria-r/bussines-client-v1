package com.codepar.bussinesclientsv1.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Getter
public class ApiClientException extends IOException {

    private String shortMessage;
    public ApiClientException(String message) {
        super(message);
        this.shortMessage = message;
    }

}
