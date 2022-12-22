package com.caio.cursomc.service;

import com.caio.cursomc.model.*;
import com.caio.cursomc.model.enums.TipoCliente;
import com.caio.cursomc.model.enums.TipoEstadoPagamento;
import com.caio.cursomc.repository.*;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class PedidoServiceTest {

    private Estado estado;
    private Cidade cidade;
    private Cliente cliente;
    private Pedido pedido;
    private Endereco endereco;
    private Pagamento pagamento;
    private ItemPedido itemPedido;
    private Produto produto;

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private BoletoService boletoService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    private static final String NAME_STATE_CITY = "São paulo";
    private static final String NAME_CLIENT = "Jocimar";
    private static final String EMAIL_CLIENT = "jocimar@gmail.com";
    private static final String CPF_CLIENT = "12232159301";
    private static final String PUBLIC_PLACE = "Rua do mockito";
    private static final String NUMBER = "777";
    private static final String COMPLEMENT = "Bloco 1";
    private static final String DISTRICT = "Junit";
    private static final String CEP = "21212021";
    private static final String NAME_PRODUCT = "MOUSE";
    private static final Double PRICE_PRODUCT = 50.0;
    private static final Double DISCOUNT = 12.0;
    private static final Integer AMOUNT = 2;
    private static final Double ORDER_ITEM_PRICE = 200.0;

    @BeforeEach
    void setUp(){
        startEntitys();

        List<Pedido> pedidos = Arrays.asList(pedido);

        BDDMockito.when(pedidoRepository.findAll()).thenReturn(pedidos);

        BDDMockito.when(pedidoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(pedido));

        BDDMockito.doNothing().when(pedidoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("List return of pedidos when search Sucessful")
    void list_ReturnPedidos_WhenSucessful(){
        List<Pedido> pedidos = pedidoService.findAll();

        Assertions.assertThat(pedidos).isNotNull();

        Assertions.assertThat(pedidos.get(0).getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(pedidos.get(0).getEnderecoDeEntrega()).isNotNull().isEqualTo(endereco);

        Assertions.assertThat(pedidos.get(0).getPagamento()).isNotNull();

        Assertions.assertThat(pedidos.get(0).getInstant()).isNotNull();

        Assertions.assertThat(pedidos.get(0).getTotal()).isNotNull();

        Assertions.assertThat(pedidos.get(0).getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(pedidos.get(0).getClass()).isEqualTo(Pedido.class);

        Assertions.assertThat(pedidos.get(0).getItens()).isNotEmpty().isNotNull().isEqualTo(new HashSet(Arrays.asList(itemPedido)));

        Assertions.assertThat(pedidos.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAll return an empty list")
    void list_ReturnAEmptyList_WhenThereAreNoRecordsSaved(){
        BDDMockito.when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Pedido> pedidos = pedidoService.findAll();

        Assertions.assertThat(pedidos).isNotNull().isEqualTo(Collections.emptyList());;

        Assertions.assertThat(pedidos.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Return pedido by id when Sucessful")
    void returnPedidoById_WhenSucessful(){
        Pedido pedidoReturn = pedidoService.findById(1L);

        Assertions.assertThat(pedidoReturn).isNotNull();

        Assertions.assertThat(pedidoReturn.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(pedidoReturn.getEnderecoDeEntrega()).isNotNull().isEqualTo(endereco);

        Assertions.assertThat(pedidoReturn.getPagamento()).isNotNull();

        Assertions.assertThat(pedidoReturn.getInstant()).isNotNull();

        Assertions.assertThat(pedidoReturn.getTotal()).isNotNull();

        Assertions.assertThat(pedidoReturn.getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(pedidoReturn.getItens()).isNotEmpty().isNotNull().isEqualTo(new HashSet(Arrays.asList(itemPedido)));

        Assertions.assertThat(pedidoReturn.getClass()).isEqualTo(Pedido.class);
    }

    @Test
    @DisplayName("Return ObjectNotFound when pedido not found")
    void returnException_WhenNotFound(){
        BDDMockito.when(pedidoRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ObjectNotFoundException.class)
                .isThrownBy(() -> pedidoService.findById(1L));
    }

    @Test
    @DisplayName("Save pedido when sucessful")
    void save_AddPedido_WhenSucessful(){
        pedido.setItens(new HashSet<>());

        BDDMockito.when(pedidoRepository.save(ArgumentMatchers.any(Pedido.class))).thenReturn(pedido);

        Pedido pedidoInsert = new Pedido(null, new Date(), cliente, endereco);

        Pagamento pagamentoCartao = new PagamentoCartao(null, TipoEstadoPagamento.QUITADO, pedidoInsert, 2);
        pedidoInsert.setPagamento(pagamentoCartao);

        Pedido retorno = pedidoService.save(pedidoInsert);

        itemPedido.setPedido(retorno);
        retorno.setItens(new HashSet<>(Arrays.asList(itemPedido)));

        Assertions.assertThat(retorno).isNotNull();

        Assertions.assertThat(retorno.getId()).isNotNull().isEqualTo(1L);

        Assertions.assertThat(retorno.getEnderecoDeEntrega()).isNotNull().isEqualTo(endereco);

        Assertions.assertThat(retorno.getPagamento()).isNotNull();

        Assertions.assertThat(retorno.getInstant()).isNotNull();

        Assertions.assertThat(retorno.getTotal()).isNotNull();

        Assertions.assertThat(retorno.getCliente()).isNotNull().isEqualTo(cliente);

        Assertions.assertThat(retorno.getItens()).isNotEmpty().isNotNull().isEqualTo(new HashSet(Arrays.asList(itemPedido)));

        Assertions.assertThat(retorno.getClass()).isEqualTo(Pedido.class);
    }


    @Test
    @DisplayName("Delete pedido when sucessful")
    void delete_RemovePedido_WhenSucessful(){
        Assertions.assertThatCode(() -> pedidoService.delete(1L))
                .doesNotThrowAnyException();
    }

    private void startEntitys(){
        estado = new Estado(1L, NAME_STATE_CITY);
        cidade = new Cidade(1L, NAME_STATE_CITY, estado);
        cliente= new Cliente(1L, NAME_CLIENT, EMAIL_CLIENT, CPF_CLIENT, TipoCliente.PESSOA_FISICA);
        endereco = new Endereco(1L, PUBLIC_PLACE, NUMBER, COMPLEMENT, DISTRICT, CEP, cliente, cidade);
        pedido = new Pedido(1L, new Date(), cliente, endereco);
        pagamento = new PagamentoCartao(null, TipoEstadoPagamento.QUITADO, pedido, 2);
        pedido.setPagamento(pagamento);
        produto = new Produto(1L, NAME_PRODUCT, PRICE_PRODUCT);
        itemPedido = new ItemPedido(pedido, produto, DISCOUNT, AMOUNT, ORDER_ITEM_PRICE);

        pedido.getItens().add(itemPedido);

    }
}
