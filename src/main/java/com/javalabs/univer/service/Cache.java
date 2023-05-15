package com.javalabs.univer.service;

import com.javalabs.univer.entities.ProcessValueResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Cache {
    private HashMap<Integer, ProcessValueResponse> cacheMap;
    public Cache()
    {
        cacheMap = new HashMap<>();
    }
    public ProcessValueResponse getData(int value)
    {
        return cacheMap.get(value);
    }

    public Boolean containsData(int value)
    {
        return cacheMap.containsKey(value);
    }

    public void addData(int value, ProcessValueResponse data)
    {
        cacheMap.put(value, data);
    }

    public HashMap<Integer, ProcessValueResponse> getAllData()
    {
        return cacheMap;
    }
}
