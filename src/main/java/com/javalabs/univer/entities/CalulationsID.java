package com.javalabs.univer.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "calcs_id")
public class CalulationsID {
    public CalulationsID(Integer id)
    {
        this.id = id;
    }
    public CalulationsID()
    {
        this.id = 0;
    }
    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }
    @Id
    private Integer id;
}
