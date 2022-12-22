package com.caio.cursomc.controller.exceptions;

import com.caio.cursomc.service.exceptions.DataIntegrityException;
import com.caio.cursomc.service.exceptions.ObjectNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
public class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @Test
    void objectNotFoundException() {
        ResponseEntity<StandarError> standarError = controllerExceptionHandler
                .objectNotFoundException(new ObjectNotFoundException("Objeto nao encontrado"),
                        new MockHttpServletRequest());

        Assertions.assertThat(standarError).isNotNull();

        Assertions.assertThat(standarError.getBody()).isNotNull();

        Assertions.assertThat(standarError.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);

        Assertions.assertThat(standarError.getBody().getMessage()).isNotNull().isEqualTo("Objeto nao encontrado");

        Assertions.assertThat(standarError.getBody().getStatus()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND.value());

        Assertions.assertThat(standarError.getBody().getTimestamp()).isNotNull();

        Assertions.assertThat(standarError.getBody().getClass()).isEqualTo(StandarError.class);

        Assertions.assertThat(standarError.getClass()).isEqualTo(ResponseEntity.class);
    }

    @Test
    void dataIntegrityException() {
        ResponseEntity<StandarError> standarError = controllerExceptionHandler
                .dataIntegrityException(new DataIntegrityException("Erro ao deletar, existem itens associados"),
                        new MockHttpServletRequest());

        Assertions.assertThat(standarError).isNotNull();

        Assertions.assertThat(standarError.getBody()).isNotNull();

        Assertions.assertThat(standarError.getStatusCode()).isNotNull().isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertThat(standarError.getBody().getMessage()).isNotNull().isEqualTo("Erro ao deletar, existem itens associados");

        Assertions.assertThat(standarError.getBody().getStatus()).isNotNull().isEqualTo(HttpStatus.BAD_REQUEST.value());

        Assertions.assertThat(standarError.getBody().getTimestamp()).isNotNull();

        Assertions.assertThat(standarError.getBody().getClass()).isEqualTo(StandarError.class);

        Assertions.assertThat(standarError.getClass()).isEqualTo(ResponseEntity.class);
    }

    @Test
    void methodArgumentNotValidException() {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        MethodParameter parameter = Mockito.mock(MethodParameter.class);

        BDDMockito.when(parameter.getMethod()).thenReturn(method);

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        BDDMockito.when(bindingResult.getAllErrors()).thenReturn(new ArrayList<>());

        ResponseEntity<StandarError> standarError = controllerExceptionHandler
                .methodArgumentNotValidException(new MethodArgumentNotValidException(parameter, bindingResult),
                        new MockHttpServletRequest());

        Assertions.assertThat(standarError).isNotNull();

        Assertions.assertThat(standarError.getStatusCode()).isNotNull().isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertThat(standarError.getBody()).isNotNull();

        Assertions.assertThat(standarError.getBody().getMessage()).isNotNull().isEqualTo("Erro de validação");

        Assertions.assertThat(standarError.getBody().getStatus()).isNotNull().isEqualTo(HttpStatus.BAD_REQUEST.value());

        Assertions.assertThat(standarError.getBody().getTimestamp()).isNotNull();

        Assertions.assertThat(standarError.getBody().getClass()).isEqualTo(ValidationError.class);

        Assertions.assertThat(standarError.getClass()).isEqualTo(ResponseEntity.class);
    }
}