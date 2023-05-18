package com.javalabs.univer.controllers;

import com.javalabs.univer.entities.*;
import com.javalabs.univer.exceptions.RequestIOException;
import com.javalabs.univer.service.*;
import com.javalabs.univer.exceptions.UncheckedException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
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

    private final IDService idService;
    private final Logger logger = LogManager.getLogger("ControllerLogger");

    @Autowired
    public FirstController(Validator validator, ProcessService service, RequestsCounter requestsCounter, Cache cache, DBConnectionService connector, IDService idService)
    {
        this.validator = validator;
        this.service = service;
        this.requestsCounter = requestsCounter;
        this.cache = cache;
        this.connectorService = connector;
        this.idService = idService;
    }


   /* @GetMapping("/random")
    public ResponseEntity<ProcessValueResponse> getData(@RequestParam int value) throws RequestIOException, UncheckedException {
        try {
            ProcessValueResponse result;

            requestsCounter.addRequest();

            logger.log(Level.INFO, "getData() Checking value = " + value);

            validator.checkInt(value, 0, 150);

            logger.log(Level.INFO, "getData() Trying to process value = " + value);
           // result = service.processValue(value);

           // connectorService.saveToDB(result.getResult());

            logger.log(Level.INFO, "getData() Proccessing sucseed!" );
           // result.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch (Error | RuntimeException error)
        {
            logger.log(Level.ERROR, "getData() Runtime error in controller!");
            throw new UncheckedException("getData() Crirtical Unchecked Error!");
        }
    }*/

    @Async
    @GetMapping("/randomAsync")
    public CompletableFuture<AsyncProcessValueResponse> getDataAsync(@RequestParam int value)  throws RequestIOException, UncheckedException{
        AsyncProcessValueResponse response = new AsyncProcessValueResponse();
        try {
            validator.checkInt(value, 0, 150);
            Integer id = idService.createNewID();
            this.processValue(value, id);
            response.setId(id);
        }
        catch (Error | RuntimeException error)
        {
            response.setError("Critical error!! ");
            logger.log(Level.ERROR, "getData() Runtime error in controller!");
            throw new UncheckedException("getData() Crirtical Unchecked Error!");
        }
        return CompletableFuture.completedFuture(response);
    }

    private void processValue(int value, int id)
    {
        logger.log(Level.INFO, "getData() Trying to process value = " + value);
        Result calcs = service.processValue(value);

        calcs.setId(id);
        connectorService.saveToDB(calcs);
    }
    @GetMapping("/allcache")
    public ResponseEntity<RandomValuesList> getAllCache() {
        RandomValuesList list = new RandomValuesList();
        list.setValues(cache.getAllData());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/cache")
    public ResponseEntity<ProcessValueResponse> getCache(@RequestParam int value ) {

        return new ResponseEntity<>(cache.getData(value), HttpStatus.OK);
    }

    @GetMapping("/dbget")
    public ResponseEntity<ProcessValueResponse> getDataFromDB(@RequestParam int id ) {
        ProcessValueResponse response = new ProcessValueResponse();
        Optional<Result> result = connectorService.getFromDB(id);
        HttpStatus status = HttpStatus.OK;
        if (result.isPresent())
        {
            response.setResult(result.get());
            response.setStatus(HttpStatus.OK);
        }
        else {
            response.setError("Cant find such element in DB");
            response.setStatus(HttpStatus.BAD_REQUEST);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
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

                Result resultCalcs = service.processValue(currentElement.getValue());
                resultCalcs.setId(idService.createNewID());
                connectorService.saveToDB(resultCalcs);
                result.addCalculationsResult(resultCalcs);
            }
            catch (Error | RuntimeException | RequestIOException except)
            {
                errors.set(true);
                logger.log(Level.ERROR, "Error in post endpoint!" + except.getMessage());
                result.addError("Value: " + Integer.toString(currentElement.getValue()) + " Error! " + except.getMessage());
            }
        });

        result.setMinLower(result.getCalculations().stream().map(Result::getLower).min(Integer::compareTo).get());
        result.setMinHigher(result.getCalculations().stream().map(Result::getHigher).min(Integer::compareTo).get());

        result.setMaxLower(result.getCalculations().stream().map(Result::getLower).max(Integer::compareTo).get());
        result.setMaxHigher(result.getCalculations().stream().map(Result::getHigher).max(Integer::compareTo).get());

        if(errors.get()) resultStatus = HttpStatus.CREATED;

        result.setStatus(resultStatus);

        return new ResponseEntity<>(result, resultStatus);
    }

}


