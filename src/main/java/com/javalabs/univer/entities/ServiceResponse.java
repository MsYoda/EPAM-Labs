package com.javalabs.univer.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

public class ServiceResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public Integer getStatus() {
        return status;
    }

}
