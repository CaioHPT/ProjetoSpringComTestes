package com.caio.cursomc.controller.exceptions;

import com.caio.cursomc.service.exceptions.DataIntegrityException;
import com.caio.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandarError> objectNotFoundException(ObjectNotFoundException exception, HttpServletRequest request){
        StandarError standarError = new StandarError(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(System.currentTimeMillis()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standarError);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandarError> dataIntegrityException(DataIntegrityException exception, HttpServletRequest request){
        StandarError standarError = new StandarError(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(System.currentTimeMillis()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standarError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandarError> methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request){

        ValidationError validationError = new ValidationError(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(System.currentTimeMillis()));

        for(FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
    }
}
