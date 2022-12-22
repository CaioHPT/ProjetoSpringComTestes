package com.caio.cursomc.controller;

import com.caio.cursomc.DTO.ClienteDTO;
import com.caio.cursomc.DTO.ClienteInsertDTO;
import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Cliente;
import com.caio.cursomc.model.Endereco;
import com.caio.cursomc.model.Estado;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.service.ClienteService;
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
public class ClienteControllerTest {

    private Cliente cliente;
    private ClienteDTO clienteDTO;
    private Estado estado;
    private Cidade cidade;
    private Endereco endereco;

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    private static final String NAME_CLIENT = "Jocimar";
    private static final String EMAIL_CLIENT = "jocimar@gmail.com";
    private static final String CNPJ_CLIENT = "12232159301";
    private static final String NAME_STATE_CITY = "São paulo";
    private static final String PUBLIC_PLACE = "Rua do mockito";
    private static final String NUMBER = "777";
    private static final String COMPLEMENT = "Bloco 1";
    private static final String DISTRICT = "Junit";
    private static final String CEP = "21212021";

    @BeforeEach
    void setUp(){
        startEntitys();

        List<ClienteDTO> clientes = Arrays.asList(clienteDTO);

        PageImpl<Cliente> clientePage = new PageImpl<>(Arrays.asList(cliente));

        BDDMockito.when(clienteService.findAll()).thenReturn(clientes);

        BDDMockito.when(clienteService.findById(ArgumentMatchers.anyLong())).thenReturn(cliente);

        BDDMockito.when(clienteService.findPage(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()))
                .thenReturn(clientePage);

        BDDMockito.when(clienteService.save(ArgumentMatchers.any(ClienteInsertDTO.class))).thenReturn(cliente);

        BDDMockito.when(clienteService.update(ArgumentMatchers.any(ClienteDTO.class))).thenReturn(cliente);

        BDDMockito.doNothing().when(clienteService).delete(1L);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    @DisplayName("List return of clientes when Sucessful")
    void list_ReturnClientes_WhenSucessful(){
        ResponseEntity<List<ClienteDTO>> clientes = clienteController.findAll();

        Assertions.assertThat(clientes).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(clientes.getClass());

        Assertions.assertThat(clientes.getBody()).isNotNull();

        Assertions.assertThat(clientes.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(clientes.getBody().get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(clientes.getBody().get(0).getNome()).isNotNull().isEqualTo(NAME_CLIENT);

        Assertions.assertThat(clientes.getBody().get(0).getEmail()).isNotNull().isEqualTo(EMAIL_CLIENT);

        Assertions.assertThat(clientes.getBody().get(0).getClass()).isEqualTo(ClienteDTO.class);

        Assertions.assertThat(clientes.getBody().isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(clienteService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ClienteDTO>> clientes = clienteController.findAll();

        Assertions.assertThat(clientes.getBody()).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(clientes.getBody().isEmpty()).isTrue();

        Assertions.assertThat(clientes).isNotNull();

        Assertions.assertThat(clientes.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(ResponseEntity.class).isEqualTo(clientes.getClass());

    }

    @Test
    @DisplayName("page return clientes when Sucessful")
    void page_ReturnClientes_WhenSucessful(){
        ResponseEntity<Page<Cliente>> clientePage = clienteController.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(clientePage).isNotNull();

        Assertions.assertThat(ResponseEntity.class).isEqualTo(clientePage.getClass());

        Assertions.assertThat(clientePage.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(clientePage.getBody()).isNotNull();

        Assertions.assertThat(clientePage.getBody().get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(clientePage.getBody().get().count() == 1).isTrue();

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getClass()).isEqualTo(Cliente.class);

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getNome()).isEqualTo(NAME_CLIENT).isNotNull();

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getEmail()).isEqualTo(EMAIL_CLIENT).isNotNull();

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getCpf_cnpj()).isEqualTo(CNPJ_CLIENT).isNotNull();

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getTipoCliente()).isEqualTo(TipoCliente.PESSOA_JURIDICA).isNotNull();

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getTipoCliente().getClass())
                .isEqualTo(TipoCliente.class).isNotNull();

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getPedidos()).isNotNull();

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getEnderecos()).isNotNull().isNotEmpty();

        Assertions.assertThat(clientePage.getBody().get().findFirst().get().getTelefones()).isNotNull().isNotEmpty();

        Assertions.assertThat(clientePage.getBody().toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return cliente by id when Sucessful")
    void returnClienteById_WhenSucessful(){
        ResponseEntity<Cliente> clienteReturn = clienteController.findById(1L);

        Assertions.assertThat(clienteReturn).isNotNull();

        Assertions.assertThat(clienteReturn.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(clienteReturn.getBody()).isNotNull();

        Assertions.assertThat(clienteReturn.getBody().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(clienteReturn.getBody().getNome()).isEqualTo(NAME_CLIENT).isNotNull();

        Assertions.assertThat(clienteReturn.getBody().getEmail()).isNotNull().isEqualTo(EMAIL_CLIENT);

        Assertions.assertThat(clienteReturn.getBody().getCpf_cnpj()).isEqualTo(CNPJ_CLIENT).isNotNull();

        Assertions.assertThat(clienteReturn.getBody().getTipoCliente()).isNotNull().isEqualTo(TipoCliente.PESSOA_JURIDICA);

        Assertions.assertThat(clienteReturn.getBody().getTipoCliente().getClass()).isEqualTo(TipoCliente.class);

        Assertions.assertThat(clienteReturn.getBody().getEnderecos()).isNotNull().isNotEmpty();

        Assertions.assertThat(clienteReturn.getBody().getTelefones()).isNotNull().isNotEmpty();

        Assertions.assertThat(Cliente.class).isEqualTo(clienteReturn.getBody().getClass());

        Assertions.assertThat(ResponseEntity.class).isEqualTo(clienteReturn.getClass());

    }
    
    @Test
    @DisplayName("Save cliente when sucessful")
    void save_AddCliente_WhenSucessful(){
        ResponseEntity<Void> retorno = clienteController.save(new ClienteInsertDTO(
                NAME_CLIENT,
                EMAIL_CLIENT,
                CNPJ_CLIENT,
                TipoCliente.PESSOA_JURIDICA.getCodigo(),
                PUBLIC_PLACE,
                NUMBER,
                DISTRICT,
                CEP,
                Arrays.asList("20131"),
                1L));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(retorno.getHeaders().get("Location")).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("Update cliente when sucessful")
    void update_ReplaceCliente_WhenSucessful(){
        ResponseEntity<Void> retorno = clienteController.update(1L, new ClienteDTO(null, NAME_CLIENT, EMAIL_CLIENT));

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete cliente when sucessful")
    void delete_RemoveCliente_WhenSucessful(){
        ResponseEntity<Void> retorno = clienteController.delete(1L);

        Assertions.assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(retorno).isNotNull();
    }

    private void startEntitys(){
        cliente = new Cliente(1L, NAME_CLIENT, EMAIL_CLIENT, CNPJ_CLIENT, TipoCliente.PESSOA_JURIDICA);
        clienteDTO = new ClienteDTO(1L, NAME_CLIENT, EMAIL_CLIENT);
        estado = new Estado(1L, NAME_STATE_CITY);
        cidade = new Cidade(1L, NAME_STATE_CITY, estado);
        endereco = new Endereco(1L, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade);

        cliente.getTelefones().add("11231245632");
        cliente.getEnderecos().add(endereco);
    }
}
