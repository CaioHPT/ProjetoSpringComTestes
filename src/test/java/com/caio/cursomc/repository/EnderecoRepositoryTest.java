package com.caio.cursomc.repository;

import com.caio.cursomc.model.Cidade;
import com.caio.cursomc.model.Cliente;
import com.caio.cursomc.model.Endereco;
import com.caio.cursomc.model.Estado;
import com.caio.cursomc.model.enums.TipoCliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for endereco repository")
public class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private static final String NAME_STATE_CITY = "São paulo";
    private static final String NAME_CLIENT = "Jocimar";
    private static final String EMAIL_CLIENT = "jocimar@gmail.com";
    private static final String CPF_CLIENT = "12232159301";
    private static final String PUBLIC_PLACE = "Rua do mockito";
    private static final String NUMBER = "777";
    private static final String COMPLEMENT = "Bloco 1";
    private static final String DISTRICT = "Junit";
    private static final String CEP = "21212021";

    @Test
    @DisplayName("Find all return list endereco when sucessful")
    void findAll_ReturnListEndereco_WhenSucessful(){
        List<Endereco> enderecos = enderecoRepository.findAll();

        Assertions.assertThat(enderecos).isNotNull();
    }

    @Test
    @DisplayName("save endereco when sucessful")
    void save_PersistEndereco_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE_CITY);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Cliente cliente = createdCliente();
        Cliente clienteSaved = clienteRepository.save(cliente);

        Endereco endereco = createdEndereco(clienteSaved, cidadeSaved);
        Endereco enderecoSaved = enderecoRepository.save(endereco);

        Assertions.assertThat(enderecoSaved).isNotNull();

        Assertions.assertThat(enderecoSaved.getId()).isNotNull();

        Assertions.assertThat(enderecoSaved.getBairro()).isEqualTo(endereco.getBairro()).isEqualTo(DISTRICT).isNotNull();

        Assertions.assertThat(enderecoSaved.getCep()).isEqualTo(endereco.getCep()).isNotNull().isEqualTo(CEP);

        Assertions.assertThat(enderecoSaved.getComplemento()).isEqualTo(endereco.getComplemento()).isEqualTo(COMPLEMENT).isNotNull();

        Assertions.assertThat(enderecoSaved.getLogradouro()).isEqualTo(endereco.getLogradouro()).isEqualTo(PUBLIC_PLACE).isNotNull();

        Assertions.assertThat(enderecoSaved.getNumero()).isEqualTo(endereco.getNumero()).isEqualTo(NUMBER).isNotNull();

        Assertions.assertThat(enderecoSaved.getCliente()).isEqualTo(endereco.getCliente()).isEqualTo(clienteSaved).isNotNull();

        Assertions.assertThat(enderecoSaved.getCidade()).isEqualTo(endereco.getCidade()).isEqualTo(cidadeSaved).isNotNull();

        Assertions.assertThat(enderecoSaved.getClass()).isEqualTo(Endereco.class);

    }

    @Test
    @DisplayName("update endereco when sucessful")
    void update_PersistEndereco_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE_CITY);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Cliente cliente = createdCliente();
        Cliente clienteSaved = clienteRepository.save(cliente);

        Endereco endereco = new Endereco(1L, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, clienteSaved, cidadeSaved);
        Endereco enderecoUpdated = enderecoRepository.save(endereco);

        Assertions.assertThat(enderecoUpdated).isNotNull();

        Assertions.assertThat(enderecoUpdated.getId()).isEqualTo(1L).isNotNull();

        Assertions.assertThat(enderecoUpdated.getBairro()).isEqualTo(endereco.getBairro()).isEqualTo(DISTRICT).isNotNull();

        Assertions.assertThat(enderecoUpdated.getCep()).isEqualTo(endereco.getCep()).isNotNull().isEqualTo(CEP);

        Assertions.assertThat(enderecoUpdated.getComplemento()).isEqualTo(endereco.getComplemento()).isEqualTo(COMPLEMENT).isNotNull();

        Assertions.assertThat(enderecoUpdated.getLogradouro()).isEqualTo(endereco.getLogradouro()).isEqualTo(PUBLIC_PLACE).isNotNull();

        Assertions.assertThat(enderecoUpdated.getNumero()).isEqualTo(endereco.getNumero()).isEqualTo(NUMBER).isNotNull();

        Assertions.assertThat(enderecoUpdated.getCliente()).isEqualTo(endereco.getCliente()).isEqualTo(clienteSaved).isNotNull();

        Assertions.assertThat(enderecoUpdated.getCidade()).isEqualTo(endereco.getCidade()).isEqualTo(cidadeSaved).isNotNull();

        Assertions.assertThat(enderecoUpdated.getClass()).isEqualTo(Endereco.class);

    }

    @Test
    @DisplayName("delete endereco when sucessful")
    void delete_RemoveEndereco_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE_CITY);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Cliente cliente = createdCliente();
        Cliente clienteSaved = clienteRepository.save(cliente);

        Endereco endereco = createdEndereco(clienteSaved, cidadeSaved);
        Endereco enderecoSaved = enderecoRepository.save(endereco);

        enderecoRepository.delete(enderecoSaved);

        Optional<Endereco> enderecoById = enderecoRepository.findById(enderecoSaved.getId());

        Assertions.assertThat(enderecoById).isEqualTo(Optional.empty());

        Assertions.assertThat(enderecoById.isPresent()).isFalse();
    }

    @Test
    @DisplayName("findById endereco when sucessful")
    void findById_FindEndereco_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE_CITY);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Cliente cliente = createdCliente();
        Cliente clienteSaved = clienteRepository.save(cliente);

        Endereco endereco = createdEndereco(clienteSaved, cidadeSaved);
        Endereco enderecoSaved = enderecoRepository.save(endereco);

        Optional<Endereco> enderecoById = enderecoRepository.findById(enderecoSaved.getId());

        Assertions.assertThat(enderecoById.get()).isNotNull().isIn(enderecoSaved);

        Assertions.assertThat(enderecoById.get().getId()).isNotNull().isEqualTo(enderecoSaved.getId());

        Assertions.assertThat(enderecoById.get().getBairro()).isEqualTo(enderecoSaved.getBairro()).isEqualTo(DISTRICT).isNotNull();

        Assertions.assertThat(enderecoById.get().getCep()).isEqualTo(enderecoSaved.getCep()).isEqualTo(CEP).isNotNull();

        Assertions.assertThat(enderecoById.get().getComplemento()).isEqualTo(enderecoSaved.getComplemento())
                .isEqualTo(COMPLEMENT).isNotNull();

        Assertions.assertThat(enderecoById.get().getLogradouro()).isEqualTo(enderecoSaved.getLogradouro())
                .isEqualTo(PUBLIC_PLACE)
                .isNotNull();

        Assertions.assertThat(enderecoById.get().getNumero()).isEqualTo(enderecoSaved.getNumero()).isNotNull();

        Assertions.assertThat(enderecoById.get().getCliente()).isEqualTo(enderecoSaved.getCliente())
                .isEqualTo(clienteSaved)
                .isNotNull();

        Assertions.assertThat(enderecoById.get().getCidade()).isEqualTo(enderecoSaved.getCidade())
                .isEqualTo(cidadeSaved)
                .isNotNull();

        Assertions.assertThat(enderecoById.get().getClass()).isEqualTo(Endereco.class);


    }

    @Test
    @DisplayName("findById endereco when not found")
    void findById_FindEndereco_NotFound(){
        Optional<Endereco> enderecoById = enderecoRepository.findById(310L);

        Assertions.assertThat(enderecoById).isEqualTo(Optional.empty());

        Assertions.assertThat(enderecoById.isPresent()).isFalse();
    }


    private Endereco createdEndereco(Cliente cliente, Cidade cidade){
        return new Endereco(null, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade);
    }

    private Cidade createdCidade(Estado estado){
        return new Cidade(null, NAME_STATE_CITY, estado);
    }

    private Cliente createdCliente(){
        return new Cliente(null, NAME_CLIENT, EMAIL_CLIENT, CPF_CLIENT, TipoCliente.PESSOA_FISICA);
    }

}