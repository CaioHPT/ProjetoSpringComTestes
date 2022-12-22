package com.caio.cursomc.controller;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Estado;
import com.caio.cursomc.service.CidadeService;
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

@ExtendWith(SpringExtension.class)
public class CidadeControllerTest {

    private Estado estado;
    private Cidade cidade;

    @InjectMocks
    private CidadeController cidadeController;

    @Mock
    private CidadeService cidadeService;

    private static final String NAME_CITY = "Vinhedo";
    private static final String NAME_STATE = "São paulo";

    @BeforeEach
    void setUp(){
        startEntitys();

        List<Cidade> cidades = Arrays.asList(cidade);

        PageImpl<Cidade> cidadePage = new PageImpl<>(Arrays.asList(cidade));

        BDDMockito.when(cidadeService.findAll()).thenReturn(cidades);

        BDDMockito.when(cidadeService.findById(ArgumentMatchers.anyLong())).thenReturn(cidade);

        BDDMockito.when(cidadeService.findPage(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()))
                .thenReturn(cidadePage);

        BDDMockito.when(cidadeService.save(ArgumentMatchers.any(Cidade.class))).thenReturn(cidade);

        BDDMockito.when(cidadeService.update(ArgumentMatchers.any(Cidade.class))).thenReturn(cidade);

        BDDMockito.doNothing().when(cidadeService).delete(1L);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    @DisplayName("List return of cidades when Sucessful")
    void list_ReturnCidades_WhenSucessful(){
        ResponseEntity<List<Cidade>> cidades = cidadeController.findAll();

        Assertions.assertThat(cidades).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(cidades.getClass());

        Assertions.assertThat(cidades.getBody()).isNotNull();

        Assertions.assertThat(cidades.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(cidades.getBody().get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(cidades.getBody().get(0).getNome()).isNotNull().isEqualTo(NAME_CITY);

        Assertions.assertThat(cidades.getBody().get(0).getEstado()).isNotNull().isEqualTo(estado);

        Assertions.assertThat(cidades.getBody().get(0).getClass()).isEqualTo(Cidade.class);

        Assertions.assertThat(cidades.getBody().isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(cidadeService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Cidade>> cidades = cidadeController.findAll();

        Assertions.assertThat(cidades.getBody()).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(cidades.getBody().isEmpty()).isTrue();

        Assertions.assertThat(cidades).isNotNull();

        Assertions.assertThat(cidades.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(ResponseEntity.class).isEqualTo(cidades.getClass());

    }

    @Test
    @DisplayName("page return cidades when Sucessful")
    void page_ReturnCidades_WhenSucessful(){
        ResponseEntity<Page<Cidade>> cidadePage = cidadeController.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(cidadePage).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(cidadePage.getClass());

        Assertions.assertThat(cidadePage.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(cidadePage.getBody()).isNotNull();

        Assertions.assertThat(cidadePage.getBody().get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(cidadePage.getBody().get().count() == 1).isTrue();

        Assertions.assertThat(cidadePage.getBody().get().findFirst().get().getClass()).isEqualTo(Cidade.class);

        Assertions.assertThat(cidadePage.getBody().get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(cidadePage.getBody().get().findFirst().get().getNome()).isEqualTo(NAME_CITY).isNotNull();

        Assertions.assertThat(cidadePage.getBody().get().findFirst().get().getEstado()).isEqualTo(estado).isNotNull();

        Assertions.assertThat(cidadePage.getBody().toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return cidade by id when Sucessful")
    void returnCidadeById_WhenSucessful(){
        ResponseEntity<Cidade> cidadeReturn = cidadeController.findById(1L);

        Assertions.assertThat(cidadeReturn).isNotNull();

        Assertions.assertThat(cidadeReturn.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(cidadeReturn.getBody()).isNotNull();

        Assertions.assertThat(cidadeReturn.getBody().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(cidadeReturn.getBody().getNome()).isEqualTo(NAME_CITY).isNotNull();

        Assertions.assertThat(cidadeReturn.getBody().getEstado()).isEqualTo(estado).isNotNull();

        Assertions.assertThat(Cidade.class).isEqualTo(cidadeReturn.getBody().getClass());

        Assertions.assertThat(ResponseEntity.class).isEqualTo(cidadeReturn.getClass());

    }
    
    @Test
    @DisplayName("Save cidade when sucessful")
    void save_AddCidade_WhenSucessful(){
        ResponseEntity<Void> retorno = cidadeController.save(new Cidade(null, NAME_CITY, estado));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(retorno.getHeaders().get("Location")).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("Update categoria when sucessful")
    void update_ReplaceCategoria_WhenSucessful(){
        ResponseEntity<Void> retorno = cidadeController.update(1L, new Cidade(null, NAME_CITY, estado));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete categoria when sucessful")
    void delete_RemoveCategoria_WhenSucessful(){
        ResponseEntity<Void> retorno = cidadeController.delete(1L);

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(retorno).isNotNull();
    }

    private void startEntitys(){
        estado = new Estado(1L, NAME_STATE);
        cidade = new Cidade(1L, NAME_CITY, estado);
    }
}
