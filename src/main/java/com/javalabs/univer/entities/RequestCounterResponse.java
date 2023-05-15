package com.javalabs.univer.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

public class RequestCounterResponse extends ServiceResponse{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer simpleCount;

    public Integer getAtomicCounter() {
        return atomicCounter;
    }

    public void setAtomicCounter(Integer atomicCounter) {
        this.atomicCounter = atomicCounter;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer atomicCounter;
    public Integer getSimpleCount() {
        return simpleCount;
    }

    public void setSimpleCount(Integer simpleCount) {
        this.simpleCount = simpleCount;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }


}
