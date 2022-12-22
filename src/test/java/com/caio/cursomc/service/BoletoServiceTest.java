package com.caio.cursomc.service;

import com.caio.cursomc.model.PagamentoBoleto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class BoletoServiceTest {

    @InjectMocks
    private BoletoService boletoService;

    @Test
    @DisplayName("Fill out payment with boleto when sucessful")
    void preencherPagamentoComBoleto_WhenSucessful(){
        Assertions.assertThatCode(() -> boletoService.preencherPagamentoComBoleto(new PagamentoBoleto(), new Date()))
                .doesNotThrowAnyException();
    }
}
