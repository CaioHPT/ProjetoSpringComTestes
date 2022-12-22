package com.caio.cursomc.controller.exceptions;

import java.io.Serializable;

public class StandarError implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status;
    private String message;
    private String timestamp;

    public StandarError(Integer status, String message, String timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
