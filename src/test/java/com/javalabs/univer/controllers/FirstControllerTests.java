package com.javalabs.univer.controllers;

import com.javalabs.univer.entities.PostInput;
import com.javalabs.univer.entities.PostProcessValueResponse;
import com.javalabs.univer.entities.ProcessValueResponse;
import com.javalabs.univer.exceptions.RequestIOException;
import com.javalabs.univer.exceptions.UncheckedException;
import com.javalabs.univer.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class FirstControllerTests extends Assert {
    private int testValue = 5;
    @Mock
    private ProcessService controllerService;
    @Mock
    private Validator validator;
    @Mock
    private Cache cache;
    @Mock
    private RequestsCounter requestsCounter;
    @Mock
    private DBConnectionService dbConnector;

    @InjectMocks
    private FirstController controller;


    @Test
    public void TestGetDataWithExceptions() throws RequestIOException, RuntimeException
    {
        Mockito.doNothing()
                .when(requestsCounter).addRequest();

        Mockito.doThrow(new RequestIOException("Error"))
                .when(validator).checkInt(-5, 0, 150);
        Mockito.doThrow(new RuntimeException())
                .when(validator).checkInt(-6, 0, 150);

        MockitoAnnotations.initMocks(this);

        Assertions.assertThrows(RequestIOException.class, () -> {
            controller.getData(-5);
        });


        Assertions.assertThrows(UncheckedException.class, () -> {
            controller.getData(-6);
        });

    }

    @Test
    public void TestGetDataWithValue()
    {
        ProcessValueResponse answer = new ProcessValueResponse();
        answer.getResult().setValue(50);
        answer.getResult().setLower(5);
        answer.getResult().setHigher(60);

        Mockito.when(controllerService.processValue(50)).thenReturn(answer);

        Mockito.doNothing()
                .when(requestsCounter).addRequest();

        Mockito.doNothing().when(dbConnector).saveToDB(Mockito.any());

        try {
            ResponseEntity<ProcessValueResponse> result = controller.getData(50);
            Mockito.verify(dbConnector).saveToDB(Mockito.any());
            Assertions.assertTrue((result.getBody().getResult().getHigher() > 50) &&
                    (result.getBody().getResult().getLower() < 50));

        }
        catch (UncheckedException | RequestIOException error)
        {}
    }

    @Test
    public void TestGetRandomizedValues() throws RequestIOException, RuntimeException
    {
        LinkedList<PostInput> input = new LinkedList<>();
        input.add(new PostInput(1));
        input.add(new PostInput(10));
        input.add(new PostInput(-100));

        PostProcessValueResponse result = new PostProcessValueResponse();
        ProcessValueResponse response = new ProcessValueResponse();
        response.getResult().setHigher(10);
        response.getResult().setLower(-10);
        response.getResult().setValue(1);
        Mockito.when(controllerService.processValue(1)).thenReturn(response);
        result.addCalculationsResult(response);
        response = new ProcessValueResponse();
        response.getResult().setHigher(50);
        response.getResult().setLower(-5);
        response.getResult().setValue(10);
        Mockito.when(controllerService.processValue(10)).thenReturn(response);
        result.addCalculationsResult(response);

        Mockito.doNothing().when(validator).checkInt(1, 0, 150);
        Mockito.doNothing().when(validator).checkInt(10, 0, 150);
        Mockito.doThrow(new RequestIOException("Value is lower than 0"))
                .when(validator).checkInt(-100, 0, 150);

        result.addError("Value: -100 Error! Value is lower than 0");

        result.setMinLower(-10);
        result.setMinHigher(10);
        result.setMaxLower(-5);
        result.setMaxHigher(50);
        result.setStatus(HttpStatus.CREATED);

        PostProcessValueResponse responseToTest =  controller.getRandomizedValues(input).getBody();
        Mockito.verify(validator).checkInt(1, 0, 150);
        Mockito.verify(validator).checkInt(10, 0, 150);
        Mockito.verify(validator).checkInt(-100, 0, 150);

        Mockito.verify(controllerService, Mockito.never()).processValue(-100);

        Assertions.assertEquals(responseToTest.getCalculations(), result.getCalculations());
        Assertions.assertEquals(responseToTest.getErrors(), result.getErrors());

        Assertions.assertEquals(responseToTest.getMaxHigher(), result.getMaxHigher());
        Assertions.assertEquals(responseToTest.getMinHigher(), result.getMinHigher());
        Assertions.assertEquals(responseToTest.getMaxLower(), result.getMaxLower());
        Assertions.assertEquals(responseToTest.getMinLower(), result.getMinLower());
        Assertions.assertEquals(responseToTest.getStatus(), result.getStatus());

    }
}
