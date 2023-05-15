package com.javalabs.univer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UncheckedException extends Throwable{
    public UncheckedException(String message)
    {
        super(message);
       // System.out.print(message + '\n');
    }
}
