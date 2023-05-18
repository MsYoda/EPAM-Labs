package com.javalabs.univer.service;

import com.javalabs.univer.entities.ProcessValueResponse;
import com.javalabs.univer.entities.Result;
import com.javalabs.univer.repositories.ProcessValueRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DBConnectionService {
    @Autowired
    private ProcessValueRep responseRepository;

    public void saveToDB(Result entity)
    {
        responseRepository.save(entity);
    }

    public Optional<Result> getFromDB(Integer key)
    {
        Optional<Result> result = Optional.empty();
        if (responseRepository.existsById(key))
        {
            return responseRepository.findById(key);
        }
        return result;
    }

    public Boolean valueExist(Integer key)
    {
        return responseRepository.existsById(key);
    }

}
