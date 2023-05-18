package com.javalabs.univer.entities;

import jakarta.persistence.*;

@Entity
public class Result {
    private Integer value;
    private Integer higher;
    private Integer lower;

    @Id
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getHigher() {
        return higher;
    }

    public void setHigher(Integer higher) {
        this.higher = higher;
    }

    public Integer getLower() {
        return lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }


}
