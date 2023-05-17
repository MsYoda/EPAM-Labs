package com.javalabs.univer.controllers;

import com.javalabs.univer.entities.*;
import com.javalabs.univer.exceptions.RequestIOException;
import com.javalabs.univer.repositories.ProcessValueRep;
import com.javalabs.univer.service.*;
import com.javalabs.univer.exceptions.UncheckedException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/api/lab")
public class FirstController
{
    private final ProcessService service;
    private final Validator validator;
    private final RequestsCounter requestsCounter;
    private final Cache cache;
    private final DBConnectionService connectorService;
    private final Logger logger = LogManager.getLogger("ControllerLogger");

    @Autowired
    public FirstController(Validator validator, ProcessService service, RequestsCounter requestsCounter, Cache cache, DBConnectionService connector)
    {
        this.validator = validator;
        this.service = service;
        this.requestsCounter = requestsCounter;
        this.cache = cache;
        this.connectorService = connector;
    }


    @GetMapping("/random")
    public ResponseEntity<ProcessValueResponse> getData(@RequestParam int value) throws RequestIOException, UncheckedException {
        try {
            ProcessValueResponse result;

            requestsCounter.addRequest();

            logger.log(Level.INFO, "getData() Checking value = " + value);

            validator.checkInt(value, 0, 150);

            logger.log(Level.INFO, "getData() Trying to process value = " + value);
            result = service.processValue(value);
            connectorService.saveToDB(result.getResult());

            logger.log(Level.INFO, "getData() Proccessing sucseed!" );
            result.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Error | RuntimeException error)
        {
            logger.log(Level.ERROR, "getData() Runtime error in controller!");
            throw new UncheckedException("getData() Crirtical Unchecked Error!");
        }
    }

    @GetMapping("/allcache")
    public ResponseEntity<RandomValuesList> getAllCache() {
        RandomValuesList list = new RandomValuesList();
        list.setValues(cache.getAllData());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/dbtest")
    public ResponseEntity<ProcessValueResponse> getAllCache(@RequestParam int value) {
        Result response = connectorService.getFromDB(value);
        ProcessValueResponse result = new ProcessValueResponse() ;
        result.setResult(response);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/cache")
    public ResponseEntity<ProcessValueResponse> getCache(@RequestParam int value ) {

        return new ResponseEntity<>(cache.getData(value), HttpStatus.OK);
    }


    @PostMapping("/randomizer")
    public ResponseEntity<PostProcessValueResponse> getRandomizedValues(@RequestBody LinkedList<PostInput> bodyList) throws RequestIOException
    {
        AtomicBoolean errors = new AtomicBoolean(false);
        HttpStatus resultStatus = HttpStatus.OK;

        PostProcessValueResponse result = new PostProcessValueResponse();
        bodyList.forEach((currentElement)-> {
            try {
                validator.checkInt(currentElement.getValue(), 0, 150);
                ProcessValueResponse serviceResult = service.processValue(currentElement.getValue());
                connectorService.saveToDB(serviceResult.getResult());
                result.addCalculationsResult(serviceResult);
            }
            catch (Error | RuntimeException | RequestIOException except)
            {
                errors.set(true);
                logger.log(Level.ERROR, "Error in post endpoint!" + except.getMessage());
                result.addError("Value: " + Integer.toString(currentElement.getValue()) + " Error! " + except.getMessage());
            }
        });

        result.setMinLower(result.getCalculations().stream().min(ProcessValueResponse.lowerComparator).get().getResult().getLower());
        result.setMinHigher(result.getCalculations().stream().min(ProcessValueResponse.higherComparator).get().getResult().getHigher());

        //result.setMaxLower(result.getCalculations().stream().max(ProcessValueResponse.lowerComparator).get().getLower());
        result.setMaxLower(result.getCalculations().stream().map(x -> x.getResult().getLower()).max(Integer::compareTo).get());
        result.setMaxHigher(result.getCalculations().stream().max(ProcessValueResponse.higherComparator).get().getResult().getHigher());

        if(errors.get()) resultStatus = HttpStatus.CREATED;

        result.setStatus(resultStatus);

        return new ResponseEntity<>(result, resultStatus);
    }

}


