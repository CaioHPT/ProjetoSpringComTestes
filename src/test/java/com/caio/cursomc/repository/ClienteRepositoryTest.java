package com.caio.cursomc.repository;


import com.caio.cursomc.model.Cliente;
import com.caio.cursomc.model.enums.TipoCliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for cliente repository")
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private static final String NAME_CLIENT = "Jocimar";
    private static final String EMAIL = "jocimar@gmail.com";
    private static final String CPF = "20349912237";
    private static final String PHONE = "123213244500";

    @Test
    @DisplayName("Find all return list clientes when sucessful")
    void findAll_ReturnListClientes_WhenSucessful(){
        List<Cliente> clientes = clienteRepository.findAll();

        Assertions.assertThat(clientes).isNotNull();
    }

    @Test
    @DisplayName("save cliente when sucessful")
    void save_PersistCliente_WhenSucessful(){
        Cliente cliente = createdCliente();
        cliente.getTelefones().add(PHONE);
        Cliente clienteSaved = clienteRepository.save(cliente);

        Assertions.assertThat(clienteSaved).isNotNull();

        Assertions.assertThat(clienteSaved.getId()).isNotNull();

        Assertions.assertThat(clienteSaved.getNome()).isNotNull().isEqualTo(cliente.getNome()).isEqualTo(NAME_CLIENT);

        Assertions.assertThat(clienteSaved.getEmail()).isNotNull().isEqualTo(cliente.getEmail()).isEqualTo(EMAIL);

        Assertions.assertThat(clienteSaved.getCpf_cnpj()).isNotNull().isEqualTo(cliente.getCpf_cnpj()).isEqualTo(CPF);

        Assertions.assertThat(clienteSaved.getTipoCliente()).isNotNull().isEqualTo(cliente.getTipoCliente())
                .isEqualTo(TipoCliente.PESSOA_FISICA);

        Assertions.assertThat(clienteSaved.getTelefones()).isNotNull().isNotEmpty();

        Assertions.assertThat(clienteSaved.getTelefones().contains(PHONE)).isTrue();

        Assertions.assertThat(clienteSaved.getEnderecos()).isNotNull();

        Assertions.assertThat(clienteSaved.getClass()).isEqualTo(Cliente.class);

    }

    @Test
    @DisplayName("update cliente when sucessful")
    void update_PersistCliente_WhenSucessful(){
        Cliente cliente = new Cliente(1L, NAME_CLIENT, EMAIL, CPF, TipoCliente.PESSOA_FISICA);
        cliente.getTelefones().add(PHONE);
        Cliente clienteUpdated = clienteRepository.save(cliente);

        Assertions.assertThat(clienteUpdated).isNotNull();

        Assertions.assertThat(clienteUpdated.getId()).isNotNull();

        Assertions.assertThat(clienteUpdated.getNome()).isNotNull().isEqualTo(cliente.getNome()).isEqualTo(NAME_CLIENT);

        Assertions.assertThat(clienteUpdated.getEmail()).isNotNull().isEqualTo(cliente.getEmail()).isEqualTo(EMAIL);

        Assertions.assertThat(clienteUpdated.getCpf_cnpj()).isNotNull().isEqualTo(cliente.getCpf_cnpj()).isEqualTo(CPF);

        Assertions.assertThat(clienteUpdated.getTipoCliente()).isNotNull().isEqualTo(cliente.getTipoCliente())
                .isEqualTo(TipoCliente.PESSOA_FISICA);

        Assertions.assertThat(clienteUpdated.getTelefones()).isNotNull().isNotEmpty();

        Assertions.assertThat(clienteUpdated.getTelefones().contains(PHONE)).isTrue();

        Assertions.assertThat(clienteUpdated.getEnderecos()).isNotNull();

        Assertions.assertThat(clienteUpdated.getClass()).isEqualTo(Cliente.class);

    }

    @Test
    @DisplayName("delete cliente when sucessful")
    void delete_RemoveCliente_WhenSucessful(){
        Cliente cliente = createdCliente();
        Cliente clienteSaved = clienteRepository.save(cliente);

        clienteRepository.delete(clienteSaved);

        Optional<Cliente> clienteById = clienteRepository.findById(clienteSaved.getId());

        Assertions.assertThat(clienteById).isEqualTo(Optional.empty());

        Assertions.assertThat(clienteById.isPresent()).isFalse();
    }

    @Test
    @DisplayName("findById cliente when sucessful")
    void findById_FindCliente_WhenSucessful(){
        Cliente cliente = createdCliente();
        cliente.getTelefones().add(PHONE);
        Cliente clienteSaved = clienteRepository.save(cliente);

        Optional<Cliente> clienteById = clienteRepository.findById(clienteSaved.getId());

        Assertions.assertThat(clienteById.get()).isNotNull().isIn(clienteSaved);

        Assertions.assertThat(clienteById.get()).isEqualTo(clienteSaved);

        Assertions.assertThat(clienteById.get().getId()).isEqualTo(clienteSaved.getId());

        Assertions.assertThat(clienteById.get().getNome()).isNotNull().isEqualTo(clienteSaved.getNome()).isEqualTo(NAME_CLIENT);

        Assertions.assertThat(clienteById.get().getEmail()).isNotNull().isEqualTo(clienteSaved.getEmail()).isEqualTo(EMAIL);

        Assertions.assertThat(clienteById.get().getCpf_cnpj()).isNotNull().isEqualTo(clienteSaved.getCpf_cnpj()).isEqualTo(CPF);

        Assertions.assertThat(clienteById.get().getTipoCliente()).isNotNull().isEqualTo(clienteSaved.getTipoCliente())
                .isEqualTo(TipoCliente.PESSOA_FISICA);

        Assertions.assertThat(clienteById.get().getTelefones()).isNotNull().isNotEmpty().isEqualTo(clienteSaved.getTelefones());

        Assertions.assertThat(clienteById.get().getTelefones().contains(PHONE)).isTrue();

        Assertions.assertThat(clienteById.get().getEnderecos()).isNotNull().isEqualTo(clienteSaved.getEnderecos());

        Assertions.assertThat(clienteById.get().getClass()).isEqualTo(Cliente.class);

        Assertions.assertThat(clienteById.isPresent()).isTrue();
    }

    @Test
    @DisplayName("findById cliente when not found")
    void findById_FindCliente_NotFound(){
        Optional<Cliente> clienteById = clienteRepository.findById(310L);

        Assertions.assertThat(clienteById).isEqualTo(Optional.empty());

        Assertions.assertThat(clienteById.isPresent()).isFalse();
    }


    private Cliente createdCliente(){
        return new Cliente(null, NAME_CLIENT, EMAIL, CPF, TipoCliente.PESSOA_FISICA);
    }

}