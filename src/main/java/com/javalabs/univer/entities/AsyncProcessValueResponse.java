package com.javalabs.univer.entities;

public class AsyncProcessValueResponse extends ServiceResponse{

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
    public AsyncProcessValueResponse()
    {
        id = 0;
    }
}
