package com.caio.cursomc.controller;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Cliente;
import com.caio.cursomc.model.Endereco;
import com.caio.cursomc.model.Estado;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.service.EnderecoService;
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
public class EnderecoControllerTest {

    private Cliente cliente;
    private Cidade cidade;
    private Endereco endereco;
    private Estado estado;

    @InjectMocks
    private EnderecoController enderecoController;

    @Mock
    private EnderecoService enderecoService;

    private static final String NAME_STATE_CITY = "São paulo";
    private static final String NAME_CLIENT = "Jocimar";
    private static final String EMAIL_CLIENT = "jocimar@gmail.com";
    private static final String CPF_CLIENT = "12232159301";
    private static final String PUBLIC_PLACE = "Rua do mockito";
    private static final String NUMBER = "777";
    private static final String COMPLEMENT = "Bloco 1";
    private static final String DISTRICT = "Junit";
    private static final String CEP = "21212021";

    @BeforeEach
    void setUp(){
        startEntitys();

        List<Endereco> enderecos = Arrays.asList(endereco);

        PageImpl<Endereco> enderecoPage = new PageImpl<>(Arrays.asList(endereco));

        BDDMockito.when(enderecoService.findAll()).thenReturn(enderecos);

        BDDMockito.when(enderecoService.findById(ArgumentMatchers.anyLong())).thenReturn(endereco);

        BDDMockito.when(enderecoService.findPage(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()))
                .thenReturn(enderecoPage);

        BDDMockito.when(enderecoService.save(ArgumentMatchers.any(Endereco.class))).thenReturn(endereco);

        BDDMockito.when(enderecoService.update(ArgumentMatchers.any(Endereco.class))).thenReturn(endereco);

        BDDMockito.doNothing().when(enderecoService).delete(1L);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    @DisplayName("List return of enderecos when Sucessful")
    void list_ReturnEnderecos_WhenSucessful(){
        ResponseEntity<List<Endereco>> enderecos = enderecoController.findAll();

        Assertions.assertThat(enderecos).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(enderecos.getClass());

        Assertions.assertThat(enderecos.getBody()).isNotNull();

        Assertions.assertThat(enderecos.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(enderecos.getBody().get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(enderecos.getBody().get(0).getCidade()).isNotNull().isEqualTo(cidade);

        Assertions.assertThat(enderecos.getBody().get(0).getLogradouro()).isNotNull().isEqualTo(PUBLIC_PLACE);

        Assertions.assertThat(enderecos.getBody().get(0).getCep()).isNotNull().isEqualTo(CEP);

        Assertions.assertThat(enderecos.getBody().get(0).getBairro()).isNotNull().isEqualTo(DISTRICT);

        Assertions.assertThat(enderecos.getBody().get(0).getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(enderecos.getBody().get(0).getComplemento()).isNotNull().isEqualTo(COMPLEMENT);

        Assertions.assertThat(enderecos.getBody().get(0).getNumero()).isNotNull().isEqualTo(NUMBER);

        Assertions.assertThat(enderecos.getBody().get(0).getClass()).isEqualTo(Endereco.class);

        Assertions.assertThat(enderecos.getBody().isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(enderecoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Endereco>> enderecos = enderecoController.findAll();

        Assertions.assertThat(enderecos.getBody()).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(enderecos.getBody().isEmpty()).isTrue();

        Assertions.assertThat(enderecos).isNotNull();

        Assertions.assertThat(enderecos.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(ResponseEntity.class).isEqualTo(enderecos.getClass());

    }

    @Test
    @DisplayName("page return enderecos when Sucessful")
    void page_ReturnEnderecos_WhenSucessful(){
        ResponseEntity<Page<Endereco>> enderecoPage = enderecoController.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(enderecoPage).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(enderecoPage.getClass());

        Assertions.assertThat(enderecoPage.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(enderecoPage.getBody()).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().get().count() == 1).isTrue();

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().get().getClass()).isEqualTo(Endereco.class);

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().get().getNumero()).isEqualTo(NUMBER).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().get().getComplemento()).isEqualTo(COMPLEMENT).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().get().getCliente()).isEqualTo(cliente).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().get().getBairro()).isEqualTo(DISTRICT).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().get().getCep()).isEqualTo(CEP).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().get().getLogradouro()).isEqualTo(PUBLIC_PLACE).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().get().getCidade()).isEqualTo(cidade).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(enderecoPage.getBody().toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return endereco by id when Sucessful")
    void returnEnderecoById_WhenSucessful(){
        ResponseEntity<Endereco> enderecoReturn = enderecoController.findById(1L);

        Assertions.assertThat(enderecoReturn).isNotNull();

        Assertions.assertThat(enderecoReturn.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(enderecoReturn.getBody()).isNotNull();

        Assertions.assertThat(enderecoReturn.getBody().getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(enderecoReturn.getBody().getCidade()).isNotNull().isEqualTo(cidade);

        Assertions.assertThat(enderecoReturn.getBody().getLogradouro()).isNotNull().isEqualTo(PUBLIC_PLACE);

        Assertions.assertThat(enderecoReturn.getBody().getCep()).isNotNull().isEqualTo(CEP);

        Assertions.assertThat(enderecoReturn.getBody().getBairro()).isNotNull().isEqualTo(DISTRICT);

        Assertions.assertThat(enderecoReturn.getBody().getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(enderecoReturn.getBody().getComplemento()).isNotNull().isEqualTo(COMPLEMENT);

        Assertions.assertThat(enderecoReturn.getBody().getNumero()).isNotNull().isEqualTo(NUMBER);

        Assertions.assertThat(Endereco.class).isEqualTo(enderecoReturn.getBody().getClass());

        Assertions.assertThat(ResponseEntity.class).isEqualTo(enderecoReturn.getClass());

    }

    @Test
    @DisplayName("Save endereco when sucessful")
    void save_AddEndereco_WhenSucessful(){
        ResponseEntity<Void> retorno = enderecoController.save(
                new Endereco(null, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(retorno.getHeaders().get("Location")).isNotNull().isNotEmpty();

    }

    @Test
    @DisplayName("Update endereco when sucessful")
    void update_ReplaceEndereco_WhenSucessful(){
        ResponseEntity<Void> retorno = enderecoController.update(1L,
                new Endereco(null, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete endereco when sucessful")
    void delete_RemoveEndereco_WhenSucessful(){
        ResponseEntity<Void> retorno = enderecoController.delete(1L);

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(retorno).isNotNull();
    }

    private void startEntitys(){
        estado = new Estado(1L, NAME_STATE_CITY);
        cidade = new Cidade(1L, NAME_STATE_CITY, estado);
        cliente= new Cliente(1L, NAME_CLIENT, EMAIL_CLIENT, CPF_CLIENT, TipoCliente.PESSOA_FISICA);
        endereco = new Endereco(1L, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade);
    }
}
