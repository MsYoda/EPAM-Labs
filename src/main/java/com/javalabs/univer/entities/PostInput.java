package com.javalabs.univer.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostInput
{

    public PostInput(@JsonProperty("value") Integer value)
    {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer value;

}
