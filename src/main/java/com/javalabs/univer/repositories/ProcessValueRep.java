package com.javalabs.univer.repositories;

import com.javalabs.univer.entities.ProcessValueResponse;
import com.javalabs.univer.entities.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessValueRep extends CrudRepository<Result, Integer> {
}
