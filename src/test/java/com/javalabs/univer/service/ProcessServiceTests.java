package com.javalabs.univer.service;

import com.javalabs.univer.entities.ProcessValueResponse;
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
        ProcessValueResponse answer = new ProcessValueResponse();
        answer.setValue(6);
        answer.setLower(3);
        answer.setHigher(25);

        Mockito.when(cache.containsData(5))
                .thenReturn(false);

        ProcessValueResponse result;
        for (int i = 0; i < 5000; i++)
        {
            result = service.processValue(5);
            Assertions.assertTrue((result.getHigher() > result.getValue())
                    && (result.getLower() < result.getValue()));
        }

        Mockito.when(cache.containsData(5)).thenReturn(true);
        Mockito.when(cache.getData(5)).thenReturn(answer);

        result = service.processValue(5);
        Assertions.assertEquals(result, answer);

    }
}
