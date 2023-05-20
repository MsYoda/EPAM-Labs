package com.javalabs.univer.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AsyncProcessValueResponse extends ServiceResponse{

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    public AsyncProcessValueResponse()
    {
       // id = 0;
    }
}
