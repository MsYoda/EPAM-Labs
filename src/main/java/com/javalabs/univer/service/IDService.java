package com.javalabs.univer.service;

import com.javalabs.univer.entities.CalulationsID;
import com.javalabs.univer.repositories.IDRepositiry;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        if (fromDBOpt.isPresent()) this.id.set(fromDBOpt.get().get());
    }
    public synchronized Integer createNewID()
    {
        this.id.set(this.id.get() + 1);
        repositiry.save(this.id);
        return this.id.get() - 1;
    }


}
