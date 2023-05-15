package com.javalabs.univer.exceptions;

import com.javalabs.univer.entities.ProcessValueResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(RequestIOException.class)
    public ResponseEntity<ProcessValueResponse> valueParamHandler(RequestIOException ex)
    {
        ProcessValueResponse response = new ProcessValueResponse();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setError(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UncheckedException.class)
    public ResponseEntity<ProcessValueResponse> valueParamHandler(UncheckedException ex)
    {
        ProcessValueResponse response = new ProcessValueResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setError(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProcessValueResponse> valueParamHandler(MethodArgumentTypeMismatchException ex)
    {
        ProcessValueResponse response = new ProcessValueResponse();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setError("Неверный тип данных, ожидалось число");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
