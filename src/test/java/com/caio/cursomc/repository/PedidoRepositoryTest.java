package com.caio.cursomc.repository;

import com.caio.cursomc.model.*;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.model.enums.TipoEstadoPagamento;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for pedido repository")
public class PedidoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

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
    @DisplayName("Find all return list pedido when sucessful")
    void findAll_ReturnListPedido_WhenSucessful(){
        List<Pedido> pedidos = pedidoRepository.findAll();

        Assertions.assertThat(pedidos).isNotNull();
    }

    @Test
    @DisplayName("save pedido when sucessful")
    void save_PersistPedido_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE_CITY);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Cliente cliente = createdCliente();
        Cliente clienteSaved = clienteRepository.save(cliente);

        Endereco endereco = createdEndereco(clienteSaved, cidadeSaved);
        Endereco enderecoSaved = enderecoRepository.save(endereco);

        Pedido pedido = createdPedido(clienteSaved, enderecoSaved);

        Pagamento pagamentoCartao = new PagamentoCartao(null, TipoEstadoPagamento.QUITADO, pedido, 2);
        pedido.setPagamento(pagamentoCartao);

        Pedido pedidoSaved = pedidoRepository.save(pedido);

        Assertions.assertThat(pedidoSaved).isNotNull();

        Assertions.assertThat(pedidoSaved.getId()).isNotNull();

        Assertions.assertThat(pedidoSaved.getInstant()).isNotNull();

        Assertions.assertThat(pedidoSaved.getPagamento()).isNotNull().isEqualTo(pagamentoCartao);

        Assertions.assertThat(pedidoSaved.getEnderecoDeEntrega()).isNotNull().isEqualTo(enderecoSaved);

        Assertions.assertThat(pedidoSaved.getCliente()).isEqualTo(clienteSaved).isNotNull();

        Assertions.assertThat(pedidoSaved.getItens()).isNotNull();

        Assertions.assertThat(pedidoSaved.getTotal()).isNotNull();

        Assertions.assertThat(pedidoSaved.getClass()).isEqualTo(Pedido.class);

    }

    @Test
    @DisplayName("update pedido when sucessful")
    void update_PersistCidade_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE_CITY);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Cliente cliente = createdCliente();
        Cliente clienteSaved = clienteRepository.save(cliente);

        Endereco endereco = createdEndereco(clienteSaved, cidadeSaved);
        Endereco enderecoSaved = enderecoRepository.save(endereco);

        Pedido pedido = createdPedido(clienteSaved, enderecoSaved);

        Pagamento pagamentoCartao = new PagamentoCartao(null, TipoEstadoPagamento.QUITADO, pedido, 2);
        pedido.setPagamento(pagamentoCartao);

        Pedido pedidoSaved = pedidoRepository.save(pedido);

        pedidoSaved.setCliente(createdCliente());

        Pedido pedidoUpdated = pedidoRepository.save(pedidoSaved);

        Assertions.assertThat(pedidoUpdated).isNotNull();

        Assertions.assertThat(pedidoUpdated.getId()).isNotNull().isEqualTo(pedidoSaved.getId());

        Assertions.assertThat(pedidoUpdated.getInstant()).isNotNull();

        Assertions.assertThat(pedidoUpdated.getPagamento()).isNotNull().isEqualTo(pagamentoCartao);

        Assertions.assertThat(pedidoUpdated.getEnderecoDeEntrega()).isNotNull().isEqualTo(enderecoSaved);

        Assertions.assertThat(pedidoUpdated.getCliente()).isEqualTo(createdCliente()).isNotNull();

        Assertions.assertThat(pedidoUpdated.getItens()).isNotNull();

        Assertions.assertThat(pedidoUpdated.getTotal()).isNotNull();

        Assertions.assertThat(pedidoUpdated.getClass()).isEqualTo(Pedido.class);

    }

    @Test
    @DisplayName("delete pedido when sucessful")
    void delete_RemovePedido_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE_CITY);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Cliente cliente = createdCliente();
        Cliente clienteSaved = clienteRepository.save(cliente);

        Endereco endereco = createdEndereco(clienteSaved, cidadeSaved);
        Endereco enderecoSaved = enderecoRepository.save(endereco);

        Pedido pedido = createdPedido(clienteSaved, enderecoSaved);

        Pagamento pagamentoCartao = new PagamentoCartao(null, TipoEstadoPagamento.QUITADO, pedido, 2);
        pedido.setPagamento(pagamentoCartao);

        Pedido pedidoSaved = pedidoRepository.save(pedido);

        pedidoRepository.delete(pedidoSaved);

        Optional<Pedido> pedidoById = pedidoRepository.findById(pedidoSaved.getId());

        Assertions.assertThat(pedidoById).isEqualTo(Optional.empty());

        Assertions.assertThat(pedidoById.isPresent()).isFalse();
    }

    @Test
    @DisplayName("findById pedido when sucessful")
    void findById_FindPedido_WhenSucessful(){
        Estado estado = new Estado(null, NAME_STATE_CITY);
        Estado estadoSaved = estadoRepository.save(estado);

        Cidade cidade = createdCidade(estadoSaved);
        Cidade cidadeSaved = cidadeRepository.save(cidade);

        Cliente cliente = createdCliente();
        Cliente clienteSaved = clienteRepository.save(cliente);

        Endereco endereco = createdEndereco(clienteSaved, cidadeSaved);
        Endereco enderecoSaved = enderecoRepository.save(endereco);

        Pedido pedido = createdPedido(clienteSaved, enderecoSaved);

        Pagamento pagamentoCartao = new PagamentoCartao(null, TipoEstadoPagamento.QUITADO, pedido, 2);
        pedido.setPagamento(pagamentoCartao);

        Pedido pedidoSaved = pedidoRepository.save(pedido);

        Optional<Pedido> pedidoById = pedidoRepository.findById(pedidoSaved.getId());

        Assertions.assertThat(pedidoById.get()).isNotNull();

        Assertions.assertThat(pedidoById.get().getId()).isNotNull().isEqualTo(pedidoSaved.getId());

        Assertions.assertThat(pedidoById.get().getInstant()).isNotNull();

        Assertions.assertThat(pedidoById.get().getPagamento()).isNotNull();

        Assertions.assertThat(pedidoById.get().getEnderecoDeEntrega()).isNotNull().isEqualTo(enderecoSaved);

        Assertions.assertThat(pedidoById.get().getCliente()).isEqualTo(clienteSaved).isNotNull();

        Assertions.assertThat(pedidoById.get().getTotal()).isNotNull();

        Assertions.assertThat(pedidoById.get().getClass()).isEqualTo(Pedido.class);

        Assertions.assertThat(pedidoById.isPresent()).isTrue();

        Assertions.assertThat(pedidoById.get().getItens()).isNotNull();

    }

    @Test
    @DisplayName("findById pedido when not found")
    void findById_FindPedido_NotFound(){
        Optional<Pedido> pedidoById = pedidoRepository.findById(310L);

        Assertions.assertThat(pedidoById).isEqualTo(Optional.empty());

        Assertions.assertThat(pedidoById.isPresent()).isFalse();
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

    private Pedido createdPedido(Cliente cliente, Endereco endereco){
        return new Pedido(null, new Date() , cliente, endereco);
    }
}