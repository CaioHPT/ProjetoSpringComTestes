package com.caio.cursomc.controller;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Estado;
import com.caio.cursomc.service.EstadoService;
import com.caio.cursomc.service.exceptions.ObjectNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class EstadoControllerTest {

    private Estado estado;
    private Cidade cidade;

    @InjectMocks
    private EstadoController estadoController;

    @Mock
    private EstadoService estadoService;

    private static final String NAME_STATE = "São Paulo";
    private static final String NAME_CITY = "Vinhedo";

    @BeforeEach
    void setUp(){
        startEntitys();

        List<Estado> estados = Arrays.asList(estado);

        PageImpl<Estado> estadoPage = new PageImpl<>(Arrays.asList(estado));

        BDDMockito.when(estadoService.findAll()).thenReturn(estados);

        BDDMockito.when(estadoService.findById(ArgumentMatchers.anyLong())).thenReturn(estado);

        BDDMockito.when(estadoService.findPage(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()))
                .thenReturn(estadoPage);

        BDDMockito.when(estadoService.save(ArgumentMatchers.any(Estado.class))).thenReturn(estado);

        BDDMockito.when(estadoService.update(ArgumentMatchers.any(Estado.class))).thenReturn(estado);

        BDDMockito.doNothing().when(estadoService).delete(1L);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    @DisplayName("List return of estados when Sucessful")
    void list_ReturnEstados_WhenSucessful(){
        ResponseEntity<List<Estado>> estados = estadoController.findAll();

        Assertions.assertThat(estados).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(estados.getClass());

        Assertions.assertThat(estados.getBody()).isNotNull();

        Assertions.assertThat(estados.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(estados.getBody().get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(estados.getBody().get(0).getNome()).isNotNull().isEqualTo(NAME_STATE);

        Assertions.assertThat(estados.getBody().get(0).getCidades()).isNotNull().isNotEmpty();

        Assertions.assertThat(estados.getBody().get(0).getClass()).isEqualTo(Estado.class);

        Assertions.assertThat(estados.getBody().isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(estadoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Estado>> estados = estadoController.findAll();

        Assertions.assertThat(estados.getBody()).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(estados.getBody().isEmpty()).isTrue();

        Assertions.assertThat(estados).isNotNull();

        Assertions.assertThat(estados.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(ResponseEntity.class).isEqualTo(estados.getClass());

    }

    @Test
    @DisplayName("page return estados when Sucessful")
    void page_ReturnEstados_WhenSucessful(){
        ResponseEntity<Page<Estado>> estadoPage = estadoController.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(estadoPage).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(estadoPage.getClass());

        Assertions.assertThat(estadoPage.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(estadoPage.getBody()).isNotNull();

        Assertions.assertThat(estadoPage.getBody().get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(estadoPage.getBody().get().count() == 1).isTrue();

        Assertions.assertThat(estadoPage.getBody().get().findFirst().get().getClass()).isEqualTo(Estado.class);

        Assertions.assertThat(estadoPage.getBody().get().findFirst().get().getNome()).isEqualTo(NAME_STATE).isNotNull();

        Assertions.assertThat(estadoPage.getBody().get().findFirst().get().getCidades()).isNotEmpty().isNotNull();

        Assertions.assertThat(estadoPage.getBody().get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(estadoPage.getBody().toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return estado by id when Sucessful")
    void returnEstadoById_WhenSucessful(){
        ResponseEntity<Estado> estadoReturn = estadoController.findById(1L);

        Assertions.assertThat(estadoReturn).isNotNull();

        Assertions.assertThat(estadoReturn.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(estadoReturn.getBody()).isNotNull();

        Assertions.assertThat(estadoReturn.getBody().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(estadoReturn.getBody().getNome()).isEqualTo(NAME_STATE).isNotNull();

        Assertions.assertThat(estadoReturn.getBody().getCidades()).isNotEmpty().isNotNull();

        Assertions.assertThat(Estado.class).isEqualTo(estadoReturn.getBody().getClass());

        Assertions.assertThat(ResponseEntity.class).isEqualTo(estadoReturn.getClass());

    }

    @Test
    @DisplayName("Save estado when sucessful")
    void save_AddEstado_WhenSucessful(){
        ResponseEntity<Void> retorno = estadoController.save(new Estado(null, NAME_STATE));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(retorno.getHeaders().get("Location")).isNotNull().isNotEmpty();

    }

    @Test
    @DisplayName("Update estado when sucessful")
    void update_ReplaceEstado_WhenSucessful(){
        ResponseEntity<Void> retorno = estadoController.update(1L, new Estado(null, NAME_STATE));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete estado when sucessful")
    void delete_RemoveEstado_WhenSucessful(){
        ResponseEntity<Void> retorno = estadoController.delete(1L);

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(retorno).isNotNull();
    }

    private void startEntitys(){
        estado = new Estado(1L, NAME_STATE);
        cidade = new Cidade(1L, NAME_CITY, estado);

        estado.getCidades().add(cidade);
    }
}
