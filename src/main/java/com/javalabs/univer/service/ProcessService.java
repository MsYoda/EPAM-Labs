package com.javalabs.univer.service;

import com.javalabs.univer.entities.ProcessValueResponse;
import com.javalabs.univer.repositories.ProcessValueRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {
    private Cache cache ;
    @Autowired
    public ProcessService(Cache cache, DBConnectionService rep)
    {
        this.cache = cache;
    }
    public ProcessValueResponse processValue(int value)
    {
        if (cache.containsData(value))
        {
            return cache.getData(value);
        }

        ProcessValueResponse result = new ProcessValueResponse();
        result.setValue(value);

        int number = (int) ((Math.random() * ((value - 1) - ((value - 1) - 100))) + ((value - 1) - 100));
        result.setLower(number);

        number = (int) ((Math.random() * (((value + 1) + 100) - (value + 1))) + (value + 1));
        result.setHigher(number);
        cache.addData(value, result);
        return result;
    }

}
