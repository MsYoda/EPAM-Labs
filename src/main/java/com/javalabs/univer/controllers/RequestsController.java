package com.javalabs.univer.controllers;

import com.javalabs.univer.entities.RequestCounterResponse;
import com.javalabs.univer.service.RequestsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lab")
public class RequestsController {
    @Autowired
    private RequestsCounter requestsCounter;
    @GetMapping("/requests")
    public ResponseEntity<RequestCounterResponse> getCountOfRequests()
    {
        RequestCounterResponse response = new RequestCounterResponse();
        response.setCount(requestsCounter.getCountOfRequests());
        response.setSimpleCount(requestsCounter.simpleGet());
        response.setAtomicCounter(requestsCounter.getAtomicCounter().intValue());
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/simpleRequests")
    public ResponseEntity<RequestCounterResponse> getCountOfSimpleRequests()
    {
        RequestCounterResponse response = new RequestCounterResponse();
        response.setCount(requestsCounter.simpleGet());
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/clearrequests")
    public void clearRequests()
    {
        requestsCounter.clearCounters();
    }

}
