package com.javalabs.univer.repositories;

import com.javalabs.univer.entities.CalulationsID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDRepositiry extends CrudRepository<CalulationsID, Integer> {
    Optional<CalulationsID> findFirstByOrderByIdDesc();
}
