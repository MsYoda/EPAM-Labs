package com.javalabs.univer.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Comparator;

public class ProcessValueResponse extends ServiceResponse{
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Result result;


}
