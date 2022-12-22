package com.caio.cursomc.controller.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError {

    private static final long serialVersionUID = 1L;

    private List<FieldMessage> fieldMessages = new ArrayList<>();

    public ValidationError(Integer status, String message, String timestamp) {
        super(status, message, timestamp);
    }

    public List<FieldMessage> getError() {
        return fieldMessages;
    }

    public void addError(String fieldName, String message) {
        fieldMessages.add(new FieldMessage(fieldName, message));
    }
}
