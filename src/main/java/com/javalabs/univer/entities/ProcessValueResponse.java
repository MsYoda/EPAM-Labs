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

    static public Comparator<ProcessValueResponse> lowerComparator = new Comparator<ProcessValueResponse>() {
        @Override
        public int compare(ProcessValueResponse o1, ProcessValueResponse o2) {
            return o1.getResult().getLower().compareTo(o2.getResult().getLower());
        }
    };

    static public Comparator<ProcessValueResponse> higherComparator = new Comparator<ProcessValueResponse>() {
        @Override
        public int compare(ProcessValueResponse o1, ProcessValueResponse o2) {
            return o1.getResult().getHigher().compareTo(o2.getResult().getHigher());
        }
    };
    public ProcessValueResponse()
    {
        result = new Result();
    }

}
