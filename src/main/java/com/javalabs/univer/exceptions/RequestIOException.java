package com.javalabs.univer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequestIOException extends IOException {

    public RequestIOException(String message)
    {
        super(message);
    }

}
