package com.javalabs.univer.service;

import com.javalabs.univer.exceptions.RequestIOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private Logger log = LogManager.getLogger("ValidatorLogger");
    public void checkInt(int value, int lBoard, int rBoard) throws RequestIOException
    {
        if (value < lBoard) {
            log.log(Level.ERROR, "checkInt() Error while checking value! It must be greater than " + lBoard);
            throw new RequestIOException("Value is lower than " + lBoard);
        }
        if (value > rBoard)
        {
            log.log(Level.ERROR, "checkInt() Error while checking value! It must be lower than " + rBoard);
            throw new RequestIOException("Value is greater than " + rBoard);
        }
    }
}
