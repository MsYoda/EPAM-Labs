package com.javalabs.univer.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.LinkedList;

public class RandomValuesList {
    public LinkedList<Integer> getValuesList() {
        return valuesList;
    }

    public void setValuesList(LinkedList<Integer> valuesList) {
        this.valuesList = valuesList;
    }


    public HashMap<Integer, ProcessValueResponse> getValues() {
        return values;
    }

    public void setValues(HashMap<Integer, ProcessValueResponse> values) {
        this.values = values;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedList<Integer> valuesList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HashMap<Integer, ProcessValueResponse> values;

}
