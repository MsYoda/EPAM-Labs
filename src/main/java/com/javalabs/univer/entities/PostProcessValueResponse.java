package com.javalabs.univer.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.LinkedList;
import java.util.Optional;

public class PostProcessValueResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedList<ProcessValueResponse> calculations = new LinkedList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedList<String> errors = new LinkedList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minLower;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxLower;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minHigher;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxHigher;

    public Integer getMinLower() {
        return minLower;
    }

    public void setMinLower(Integer minLower) {
        this.minLower = minLower;
    }

    public Integer getMaxLower() {
        return maxLower;
    }

    public void setMaxLower(Integer maxLower) {
        this.maxLower = maxLower;
    }

    public Integer getMinHigher() {
        return minHigher;
    }

    public void setMinHigher(Integer minHigher) {
        this.minHigher = minHigher;
    }

    public Integer getMaxHigher() {
        return maxHigher;
    }

    public void setMaxHigher(Integer maxHigher) {
        this.maxHigher = maxHigher;
    }

    public void addCalculationsResult(ProcessValueResponse result)
    {
        calculations.add(result);
    }

    public void addError(String error)
    {
        errors.add(error);
    }
    public LinkedList<ProcessValueResponse> getCalculations() {
        return calculations;
    }

    public LinkedList<String> getErrors() {
        return errors;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public Integer getStatus() {
        return status;
    }
}
