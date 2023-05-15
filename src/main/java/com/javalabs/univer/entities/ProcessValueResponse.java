package com.javalabs.univer.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Comparator;

@Entity
public class ProcessValueResponse extends ServiceResponse{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer higher;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer lower;
    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer value;

    static public Comparator<ProcessValueResponse> lowerComparator = new Comparator<ProcessValueResponse>() {
        @Override
        public int compare(ProcessValueResponse o1, ProcessValueResponse o2) {
            return o1.getLower().compareTo(o2.getLower());
        }
    };

    static public Comparator<ProcessValueResponse> higherComparator = new Comparator<ProcessValueResponse>() {
        @Override
        public int compare(ProcessValueResponse o1, ProcessValueResponse o2) {
            return o1.getHigher().compareTo(o2.getHigher());
        }
    };

    public Integer getHigher() {
        return higher;
    }

    public Integer getLower() {
        return lower;
    }

    public Integer getValue() {
        return value;
    }

    public void setHigher(Integer higher) {
        this.higher = higher;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
