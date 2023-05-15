package com.javalabs.univer.service;

import com.javalabs.univer.entities.ProcessValueResponse;
import com.javalabs.univer.repositories.ProcessValueRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBConnectionService {
    @Autowired
    private ProcessValueRep responseRepository;

    public void saveToDB(ProcessValueResponse entity)
    {
        responseRepository.save(entity);
    }

    public ProcessValueResponse getFromDB(Integer key)
    {
        if (responseRepository.existsById(key)) return responseRepository.findById(key).get();
        return null;
    }

    public Boolean valueExist(Integer key)
    {
        return responseRepository.existsById(key);
    }

}
