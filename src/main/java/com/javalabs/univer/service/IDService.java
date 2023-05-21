package com.javalabs.univer.service;

import com.javalabs.univer.entities.CalulationsID;
import com.javalabs.univer.repositories.IDRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IDService {
    private volatile CalulationsID id;
    @Autowired
    private IDRepositiry repositiry;
    @Autowired
    public IDService(IDRepositiry repositiry)
    {
        this.id = new CalulationsID();
        this.repositiry = repositiry;
        Optional<CalulationsID> fromDBOpt = repositiry.findFirstByOrderByIdDesc();
        if (fromDBOpt.isPresent()) this.id.setID(fromDBOpt.get().getID());
    }
    public synchronized Integer createNewID()
    {
        this.id.setID(this.id.getID() + 1);
        repositiry.save(this.id);
        return this.id.getID() - 1;
    }


}
