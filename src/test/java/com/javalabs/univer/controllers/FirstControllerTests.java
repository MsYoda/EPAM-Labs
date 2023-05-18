package com.javalabs.univer.controllers;

import com.javalabs.univer.entities.*;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @Mock
    private IDService idService;

    @InjectMocks
    private FirstController controller;

    @Test
    public void TestGetDataAsyncWithExceptions() throws RequestIOException {
        Mockito.doThrow(RequestIOException.class).when(validator).checkInt(-1, 0, 150);
        Mockito.doThrow(NullPointerException.class).when(validator).checkInt(-10, 0, 150);

        assertThrows(RequestIOException.class, () ->{
                controller.getDataAsync(-1);
        });

        assertThrows(UncheckedException.class, () ->{
            controller.getDataAsync(-10);
        });
    }

    @Test
    public void TestGetDataAsyncWithCorrectValue() throws UncheckedException, RequestIOException, ExecutionException, InterruptedException {
        Mockito.when(idService.createNewID()).thenReturn(1);
        Mockito.when(controllerService.processValue(1)).thenReturn(new Result());
        CompletableFuture<AsyncProcessValueResponse> response = controller.getDataAsync(1);
        assertEquals(response.get().getId(), 1);

    }

    @Test
    public void TestGetRandomizedValues() throws RequestIOException, RuntimeException
    {
        LinkedList<PostInput> input = new LinkedList<>();
        input.add(new PostInput(1));
        input.add(new PostInput(10));
        input.add(new PostInput(-100));

        PostProcessValueResponse result = new PostProcessValueResponse();
        Result response = new Result();
        response.setHigher(10);
        response.setLower(-10);
        response.setValue(1);
        Mockito.when(controllerService.processValue(1)).thenReturn(response);
        result.addCalculationsResult(response);
        response = new Result();
        response.setHigher(50);
        response.setLower(-5);
        response.setValue(10);
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
