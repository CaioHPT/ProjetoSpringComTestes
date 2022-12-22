package com.caio.cursomc.service;

import com.caio.cursomc.DTO.ClienteDTO;
import com.caio.cursomc.DTO.ClienteInsertDTO;
import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Cliente;
import com.caio.cursomc.model.Endereco;
import com.caio.cursomc.model.Estado;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.repository.ClienteRepository;
import com.caio.cursomc.repository.EnderecoRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class ClienteServiceTest {

    private Cliente cliente;
    private Estado estado;
    private Cidade cidade;
    private Endereco endereco;

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private static final String NAME_CLIENT = "Jocimar";
    private static final String EMAIL_CLIENT = "jocimar@gmail.com";
    private static final String CNPJ_CLIENT = "12232159301";
    private static final String NAME_STATE_CITY = "São paulo";
    private static final String PUBLIC_PLACE = "Rua do mockito";
    private static final String NUMBER = "777";
    private static final String COMPLEMENT = "Bloco 1";
    private static final String DISTRICT = "Junit";
    private static final String CEP = "21212021";
    private static final String NUMBER_PHONE = "11987266631";

    @BeforeEach
    void setUp(){
        startEntitys();

        List<Cliente> clientes = Arrays
                .asList(cliente);

        PageImpl<Cliente> clientePage = new PageImpl<Cliente>(Arrays
                .asList(cliente));

        BDDMockito.when(clienteRepository.findAll()).thenReturn(clientes);

        BDDMockito.when(clienteRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(cliente));

        BDDMockito.when(clienteRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(clientePage);

        BDDMockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class)))
                .thenReturn(cliente);

        BDDMockito.doNothing().when(clienteRepository).deleteById(1L);
    }

    @Test
    @DisplayName("List return of clientes when Sucessful")
    void list_ReturnClientes_WhenSucessful(){
        List<ClienteDTO> clientes = clienteService.findAll();

        Assertions.assertThat(clientes).isNotNull();

        Assertions.assertThat(clientes.get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(clientes.get(0).getNome()).isNotNull().isEqualTo(NAME_CLIENT);

        Assertions.assertThat(clientes.get(0).getEmail()).isNotNull().isEqualTo(EMAIL_CLIENT);

        Assertions.assertThat(clientes.get(0).getClass()).isEqualTo(ClienteDTO.class);

        Assertions.assertThat(clientes.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(clienteService.findAll()).thenReturn(Collections.emptyList());

        List<ClienteDTO> clienteDTOS = clienteService.findAll();

        Assertions.assertThat(clienteDTOS).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(clienteDTOS.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("page return clientes when Sucessful")
    void page_ReturnClientes_WhenSucessful(){
        Page<Cliente> clientePage = clienteService.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(clientePage).isNotNull();

        Assertions.assertThat(clientePage.get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(clientePage.get().count() == 1).isTrue();

        Assertions.assertThat(clientePage.get().findFirst().get().getClass()).isEqualTo(Cliente.class);

        Assertions.assertThat(clientePage.get().findFirst().get().getNome()).isEqualTo(NAME_CLIENT).isNotNull();

        Assertions.assertThat(clientePage.get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(clientePage.get().findFirst().get().getCpf_cnpj())
                .isEqualTo(CNPJ_CLIENT).isNotNull();

        Assertions.assertThat(clientePage.get().findFirst().get().getEmail()).isNotNull().isEqualTo(EMAIL_CLIENT);

        Assertions.assertThat(clientePage.get().findFirst().get().getTipoCliente()).isNotNull().isEqualTo(TipoCliente.PESSOA_JURIDICA);

        Assertions.assertThat(clientePage.get().findFirst().get().getPedidos()).isNotNull();

        Assertions.assertThat(clientePage.get().findFirst().get().getEnderecos()).isNotNull().isNotEmpty().isEqualTo(Arrays.asList(endereco));

        Assertions.assertThat(clientePage.get().findFirst().get().getTelefones()).isNotNull().isNotEmpty().isEqualTo(new HashSet(Arrays.asList(NUMBER_PHONE)));

        Assertions.assertThat(clientePage.toList()).isNotEmpty();
    }

    @Test
    @DisplayName("Return cliente by id when Sucessful")
    void returnClienteById_WhenSucessful(){
        Cliente clienteReturn = clienteService.findById(1L);

        Assertions.assertThat(clienteReturn).isNotNull();

        Assertions.assertThat(clienteReturn.getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(clienteReturn.getNome()).isEqualTo(NAME_CLIENT).isNotNull();

        Assertions.assertThat(clienteReturn.getEmail()).isNotNull().isEqualTo(EMAIL_CLIENT);

        Assertions.assertThat(clienteReturn.getCpf_cnpj()).isEqualTo(CNPJ_CLIENT).isNotNull();

        Assertions.assertThat(clienteReturn.getTipoCliente()).isNotNull().isEqualTo(TipoCliente.PESSOA_JURIDICA);

        Assertions.assertThat(clienteReturn.getTipoCliente().getClass()).isEqualTo(TipoCliente.class);

        Assertions.assertThat(clienteReturn.getEnderecos()).isNotNull().isNotEmpty().isEqualTo(Arrays.asList(endereco));

        Assertions.assertThat(clienteReturn.getTelefones()).isNotNull().isNotEmpty().isEqualTo(new HashSet(Arrays.asList(NUMBER_PHONE)));

        Assertions.assertThat(clienteReturn.getPedidos()).isNotNull();

        Assertions.assertThat(Cliente.class).isEqualTo(clienteReturn.getClass());
    }

    @Test
    @DisplayName("Return ObjectNotFound when cliente not found")
    void returnException_WhenNotFound(){
        BDDMockito.when(clienteRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ObjectNotFoundException.class)
                .isThrownBy(() -> clienteService.findById(1L));
    }

    @Test
    @DisplayName("Save cliente when sucessful")
    void save_AddCliente_WhenSucessful(){
        Cliente retorno = clienteService.save(new ClienteInsertDTO(
                NAME_CLIENT,
                EMAIL_CLIENT,
                CNPJ_CLIENT,
                TipoCliente.PESSOA_JURIDICA.getCodigo(),
                PUBLIC_PLACE,
                NUMBER,
                DISTRICT,
                CEP,
                Arrays.asList(NUMBER_PHONE),
                1L));

        Assertions.assertThat(retorno.getId()).isNotNull();

        Assertions.assertThat(retorno.getNome()).isEqualTo(NAME_CLIENT).isNotNull();

        Assertions.assertThat(retorno.getEmail()).isNotNull().isEqualTo(EMAIL_CLIENT);

        Assertions.assertThat(retorno.getCpf_cnpj()).isEqualTo(CNPJ_CLIENT).isNotNull();

        Assertions.assertThat(retorno.getTipoCliente()).isNotNull().isEqualTo(TipoCliente.PESSOA_JURIDICA);

        Assertions.assertThat(retorno.getTipoCliente().getClass()).isEqualTo(TipoCliente.class);

        Assertions.assertThat(retorno.getTelefones()).isNotNull().isNotEmpty().isEqualTo(new HashSet(Arrays.asList(NUMBER_PHONE)));

        Assertions.assertThat(retorno.getEnderecos()).isNotEmpty().isNotEmpty();

        Assertions.assertThat(retorno.getPedidos()).isNotNull();

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Update cliente when sucessful")
    void update_ReplaceCliente_WhenSucessful(){
        Cliente retorno = clienteService.update(new ClienteDTO(1L, NAME_CLIENT, EMAIL_CLIENT));

        Assertions.assertThatCode(() -> clienteService.update(new ClienteDTO(1L, NAME_CLIENT, EMAIL_CLIENT)))
                .doesNotThrowAnyException();

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete cliente when sucessful")
    void delete_RemoveCliente_WhenSucessful(){
        Assertions.assertThatCode(() -> clienteService.delete(1L))
                .doesNotThrowAnyException();
    }

    private void startEntitys(){
        cliente = new Cliente(1L, NAME_CLIENT, EMAIL_CLIENT, CNPJ_CLIENT, TipoCliente.PESSOA_JURIDICA);
        estado = new Estado(1L, NAME_STATE_CITY);
        cidade = new Cidade(1L, NAME_STATE_CITY, estado);
        endereco = new Endereco(1L, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade);

        cliente.getTelefones().add(NUMBER_PHONE);
        cliente.getEnderecos().add(endereco);
    }

}
