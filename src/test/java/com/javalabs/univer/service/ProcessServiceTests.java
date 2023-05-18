package com.javalabs.univer.service;

import com.javalabs.univer.entities.ProcessValueResponse;
import com.javalabs.univer.entities.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProcessServiceTests {
    @Mock
    private Cache cache;

    @InjectMocks
    private ProcessService service;

    @Test
    public void TestProcessValueWithValue5()
    {
        Result answer = new Result();
        answer.setValue(6);
        answer.setLower(3);
        answer.setHigher(25);

        Result result;
        for (int i = 0; i < 5000; i++)
        {
            result = service.processValue(5);
            Assertions.assertTrue((result.getHigher() > result.getValue())
                    && (result.getLower() < result.getValue()));
        }


    }
}
