package com.caio.cursomc.service;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Cliente;
import com.caio.cursomc.model.Endereco;
import com.caio.cursomc.model.Estado;
import com.caio.cursomc.model.enums.TipoCliente;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class EnderecoServiceTest {

    private Cliente cliente;
    private Cidade cidade;
    private Endereco endereco;
    private Estado estado;

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

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

        List<Endereco> enderecos = Arrays
                .asList(endereco);

        PageImpl<Endereco> enderecoPage = new PageImpl<>(Arrays.asList(endereco));

        BDDMockito.when(enderecoRepository.findAll()).thenReturn(enderecos);

        BDDMockito.when(enderecoRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(endereco));

        BDDMockito.when(enderecoRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(enderecoPage);

        BDDMockito.when(enderecoRepository.save(ArgumentMatchers.any(Endereco.class))).thenReturn((endereco));

        BDDMockito.doNothing().when(enderecoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("List return of enderecos when Sucessful")
    void list_ReturnEnderecos_WhenSucessful(){
        List<Endereco> enderecos = enderecoService.findAll();

        Assertions.assertThat(enderecos).isNotNull();

        Assertions.assertThat(enderecos.get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(enderecos.get(0).getNumero()).isNotNull().isEqualTo(NUMBER);

        Assertions.assertThat(enderecos.get(0).getCidade()).isNotNull().isEqualTo(cidade);

        Assertions.assertThat(enderecos.get(0).getCep()).isNotNull().isEqualTo(CEP);

        Assertions.assertThat(enderecos.get(0).getBairro()).isNotNull().isEqualTo(DISTRICT);

        Assertions.assertThat(enderecos.get(0).getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(enderecos.get(0).getComplemento()).isNotNull().isEqualTo(COMPLEMENT);

        Assertions.assertThat(enderecos.get(0).getLogradouro()).isNotNull().isEqualTo(PUBLIC_PLACE);

        Assertions.assertThat(enderecos.isEmpty()).isFalse();

        Assertions.assertThat(enderecos.get(0).getClass()).isEqualTo(Endereco.class);
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(enderecoService.findAll()).thenReturn(Collections.emptyList());

        List<Endereco> enderecos = enderecoService.findAll();

        Assertions.assertThat(enderecos).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(enderecos.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("page return enderecos when Sucessful")
    void page_ReturnEnderecos_WhenSucessful(){
        Page<Endereco> enderecoPage = enderecoService.findPage(0, 24, "nome", "ASC");

        Assertions.assertThat(enderecoPage).isNotNull();

        Assertions.assertThat(enderecoPage.get().findFirst().isPresent()).isNotNull();

        Assertions.assertThat(enderecoPage.get().count() == 1).isTrue();

        Assertions.assertThat(enderecoPage.get().findFirst().get().getClass()).isEqualTo(Endereco.class);

        Assertions.assertThat(enderecoPage.get().findFirst().get().getNumero()).isEqualTo(NUMBER).isNotNull();

        Assertions.assertThat(enderecoPage.get().findFirst().get().getComplemento()).isEqualTo(COMPLEMENT).isNotNull();

        Assertions.assertThat(enderecoPage.get().findFirst().get().getCliente()).isEqualTo(cliente).isNotNull();

        Assertions.assertThat(enderecoPage.get().findFirst().get().getBairro()).isEqualTo(DISTRICT).isNotNull();

        Assertions.assertThat(enderecoPage.get().findFirst().get().getCep()).isEqualTo(CEP).isNotNull();

        Assertions.assertThat(enderecoPage.get().findFirst().get().getLogradouro()).isEqualTo(PUBLIC_PLACE).isNotNull();

        Assertions.assertThat(enderecoPage.get().findFirst().get().getCidade()).isNotNull().isEqualTo(cidade);

        Assertions.assertThat(enderecoPage.get().findFirst().get().getClass()).isEqualTo(Endereco.class);

        Assertions.assertThat(enderecoPage.get().findFirst().get().getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(enderecoPage.toList()).isNotEmpty();

    }

    @Test
    @DisplayName("Return endereco by id when Sucessful")
    void returnEnderecoById_WhenSucessful(){
        Endereco enderecoReturn = enderecoService.findById(1L);

        Assertions.assertThat(enderecoReturn).isNotNull();

        Assertions.assertThat(enderecoReturn.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(enderecoReturn.getNumero()).isNotNull().isEqualTo(NUMBER);

        Assertions.assertThat(enderecoReturn.getCidade()).isNotNull().isEqualTo(cidade);

        Assertions.assertThat(enderecoReturn.getCep()).isNotNull().isEqualTo(CEP);

        Assertions.assertThat(enderecoReturn.getBairro()).isNotNull().isEqualTo(DISTRICT);

        Assertions.assertThat(enderecoReturn.getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(enderecoReturn.getComplemento()).isNotNull().isEqualTo(COMPLEMENT);

        Assertions.assertThat(enderecoReturn.getLogradouro()).isNotNull().isEqualTo(PUBLIC_PLACE);

        Assertions.assertThat(enderecoReturn.getClass()).isEqualTo(Endereco.class);
    }

    @Test
    @DisplayName("Return ObjectNotFound when endereco not found")
    void returnException_WhenNotFound(){
        BDDMockito.when(enderecoRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ObjectNotFoundException.class)
                .isThrownBy(() -> enderecoService.findById(1L));
    }

    @Test
    @DisplayName("Save endereco when sucessful")
    void save_AddEndereco_WhenSucessful(){

        Endereco retorno = enderecoService.save(new Endereco(null, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade));

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(retorno.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(retorno.getNumero()).isNotNull().isEqualTo(NUMBER);

        Assertions.assertThat(retorno.getCidade()).isNotNull().isEqualTo(cidade);

        Assertions.assertThat(retorno.getCep()).isNotNull().isEqualTo(CEP);

        Assertions.assertThat(retorno.getBairro()).isNotNull().isEqualTo(DISTRICT);

        Assertions.assertThat(retorno.getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(retorno.getComplemento()).isNotNull().isEqualTo(COMPLEMENT);

        Assertions.assertThat(retorno.getLogradouro()).isNotNull().isEqualTo(PUBLIC_PLACE);

        Assertions.assertThat(retorno.getClass()).isEqualTo(Endereco.class);
    }

    @Test
    @DisplayName("Update endereco when sucessful")
    void update_ReplaceEndereco_WhenSucessful(){
        Endereco retorno = enderecoService.update(endereco);

        Assertions.assertThatCode(() -> enderecoService
                .update(endereco))
                .doesNotThrowAnyException();

        Assertions.assertThat(retorno).isNotNull();
    }

    @Test
    @DisplayName("Delete endereco when sucessful")
    void delete_RemoveEstado_WhenSucessful(){
        Assertions.assertThatCode(() -> enderecoService.delete(1L))
                .doesNotThrowAnyException();
    }

    private void startEntitys(){
        estado = new Estado(1L, NAME_STATE_CITY);
        cidade = new Cidade(1L, NAME_STATE_CITY, estado);
        cliente= new Cliente(1L, NAME_CLIENT, EMAIL_CLIENT, CPF_CLIENT, TipoCliente.PESSOA_FISICA);
        endereco = new Endereco(1L, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade);
    }
}
