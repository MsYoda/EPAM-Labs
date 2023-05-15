package com.javalabs.univer.repositories;

import com.javalabs.univer.entities.ProcessValueResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessValueRep extends CrudRepository<ProcessValueResponse, Integer> {
}
